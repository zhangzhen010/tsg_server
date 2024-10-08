package com.game.redis.manager;

import com.alibaba.fastjson2.JSON;
import com.game.clans.structs.*;
import com.game.data.define.MyDefineConstant;
import com.game.db.proto.DbProto;
import com.game.db.utils.*;
import com.game.friend.structs.FriendPublicData;
import com.game.player.structs.*;
import com.game.redis.structs.RedisClientConfig;
import com.game.redis.structs.RedisKey;
import com.game.user.structs.User;
import com.game.utils.DataZipUtil;
import com.game.utils.ID;
import com.game.utils.TimeUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.params.ZAddParams;
import redis.clients.jedis.resps.Tuple;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Redis管理
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/15 10:12
 */
@Component
@Log4j2
public class RedisManager {

    private final Logger redisLog = LogManager.getLogger("REDIS");
    /**
     * redis客户端配置列表
     */
    private final List<RedisClientConfig> redisConfigList = new ArrayList<>();
    /**
     * redis客户端配置列表
     */
    private final Map<String, RedisClientConfig> redisConfigMap = new HashMap<>();
    /**
     * redis连接map key=configKey value=JedisPool
     */
    private final ConcurrentHashMap<String, JedisPool> redisPoolMap = new ConcurrentHashMap<>();
    /**
     * redis连接map key=configKey value=RedissonClient
     */
    private final ConcurrentHashMap<String, RedissonClient> redissonClientMap = new ConcurrentHashMap<>();
    /**
     * redisson锁
     */
    private final Object redissonLock = new Object();
    /**
     * redis分布式锁Key
     */
    private static final String REDIS_LOCK = "redisLock_";
    /**
     * 上一次打印日志数量时间
     */
    private long lastLogNumTime;

    /**
     * 这里使用的javax的注解，使用jakarta.annotation的在web项目中（springboot2）无法自动注入，其他项目springboot3都支持
     */
    @PostConstruct
    public void load() {
        loadRedisConfig();
        init();
        testJedis();
    }

    /**
     * 加载redis配置
     */
    private void loadRedisConfig() {
        try {
            File file = new File("redis.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String path = this.getClass().getClassLoader().getResource("").getPath();
            InputStream in = new FileInputStream(path + file);
            Document doc = builder.parse(in);
            NodeList list = doc.getElementsByTagName("redisInfo");
            if (list.getLength() > 0) {
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    NodeList childs = node.getChildNodes();
                    RedisClientConfig redisConfig = new RedisClientConfig();
                    for (int j = 0; j < childs.getLength(); j++) {
                        if ("key".equals(childs.item(j).getNodeName())) {
                            redisConfig.setKey(childs.item(j).getTextContent().trim());
                        } else if ("ip".equals(childs.item(j).getNodeName())) {
                            redisConfig.setIp(childs.item(j).getTextContent().trim());
                        } else if ("port".equals(childs.item(j).getNodeName())) {
                            redisConfig.setPort(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("auth".equals(childs.item(j).getNodeName())) {
                            redisConfig.setAuth(childs.item(j).getTextContent().trim());
                        } else if ("maxTotal".equals(childs.item(j).getNodeName())) {
                            redisConfig.setMaxTotal(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("maxIdle".equals(childs.item(j).getNodeName())) {
                            redisConfig.setMaxIdle(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("blockWhenExhausted".equals(childs.item(j).getNodeName())) {
                            redisConfig.setBlockWhenExhausted(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        } else if ("maxWaitMillis".equals(childs.item(j).getNodeName())) {
                            redisConfig.setMaxWaitMillis(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("timeout".equals(childs.item(j).getNodeName())) {
                            redisConfig.setTimeout(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("testOnBorrow".equals(childs.item(j).getNodeName())) {
                            redisConfig.setTestOnBorrow(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        } else if ("testOnReturn".equals(childs.item(j).getNodeName())) {
                            redisConfig.setTestOnReturn(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        } else if ("evictionPolicyClassName".equals(childs.item(j).getNodeName())) {
                            redisConfig.setEvictionPolicyClassName(childs.item(j).getTextContent().trim());
                        } else if ("jmxEnabled".equals(childs.item(j).getNodeName())) {
                            redisConfig.setJmxEnabled(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        } else if ("jmxNamePrefix".equals(childs.item(j).getNodeName())) {
                            redisConfig.setJmxNamePrefix(childs.item(j).getTextContent().trim());
                        } else if ("setLifo".equals(childs.item(j).getNodeName())) {
                            redisConfig.setSetLifo(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        } else if ("minEvictableIdleTimeMillis".equals(childs.item(j).getNodeName())) {
                            redisConfig.setMinEvictableIdleTimeMillis(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("minIdle".equals(childs.item(j).getNodeName())) {
                            redisConfig.setMinIdle(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("numTestsPerEvictionRun".equals(childs.item(j).getNodeName())) {
                            redisConfig.setNumTestsPerEvictionRun(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("softMinEvictableIdleTimeMillis".equals(childs.item(j).getNodeName())) {
                            redisConfig.setSoftMinEvictableIdleTimeMillis(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("testWhileIdle".equals(childs.item(j).getNodeName())) {
                            redisConfig.setTestWhileIdle(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        } else if ("timeBetweenEvictionRunsMillis".equals(childs.item(j).getNodeName())) {
                            redisConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt(childs.item(j).getTextContent().trim()));
                        } else if ("showMemoryInfo".equals(childs.item(j).getNodeName())) {
                            redisConfig.setShowMemoryInfo(Boolean.parseBoolean(childs.item(j).getTextContent().trim()));
                        }
                    }
                    getRedisConfigList().add(redisConfig);
                    getRedisConfigMap().put(redisConfig.getKey(), redisConfig);
                }
            }
            log.info("加载redis配置size=" + getRedisConfigMap().size());
            in.close();
        } catch (Exception e) {
            log.error("加载redis配置异常", e);
        }
    }

    /**
     * redis初始化
     */
    private void init() {
        try {
            for (int i = 0; i < getRedisConfigList().size(); i++) {
                // 初始化Jedis
                RedisClientConfig clientConfig = getRedisConfigList().get(i);
                JedisPoolConfig config = new JedisPoolConfig();
                // 最大jedis实例
                config.setMaxTotal(clientConfig.getMaxTotal());
                // 最大空闲连接数(控制一个pool最多有多少个状态为idle(空闲的)的jedis实例)
                config.setMaxIdle(clientConfig.getMaxIdle());
                // 资源池确保最少空闲连接数
                config.setMinIdle(clientConfig.getMinIdle());
                config.setMaxWait(Duration.ofMillis(clientConfig.getMaxWaitMillis()));
                config.setTestOnBorrow(clientConfig.isTestOnBorrow());
                config.setTestOnReturn(clientConfig.isTestOnReturn());
                JedisPool jedisPool = new JedisPool(config, clientConfig.getIp(), clientConfig.getPort(), clientConfig.getTimeout(), clientConfig.getAuth().isEmpty() ? null : clientConfig.getAuth());
                getRedisPoolMap().put(clientConfig.getKey(), jedisPool);
            }
        } catch (Exception e) {
            log.error("redis初始化", e);
        }
    }

    public List<RedisClientConfig> getRedisConfigList() {
        return redisConfigList;
    }

    public Map<String, RedisClientConfig> getRedisConfigMap() {
        return redisConfigMap;
    }

    private Map<String, JedisPool> getRedisPoolMap() {
        return redisPoolMap;
    }

    public ConcurrentHashMap<String, RedissonClient> getRedissonClientMap() {
        return redissonClientMap;
    }

    /**
     * 获取本地Jedis实例，不同服务器连接不同数据库
     *
     * @return
     */
    public Jedis getServerJedis() {
        return getJedis("server");
    }

    /**
     * 获取本地Jedis实例，不同服务器连接不同数据库
     *
     * @return
     */
    public Jedis getCenterJedis() {
        return getJedis("center");
    }

    /**
     * 获取Jedis实例，不同服务器连接不同数据库
     *
     * @param key
     * @return
     */
    private Jedis getJedis(String key) {
        try {
            long startTime = System.currentTimeMillis();
            JedisPool jedisPool = getRedisPoolMap().get(key);
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
//				String state = jedis.select(REDIS_DB);
                long time = System.currentTimeMillis() - startTime;
                if (time > 30) {
                    log.info("获取redis连接[" + key + "]耗时：[" + time + "]");
                }
//				if (state.equals("OK")) {
                return jedis;
//				}
//				return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("获取Jedis实例，不同服务器连接不同数据库异常key=" + key, e);
            return null;
        }
    }

    /**
     * 获取本地Redisson实例，不同服务器连接不同数据库
     *
     * @return
     */
    public RedissonClient getServerRedisson() {
        return getRedissonClient("server");
    }

    /**
     * 获取本地Redisson实例，不同服务器连接不同数据库
     *
     * @return
     */
    public RedissonClient getCenterRedisson() {
        return getRedissonClient("center");
    }

    /**
     * 获取redisson客户端
     *
     * @param name
     * @return
     */
    public RedissonClient getRedissonClient(String name) {
        try {
            RedissonClient redissonClient = getRedissonClientMap().get(name);
            if (redissonClient == null) {
                synchronized (redissonLock) {
                    // 获取redis配置
                    RedisClientConfig clientConfig = getRedisConfigMap().get(name);
                    // 初始化Redisson（分布式锁）
                    Config redissonConfig = new Config();
                    redissonConfig.useSingleServer().setAddress("redis://" + clientConfig.getIp() + ":" + clientConfig.getPort()).setPassword(clientConfig.getAuth().isEmpty() ? null : clientConfig.getAuth()).setTimeout(clientConfig.getTimeout());
                    redissonClient = Redisson.create(redissonConfig);
                    getRedissonClientMap().put(clientConfig.getKey(), redissonClient);
                    return redissonClient;
                }
            }
            return redissonClient;
        } catch (Exception e) {
            log.error("获取redisson客户端异常：", e);
            return null;
        }
    }

    /**
     * 释放jedis资源(使用后调用此方法)
     *
     * @param jedis
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
            // jedisPool.returnResource(jedis); 废弃低版本才使用而且有bug
        }
    }

    /**
     * 测试redis，并显示当前操作数据库是否正常
     */
    private void testJedis() {
        Jedis jedis = getServerJedis();
        try {
            log.info("当前连接redis数据库 DB = " + jedis.getDB());
        } catch (Exception e) {
            log.error("测试redis异常", e);
        } finally {
            returnResource(jedis);
        }
        // 测试redisson分布式锁
        boolean publicLock = false;
        try {
            publicLock = publicLock(getServerRedisson(), "1");
            if (publicLock) {
                log.info("测试获取分布式锁成功！");
            }
        } catch (Exception e) {
            log.error("测试redisson分布式锁异常:", e);
        } finally {
            if (publicLock) {
                log.info("测试释放分布式锁！");
                publicUnlock(getServerRedisson(), "1");
            }
        }
    }

    /**
     * 获取分布式锁
     *
     * @param redissonClient
     * @param lockName
     * @return
     */
    public boolean publicLock(RedissonClient redissonClient, String lockName) {
        try {
            // 声明key对象
            String key = REDIS_LOCK + lockName;
            // 获取锁对象
            RLock lock = redissonClient.getLock(key);
            // 尝试加锁，等待1秒没拿到锁就放弃，拿不到锁肯定逻辑有问题，(这种方式有看门狗，拿到锁后自动续期，防止某些没有处理完，其他线程同时进行处理)
            return lock.tryLock(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("获取分布式锁", e);
            return false;
        }
    }

    /**
     * 获取分布式锁
     *
     * @param redissonClient
     * @param lockName
     * @param timeout        等待超时，毫秒
     * @return
     */
    public boolean publicLock(RedissonClient redissonClient, String lockName, long timeout) {
        try {
            // 声明key对象
            String key = REDIS_LOCK + lockName;
            // 获取锁对象
            RLock lock = redissonClient.getLock(key);
            // 尝试加锁，等待1秒没拿到锁就放弃，拿不到锁肯定逻辑有问题，(这种方式有看门狗，拿到锁后自动续期，防止某些没有处理完，其他线程同时进行处理)
            return lock.tryLock(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("获取分布式锁", e);
            return false;
        }
    }

    /**
     * 释放分布式锁
     *
     * @param redissonClient
     * @param lockName
     */
    public void publicUnlock(RedissonClient redissonClient, String lockName) {
        try {
            // 必须是和加锁时的同一个key
            String key = REDIS_LOCK + lockName;
            // 获取所对象
            RLock mylock = redissonClient.getLock(key);
            // 释放锁（解锁）(逻辑层来判断成功获取锁才解锁，下面的注释代码也可以判断，但是需要向reds请求2次来获得结果)
//			if (mylock.isLocked() && mylock.isHeldByCurrentThread()) {
            mylock.unlock();
//			}
        } catch (Exception e) {
            log.error("释放分布式锁", e);
        }
    }

    /**
     * 构建redis key
     *
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    private String buildRedisKey(RedisKey key, String keyPrefix, String keySuffix) {
        String keyString = key.getKey();
        if (keyPrefix != null) {
            keyString = keyPrefix + keyString;
        }
        if (keySuffix != null) {
            keyString = keyString + keySuffix;
        }
        return keyString;
    }

    /**
     * 检查Redis的一个key是否存在
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public boolean redisKeyExist(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            return jedis.exists(keyString);
        } catch (Exception e) {
            log.error("检查一个key是否存在", e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除Redis的一个key
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     */
    public void redisKeyDel(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            jedis.del(keyString.getBytes());
        } catch (Exception e) {
            log.error("删除Redis的一个key", e);
            return;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传排行榜数据
     * <p>
     * 更新排行榜 XX: 仅仅更新存在的成员，不添加新成员。 NX: 只添加新成员，不更新存在的成员。 CH:
     * 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。 更改的元素是新添加的成员，已经存在的成员更新分数。
     * 所以在命令中指定的成员有相同的分数将不被计算在内。 注：在通常情况下，ZADD返回值只计算新添加成员的数量。 INCR:
     * 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
     * <p>
     * 老版本zadd方法不带有ZAddParams属性，类似于新版本ch，但是返回数量老版本只返回新增数量，新版本返回新增和修改总数
     *
     * @param jedis
     * @param key
     * @param map
     * @param keyPrefix
     * @param keySuffix
     * @param seconds   过期时间 >0表示有过期时间
     */
    public void rankZadd(Jedis jedis, RedisKey key, Map<String, Double> map, String keyPrefix, String keySuffix, long seconds) {
        try {
            if (jedis == null) {
                log.error("更新排行榜数据获取jedis=null");
                return;
            }
            // long state =
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            jedis.zadd(keyString, map, ZAddParams.zAddParams().ch());
            // 设置过期时间
            if (seconds > 0) {
                jedis.expire(keyString, seconds);
            }
//			redisLog.info("上传更新排行榜 type=" + key + keySuffix + " 更新数量=" + state);
        } catch (Exception e) {
            log.error("更新排行榜异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传排行榜数据
     * <p>
     * 更新排行榜 XX: 仅仅更新存在的成员，不添加新成员。 NX: 只添加新成员，不更新存在的成员。 CH:
     * 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。 更改的元素是新添加的成员，已经存在的成员更新分数。
     * 所以在命令中指定的成员有相同的分数将不被计算在内。 注：在通常情况下，ZADD返回值只计算新添加成员的数量。 INCR:
     * 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
     * <p>
     * 老版本zadd方法不带有ZAddParams属性，类似于新版本ch，但是返回数量老版本只返回新增数量，新版本返回新增和修改总数
     *
     * @param jedis
     * @param key
     * @param member
     * @param score
     * @param keyPrefix
     * @param keySuffix
     * @param seconds   过期时间 >0表示有过期时间
     */
    public void rankZadd(Jedis jedis, RedisKey key, String member, double score, String keyPrefix, String keySuffix, long seconds) {
        try {
            if (jedis == null) {
                log.error("更新排行榜数据获取jedis=null");
                return;
            }
            // long state =
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            jedis.zadd(keyString, score, member, ZAddParams.zAddParams().ch());
            // 设置过期时间
            if (seconds > 0) {
                jedis.expire(keyString, seconds);
            }
//			redisLog.info("上传更新排行榜 type=" + key + keySuffix + " 更新数量=" + state);
        } catch (Exception e) {
            log.error("更新排行榜异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取排行榜成员数量
     *
     * @param key
     * @param keySuffix
     * @return
     */
    public long rankZcard(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("获取排行榜成员数量jedis=null");
                return 0;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            Long num = jedis.zcard(keyString);
            return num;
        } catch (Exception e) {
            log.error("获取排行榜成员数量异常 type=" + key);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。 可以通过传递一个负数值 increment ，让 score减去相应的值 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADDkey increment member 。 当 key 不是有序集类型时，返回一个错误。 score 值可以是整数值或双精度浮点数。
     *
     * @param jedis
     * @param key
     * @param score
     * @param member
     * @param keyPrefix
     * @param keySuffix
     */
    public void rankZincrby(Jedis jedis, RedisKey key, double score, String member, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("修改排行榜数据获取jedis=null");
                return;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            jedis.zincrby(keyString, score, member);
//			redisLog.info("修改排行榜 type=" + key.getKey() + keySuffix + " playerId=" + member + " add=" + score
//					+ " result=" + state);
        } catch (Exception e) {
            log.error("修改排行榜异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)(降序)
     *
     * @param jedis
     * @param key
     * @param num       获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> rankZrevrange(Jedis jedis, RedisKey key, long num, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜数据获取jedis=null");
                return Collections.emptyList();
            }
            // 返回降序20位排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            List<Tuple> rankList = jedis.zrevrangeWithScores(keyString, 0l, num);// -1l = 全部
            // 49=50个
//			redisLog.info("下载更新排行榜 type=" + key + keySuffix + " 更新数量=" + rankSet.size());
            return rankList;
        } catch (Exception e) {
            log.error("下载更新排行榜异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)(降序)
     *
     * @param jedis
     * @param key
     * @param start
     * @param end       获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> rankZrevrange(Jedis jedis, RedisKey key, long start, long end, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜数据获取jedis=null");
                return Collections.emptyList();
            }
            // redis向后超出不足不会出问题，向前成为负数会出现一个都找不到的bug
            if (start < 0) {
                start = 0;
            }
            // 返回降序20位排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            List<Tuple> rankList = jedis.zrevrangeWithScores(keyString, start, end);// -1l = 全部
            // 49=50个
//			redisLog.info("下载更新排行榜 type=" + key + keySuffix + " 更新数量=" + rankSet.size());
            return rankList;
        } catch (Exception e) {
            log.error("下载更新排行榜异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)(只获取所有key，不包含score)(降序)
     *
     * @param jedis
     * @param key
     * @param start     0开始
     * @param num       获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<String> rankZrevrangeKey(Jedis jedis, RedisKey key, long start, long num, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            if (start < 0) {
                start = 0;
            }
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            List<String> rankList = jedis.zrevrange(keyString, start, num);// 0开始，-1l表示返回所有
//			redisLog.info("下载更新排行榜(只获取所有key，不包含score) type=" + key + keySuffix + " 更新数量=" + rankSet.size());
            return rankList;
        } catch (Exception e) {
            log.error("下载更新排行榜(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)（pipeline）(只获取所有key，不包含score)(降序)
     *
     * @param jedis
     * @param key
     * @param startList
     * @param numList   获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Object> rankZrevrangeKey(Jedis jedis, RedisKey key, List<Integer> startList, List<Integer> numList, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            Pipeline pipelined = jedis.pipelined();
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            for (int i = 0; i < startList.size(); i++) {
                Integer num = startList.get(i);
                if (num < 0) {
                    num = 0;
                }
                pipelined.zrevrange(keyString, num, numList.get(i));// 0开始，-1l表示返回所有
            }
            List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
            return syncAndReturnAll;
        } catch (Exception e) {
            log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)(升序)
     *
     * @param jedis
     * @param key
     * @param num       获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> rankZrange(Jedis jedis, RedisKey key, long num, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜数据获取jedis=null");
                return Collections.emptyList();
            }
            // 返回降序20位排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            List<Tuple> rankList = jedis.zrangeWithScores(keyString, 0l, num);// -1l = 全部
            // 49=50个
//			redisLog.info("下载更新排行榜 type=" + key + keySuffix + " 更新数量=" + rankSet.size());
            return rankList;
        } catch (Exception e) {
            log.error("下载更新排行榜异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)(升序)
     *
     * @param jedis
     * @param key
     * @param start
     * @param end       获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> rankZrange(Jedis jedis, RedisKey key, long start, long end, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜数据获取jedis=null");
                return Collections.emptyList();
            }
            // redis向后超出不足不会出问题，向前成为负数会出现一个都找不到的bug
            if (start < 0) {
                start = 0;
            }
            // 返回降序20位排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            List<Tuple> rankList = jedis.zrangeWithScores(keyString, start, end);// -1l = 全部
            // 49=50个
//			redisLog.info("下载更新排行榜 type=" + key + keySuffix + " 更新数量=" + rankSet.size());
            return rankList;
        } catch (Exception e) {
            log.error("下载更新排行榜异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)(只获取所有key，不包含score)(升序)
     *
     * @param jedis
     * @param key
     * @param start     0开始
     * @param num       获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<String> rankZrangeKey(Jedis jedis, RedisKey key, long start, long num, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            if (start < 0) {
                start = 0;
            }
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            List<String> rankList = jedis.zrange(keyString, start, num);// 0开始，-1l表示返回所有
//			redisLog.info("下载更新排行榜(只获取所有key，不包含score) type=" + key + keySuffix + " 更新数量=" + rankSet.size());
            return rankList;
        } catch (Exception e) {
            log.error("下载更新排行榜(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜排名)（pipeline）(只获取所有key，不包含score)(升序)
     *
     * @param jedis
     * @param key
     * @param startList
     * @param numList   获取位置0是第一个，-1l = 全部
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Object> rankZrangeKey(Jedis jedis, RedisKey key, List<Integer> startList, List<Integer> numList, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            Pipeline pipelined = jedis.pipelined();
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            for (int i = 0; i < startList.size(); i++) {
                Integer num = startList.get(i);
                if (num < 0) {
                    num = 0;
                }
                pipelined.zrange(keyString, num, numList.get(i));// 0开始，-1l表示返回所有
            }
            List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
            return syncAndReturnAll;
        } catch (Exception e) {
            log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜积分)(降序)
     *
     * @param jedis
     * @param key
     * @param maxScore  最大分数（包含）
     * @param minScore  最小分数（包含）
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> rankZrevrangeByScore(Jedis jedis, RedisKey key, long maxScore, long minScore, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜数据获取jedis=null");
                return Collections.emptyList();
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            return jedis.zrevrangeByScoreWithScores(keyString, maxScore, minScore);
        } catch (Exception e) {
            log.error("下载更新排行榜异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜积分)（pipeline）(只获取所有key，不包含score)(降序)
     *
     * @param jedis
     * @param key
     * @param maxScoreList 最大分数列表（包含）
     * @param minScoreList 最小分数列表（包含）
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Object> rankZrevrangeByScore(Jedis jedis, RedisKey key, List<Integer> maxScoreList, List<Integer> minScoreList, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            Pipeline pipelined = jedis.pipelined();
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            for (int i = 0; i < maxScoreList.size(); i++) {
                pipelined.zrevrangeByScore(keyString, maxScoreList.get(i), minScoreList.get(i));
            }
            return pipelined.syncAndReturnAll();
        } catch (Exception e) {
            log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜积分)（pipeline）(降序)
     *
     * @param jedis
     * @param key
     * @param maxScoreList 最大分数列表（包含）
     * @param minScoreList 最小分数列表（包含）
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<List<Tuple>> rankZrevrangeByScoreWithScores(Jedis jedis, RedisKey key, List<Integer> maxScoreList, List<Integer> minScoreList, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            Pipeline pipelined = jedis.pipelined();
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            for (int i = 0; i < maxScoreList.size(); i++) {
                pipelined.zrevrangeByScoreWithScores(keyString, maxScoreList.get(i), minScoreList.get(i));
            }
            List<Object> objectList = pipelined.syncAndReturnAll();
            List<List<Tuple>> allTupleList = new ArrayList<>();
            for (Object response : objectList) {
                List<Tuple> tupleList = (List<Tuple>) response;
                allTupleList.add(tupleList);
            }
            return allTupleList;
        } catch (Exception e) {
            log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜积分)(升序)
     *
     * @param jedis
     * @param key
     * @param minScore  最小分数（包含）
     * @param maxScore  最大分数（包含）
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> rankZrangeByScore(Jedis jedis, RedisKey key, long minScore, long maxScore, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜数据获取jedis=null");
                return Collections.emptyList();
            }
            // 返回降序20位排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            return jedis.zrangeByScoreWithScores(keyString, minScore, maxScore);
        } catch (Exception e) {
            log.error("下载更新排行榜异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载更新排行榜(根据排行榜积分)（pipeline）(只获取所有key，不包含score)(升序)
     *
     * @param jedis
     * @param key
     * @param minScoreList 最小分数列表（包含）
     * @param maxScoreList 最大分数列表（包含）
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Object> rankZrangeByScore(Jedis jedis, RedisKey key, List<Integer> minScoreList, List<Integer> maxScoreList, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)数据获取jedis=null");
                return Collections.emptyList();
            }
            Pipeline pipelined = jedis.pipelined();
            // 返回所有排行数据
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            for (int i = 0; i < minScoreList.size(); i++) {
                pipelined.zrangeByScore(keyString, minScoreList.get(i), maxScoreList.get(i));// 0开始，-1l表示返回所有
            }
            return pipelined.syncAndReturnAll();
        } catch (Exception e) {
            log.error("下载更新排行榜（pipeline）(只获取所有key，不包含score)异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取指定元素的排名
     *
     * @param jedis
     * @param key
     * @param sourceId
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public int rankZrevrank(Jedis jedis, RedisKey key, long sourceId, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("获取指定元素的排名key=" + key.getKey() + keySuffix + " jedis=null");
                return -1;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            Long rank = jedis.zrevrank(keyString, String.valueOf(sourceId));
            if (rank == null) {
                rank = -1l;
            } else {
                rank++;// (排名以 0 为底，所以这里+1)
            }
            return rank.intValue();
        } catch (Exception e) {
            log.error("获取指定元素的排名异常 type=" + key.getKey() + keySuffix);
            return -1;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取指定元素的排行分数
     *
     * @param jedis
     * @param key
     * @param sourceId
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public long rankZscore(Jedis jedis, RedisKey key, long sourceId, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("获取指定元素的排行分数key=" + key.getKey() + keySuffix + " jedis=null");
                return -1;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            Double score = jedis.zscore(keyString, String.valueOf(sourceId));
            if (score == null) {
                score = 0d;// -1d
            }
            return score.longValue();
        } catch (Exception e) {
            log.error("获取指定元素的排行分数异常 type=" + key + keySuffix);
            return -1;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取指定元素的排行分数
     *
     * @param jedis
     * @param key
     * @param sourceIdList
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Object> rankZscore(Jedis jedis, RedisKey key, List<Long> sourceIdList, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("获取指定元素的排行分数key=" + key.getKey() + keySuffix + " jedis=null");
                return Collections.emptyList();
            }
            Pipeline pipelined = jedis.pipelined();
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            for (int i = 0; i < sourceIdList.size(); i++) {
                pipelined.zscore(keyString, String.valueOf(sourceIdList.get(i)));
            }
            return pipelined.syncAndReturnAll();
        } catch (Exception e) {
            log.error("获取指定元素的排行分数异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取指定元素是否存在于排行榜中
     *
     * @param jedis
     * @param key
     * @param sourceId
     * @param keyPrefix
     * @param keySuffix
     * @return 存在返回true
     */
    public boolean rankMemberExist(Jedis jedis, RedisKey key, long sourceId, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("获取指定元素是否存在于排行榜中key=" + key.getKey() + keySuffix + " jedis=null");
                return false;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            Long rank = jedis.zrevrank(keyString, String.valueOf(sourceId));
            if (rank == null) {
                rank = -1L;
            } else {
                rank++;// (排名以 0 为底，所以这里+1)
            }
            return rank >= 0;
        } catch (Exception e) {
            log.error("获取指定元素是否存在于排行榜中异常 type=" + key.getKey() + keySuffix);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取排序数据总条数
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public long rankZcount(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("获取排序数据总条数jedis=null");
                return 0;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            Long num = jedis.zcount(keyString, 0, System.currentTimeMillis());
//			redisLog.info("获取排序数据总条数 type=" + key + keySuffix + " 总数量=" + num);
            return num;
        } catch (Exception e) {
            log.error("获取排序数据总条数异常 type=" + key + keySuffix);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 排行榜移除指定的成员
     *
     * @param key
     * @param member
     * @param keySuffix redis的key后缀
     * @return
     */
    public long rankZrem(Jedis jedis, RedisKey key, String member, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("排行榜移除指定的成员key=" + key.getKey() + keySuffix + " jedis=null");
                return 0l;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            // 如果新元素被移除，则为1;如果新元素不是集合的成员，则为0
            long result = jedis.zrem(keyString, member);
//			redisLog.info("排行榜移除指定的成员 type=" + key + keySuffix + " members=" + member + " 移除数量=" + result);
            return result;
        } catch (Exception e) {
            log.error("排行榜移除指定的成员异常 type=" + key + keySuffix);
            return -1l;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 清理排序数据 这里是升序，第一个元素=0，第二个=1 倒数第1=-1 倒数第二=-2所以这里从0开始清理，表示时间最久的排序记录，
     *
     * @param jedis
     * @param key
     * @param end       表示要清理到第几条 清理包含end
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public long rankZremrangeByRank(Jedis jedis, RedisKey key, long end, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("清理排序数据获取jedis=null");
                return 0;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            return jedis.zremrangeByRank(keyString, 0, end);
        } catch (Exception e) {
            log.error("清理排序数据异常 type=" + key + keySuffix);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 清空整个排行数据（慎用）
     *
     * @param key
     * @param keySuffix redis的key后缀
     * @return
     */
    public long rankZremrangeAllByRank(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("清理排行数据获取jedis=null");
                return 0;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            Long num = jedis.zremrangeByRank(keyString, 0, -1);
            if (num != null && num != 0) {
                redisLog.info("清理排行数据 type=" + key + keySuffix + " 清理数量=" + num);
            }
            return num;
        } catch (Exception e) {
            log.error("清理排行数据异常 type=" + key + keySuffix);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 移除一个排序榜
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     */
    public void rankDel(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("移除一个排序榜获取jedis=null");
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            jedis.del(keyString);
        } catch (Exception e) {
            log.error("移除一个排序榜异常 type=" + key + keySuffix);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 移除多个排序榜（适合批量）
     *
     * @param jedis
     * @param key
     * @param keySuffixList
     */
    public void rankDel(Jedis jedis, RedisKey key, List<String> keySuffixList) {
        try {
            if (jedis == null) {
                log.error("移除多个公会的排序数据获取jedis=null");
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            for (int i = 0, len = keySuffixList.size(); i < len; i++) {
                String keyString = key.getKey();
                keyString = keyString + keySuffixList.get(i);
                pipelined.del(keyString);
            }
            pipelined.sync();
        } catch (Exception e) {
            log.error("移除多个排序榜（适合批量）异常 ：" + e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 对list数据类型进行轮询
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return 返回当前列表第一个元素（使用后放入列表末尾）
     */
    public String listRoundRobin(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("String自增获取jedis=null");
                return null;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            // 表尾元素被移动到表头，并返回该元素（非阻塞）
            return jedis.rpoplpush(keyString, keyString);
        } catch (Exception e) {
            log.error("对list数据类型进行轮询异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 对list数据类型添加数据(去重，添加列表末尾)
     *
     * @param jedis
     * @param key
     * @param value
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public void listRPushAtomic(Jedis jedis, RedisKey key, String value, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("String自增获取jedis=null");
                return;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            // 值不存在，表尾元素被移动到表头，并返回该元素
            String luaScript = """
                    local key = KEYS[1]
                    local value = ARGV[1]
                    redis.call('lrem', key, 0, value)
                    redis.call('rpush', key, value)
                    """; // 添加value到列表尾部
            // 使用eval执行Lua脚本，参数为keyString和value
            jedis.eval(luaScript, 1, keyString, value);
        } catch (Exception e) {
            log.error("对list数据类型添加数据(添加列表末尾)异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 对list数据获取范围
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @param start     开始下标，从0开始
     * @param stop      结束下标，-1表示全部
     * @return
     */
    public List<String> listRange(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix, long start, long stop) {
        try {
            if (jedis == null) {
                log.error("对list数据获取范围jedis=null");
                return null;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            // 获取list范围内元素
            return jedis.lrange(keyString, start, stop);
        } catch (Exception e) {
            log.error("对list数据获取范围异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 对list数据获取范围（全部）
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<String> listRange(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("对list数据获取范围（全部）jedis=null");
                return null;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            // 获取list范围内元素
            return jedis.lrange(keyString, 0, -1);
        } catch (Exception e) {
            log.error("对list数据获取范围（全部）异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 对list数据类型移除数据
     *
     * @param jedis
     * @param key
     * @param value
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public void listLRem(Jedis jedis, RedisKey key, String value, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("String自增获取jedis=null");
                return;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            // 表尾元素被移动到表头，并返回该元素（非阻塞）0表示移除所有相等的值
            jedis.lrem(keyString, 0, value);
        } catch (Exception e) {
            log.error("对list数据类型移除数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * String自增
     *
     * @param jedis
     * @param key
     * @param keyPrefix 前缀
     * @param keySuffix 后缀
     * @param increment 增量
     */
    public long stringIncrBy(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix, long increment) {
        try {
            if (jedis == null) {
                log.error("String自增获取jedis=null");
                return 0;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            return jedis.incrBy(keyString.getBytes(), increment);
        } catch (Exception e) {
            log.error("String自增异常", e);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * String设置数据
     *
     * @param jedis
     * @param key
     * @param keyPrefix 前缀
     * @param keySuffix 后缀
     * @param value
     * @param seconds   过期时间秒
     */
    public void stringSet(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix, byte[] value, long seconds) {
        try {
            if (jedis == null) {
                log.error("String设置数据获取jedis=null");
                return;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            if (seconds > 0) {
                // 设置过期时间
                jedis.set(keyString.getBytes(), value, SetParams.setParams().ex(seconds));
            } else {
                jedis.set(keyString.getBytes(), value);
            }
        } catch (Exception e) {
            log.error("String设置数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * String设置数据超时时间
     *
     * @param jedis
     * @param key
     * @param keyPrefix 前缀
     * @param keySuffix 后缀
     * @param seconds   过期时间秒
     */
    public void stringExpire(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix, long seconds) {
        try {
            if (jedis == null) {
                log.error("String设置数据超时时间获取jedis=null");
                return;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            jedis.expire(keyString.getBytes(), seconds);
        } catch (Exception e) {
            log.error("String设置数据超时时间异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * String获取数据
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public byte[] stringGet(Jedis jedis, RedisKey key, String keyPrefix, String keySuffix) {
        try {
            if (jedis == null) {
                log.error("String获取数据获取jedis=null");
                return null;
            }
            String keyString = buildRedisKey(key, keyPrefix, keySuffix);
            byte[] data = jedis.get(keyString.getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                redisLog.error("String获取数据不存在 keyString=" + keyString);
                return null;
            }
            return data;
        } catch (Exception e) {
            log.error("String获取数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * StringPipeline获取数据 注意传入的后缀list大小，别太大
     *
     * @param jedis
     * @param key
     * @param keyPrefix
     * @param keySuffixs
     * @return
     */
    public List<Object> stringPipelineGet(Jedis jedis, RedisKey key, String keyPrefix, List<String> keySuffixs) {
        try {
            if (jedis == null) {
                log.error("String获取数据获取jedis=null");
                return null;
            }
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < keySuffixs.size(); i++) {
                String keyString = key.getKey();
                if (keyPrefix != null) {
                    keyString = keyPrefix + keyString;
                }
                String keySuffix = keySuffixs.get(i);
                keyString = keyString + keySuffix;
                pipeline.get(keyString.getBytes());
            }
            List<Object> objects = pipeline.syncAndReturnAll();
            // 为空表示没有这个key || key里面没有这个field
            if (objects == null || objects.size() == 0) {
                redisLog.error("String获取数据不存在 keyString=" + keySuffixs);
                return null;
            }
            return objects;
        } catch (Exception e) {
            log.error("String获取数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 保存用户数据
     *
     * @param key
     * @param user
     */
    public void userSet(RedisKey key, User user) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("保存用户数据获取jedis=null");
                return;
            }
            String userData = JSON.toJSONString(user);
            // 设置一周过期时间
            jedis.set(key.getKey() + user.getUserName(), userData, SetParams.setParams().ex(TimeUtil.WEEK_SEC));
//			log.info("保存用户数据 type=" + key.getKey() + " user=" + userData + " result=" + result);
        } catch (Exception e) {
            log.error("保存用户数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取用户数据
     *
     * @param key
     * @param userName
     * @return
     */
    public User userGet(RedisKey key, String userName) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取用户数据获取jedis=null");
                return null;
            }
            String userData = jedis.get(key.getKey() + userName);
//			log.info("获取用户数据 type=" + key.getKey() + userName + " userName=" + userName + " userData=" + userData);
            if (userData == null) {
                return null;
            }
            return JSON.parseObject(userData, User.class);
        } catch (Exception e) {
            log.error("获取用户数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传聊天数据（根据聊天时间排序）
     * <p>
     * 更新数据 XX: 仅仅更新存在的成员，不添加新成员。 NX: 只添加新成员，不更新存在的成员。 CH:
     * 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。 更改的元素是新添加的成员，已经存在的成员更新分数。
     * 所以在命令中指定的成员有相同的分数将不被计算在内。 注：在通常情况下，ZADD返回值只计算新添加成员的数量。 INCR:
     * 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
     * <p>
     * 老版本zadd方法不带有ZAddParams属性，类似于新版本ch，但是返回数量老版本只返回新增数量，新版本返回新增和修改总数
     *
     * @param key
     * @param score
     * @param member
     * @param keyPrefix
     * @param keySuffix redis的key后缀 key=ChatData（聊天数据json） value=聊天发言时间
     */
    public void chatZadd(RedisKey key, double score, String member, String keyPrefix, String keySuffix) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("上传聊天数据获取jedis=null");
                return;
            }
            // 因为是把聊天内容当作key值处理，所以这里会有一个小小的bug，相同的聊天内容是不会更新到redis的，所以这里改为先删除相同的历史内容， 再添加新的内容
//			因为里面有时间，不需要移除了
//			for (String chatKey : map.keySet()) {
//				jedis.zrem(port + "_" + key.getKey() + keySuffix, chatKey);
//			}
            String redisKey = keyPrefix + "_" + key.getKey() + keySuffix;
            // 上传新的聊天内容
            jedis.zadd(redisKey, score, member, ZAddParams.zAddParams().nx());
            // 设置一周过期时间
            jedis.expire(redisKey, TimeUtil.WEEK_SEC);
//			log.info("上传聊天数据 type=" + key + keySuffix + " 更新数量=" + state);
        } catch (Exception e) {
            log.error("上传聊天数据异常 type=" + key + keySuffix, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 聊天移除指定的成员
     * 因为是把聊天内容当作key值处理，所以这里会有一个小小的bug，相同的聊天内容是不会更新到redis的，所以这里改为先删除相同的历史内容， 再添加新的内容
     *
     * @param key
     * @param member
     * @return
     */
    public long chatZrem(RedisKey key, String member) {
        return chatZrem(key, member, "", "");
    }

    /**
     * 聊天移除指定的成员
     * 因为是把聊天内容当作key值处理，所以这里会有一个小小的bug，相同的聊天内容是不会更新到redis的，所以这里改为先删除相同的历史内容， 再添加新的内容
     *
     * @param key
     * @param member
     * @param keyPrefix
     * @param keySuffix redis的key后缀
     * @return
     */
    public long chatZrem(RedisKey key, String member, String keyPrefix, String keySuffix) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("聊天移除指定的成员key=" + key.getKey() + keySuffix + " jedis=null");
                return 0l;
            }
            // 如果新元素被移除，则为1;如果新元素不是集合的成员，则为0
            long result = jedis.zrem(keyPrefix + "_" + key.getKey() + keySuffix, member);
//			log.info("聊天移除指定的成员 type=" + key + keySuffix + " members=" + member + " 移除数量=" + result);
            return result;
        } catch (Exception e) {
            log.error("聊天移除指定的成员异常 type=" + key + keySuffix);
            return -1l;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载聊天数据（根据角色当前的聊天条数，下载此条数之前的10条记录）
     *
     * @param key
     * @param num
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public List<Tuple> chatZrevrange(RedisKey key, long num, String keyPrefix, String keySuffix) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("下载聊天数据获取jedis=null");
                return Collections.emptyList();
            }
            // 惨痛教训，redis向后超出不足不会出问题，向前成为负数会出现一个都找不到的bug
            if (num < 0) {
                num = 0;
            }
            List<Tuple> chatSet = jedis.zrevrangeWithScores(keyPrefix + "_" + key.getKey() + keySuffix, num, num + 9);
//			log.info("下载聊天数据 type=" + key + keySuffix + " 更新数量=" + chatSet.size());
            return chatSet;
        } catch (Exception e) {
            log.error("下载聊天数据异常 type=" + key + keySuffix);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取聊天数据总条数
     *
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public long chatZcount(RedisKey key, String keyPrefix, String keySuffix) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取聊天数据总条数jedis=null");
                return 0;
            }
            Long num = jedis.zcount(keyPrefix + "_" + key.getKey() + keySuffix, 0, System.currentTimeMillis());
//			log.info("获取聊天数据总条数 type=" + key + keySuffix + " 总数量=" + num);
            return num;
        } catch (Exception e) {
            log.error("获取聊天数据总条数异常 type=" + key + keySuffix);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 清理聊天数据 这里是升序，第一个元素=0，第二个=1 倒数第1=-1 倒数第二=-2所以这里从0开始清理，表示时间最久的聊天记录，
     *
     * @param key
     * @param end       表示要清理到第几条 清理包含end
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public long chatZremrangeByRank(RedisKey key, long end, String keyPrefix, String keySuffix) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("清理聊天数据获取jedis=null");
                return 0;
            }
            Long num = jedis.zremrangeByRank(keyPrefix + "_" + key.getKey() + keySuffix, 0, end);
//			log.info("清理聊天数据 type=" + key + keySuffix + " 清理数量=" + num);
            return num;
        } catch (Exception e) {
            log.error("清理聊天数据异常 type=" + key + keySuffix);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 移除一个公会的聊天数据
     *
     * @param key
     * @param keyPrefix
     * @param keySuffix
     * @return
     */
    public long chatDel(RedisKey key, String keyPrefix, String keySuffix) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("移除一个公会的聊天数据获取jedis=null");
                return 0;
            }
            Long num = jedis.del(keyPrefix + "_" + key.getKey() + keySuffix);
//			log.info("移除一个公会的聊天数据 type=" + key + keySuffix + " 移除数量=" + num);
            return num;
        } catch (Exception e) {
            log.error("移除一个公会的聊天数据异常 type=" + key + keySuffix);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传本服聊天数据（根据聊天时间排序）
     * <p>
     * 更新数据 XX: 仅仅更新存在的成员，不添加新成员。 NX: 只添加新成员，不更新存在的成员。 CH:
     * 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。 更改的元素是新添加的成员，已经存在的成员更新分数。
     * 所以在命令中指定的成员有相同的分数将不被计算在内。 注：在通常情况下，ZADD返回值只计算新添加成员的数量。 INCR:
     * 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
     * <p>
     * 老版本zadd方法不带有ZAddParams属性，类似于新版本ch，但是返回数量老版本只返回新增数量，新版本返回新增和修改总数
     *
     * @param key
     * @param serverId
     * @param score
     * @param member
     */
    public void chatLocalZadd(RedisKey key, int serverId, double score, String member) {
        // 本服聊天也使用聊天redis
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("上传本服聊天数据获取jedis=null");
                return;
            }
            // 因为是把聊天内容当作key值处理，所以这里会有一个小小的bug，相同的聊天内容是不会更新到redis的，所以这里改为先删除相同的历史内容， 再添加新的内容
//			因为里面有时间，不需要移除了
//			for (String chatKey : map.keySet()) {
//				jedis.zrem(port + "_" + key.getKey() + keySuffix, chatKey);
//			}
            String redisKey = serverId + key.getKey();
            // 上传新的聊天内容
            jedis.zadd(redisKey, score, member, ZAddParams.zAddParams().nx());
            // 设置一周过期时间
            jedis.expire(redisKey, TimeUtil.WEEK_SEC);
//			log.info("上传聊天数据 type=" + key + keySuffix + " 更新数量=" + state);
        } catch (Exception e) {
            log.error("上传本服聊天数据异常 type=" + key, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载本服聊天数据（根据角色当前的聊天条数，下载此条数之前的10条记录）
     *
     * @param key
     * @param num 角色当前已有的聊天条数
     * @return
     */
    public List<Tuple> chatLocalZrevrange(RedisKey key, int serverId, long num) {
        // 本服聊天也使用聊天redis
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("下载本服聊天数据获取jedis=null");
                return Collections.emptyList();
            }
            // redis向后超出不足不会出问题，向前成为负数会出现一个都找不到的bug
            if (num < 0) {
                num = 0;
            }
            List<Tuple> chatList = jedis.zrevrangeWithScores(serverId + key.getKey(), num, num + 9);
//			log.info("下载聊天数据 type=" + key + keySuffix + " 更新数量=" + chatSet.size());
            return chatList;
        } catch (Exception e) {
            log.error("下载本服聊天数据异常 type=" + key);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取本服聊天数据总条数
     *
     * @param key
     * @param serverId
     * @return
     */
    public long chatLocalZcount(RedisKey key, int serverId) {
        // 本服聊天也使用聊天redis
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取本服聊天数据总条数jedis=null");
                return 0;
            }
            Long num = jedis.zcount(serverId + key.getKey(), 0, System.currentTimeMillis());
//			log.info("获取聊天数据总条数 type=" + key + keySuffix + " 总数量=" + num);
            return num;
        } catch (Exception e) {
            log.error("获取本服聊天数据总条数异常 type=" + key);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 清理本服聊天数据 这里是升序，第一个元素=0，第二个=1 倒数第1=-1 倒数第二=-2所以这里从0开始清理，表示时间最久的聊天记录，
     *
     * @param key
     * @param serverId
     * @param end      表示要清理到第几条 清理包含end
     * @return
     */
    public long chatLocalZremrangeByRank(RedisKey key, int serverId, long end) {
        // 本服聊天也使用聊天redis
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("清理本服聊天数据获取jedis=null");
                return 0;
            }
            Long num = jedis.zremrangeByRank(serverId + "_" + key.getKey(), 0, end);
//			log.info("清理聊天数据 type=" + key + keySuffix + " 清理数量=" + num);
            return num;
        } catch (Exception e) {
            log.error("清理本服聊天数据异常 type=" + key);
            return 0;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新玩家部落数据
     *
     * @param playerId
     * @param data
     */
    public void clansPlayerSet(long playerId, byte[] data) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新玩家公会获取jedis=null player=" + playerId);
                return;
            }
            jedis.set((RedisKey.CLANS_PLAYER.getKey() + playerId).getBytes(), data);
//			log.info("缓存玩家公会数据(老覆盖)player=" + playerId + " result=" + result);
        } catch (Exception e) {
            log.error("上传最新玩家公会数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家公会数据
     *
     * @param playerId
     * @return
     */
    public PlayerClansData clansPlayerGet(long playerId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家公会获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.CLANS_PLAYER.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家公会数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerClansData playerClansData = PlayerClansDataDbUtils.parseFromPlayerClansData(data);
//			log.info("从redis获取玩家公会数据player=" + playerId);
            return playerClansData;
        } catch (Exception e) {
            log.error("获取缓存玩家公会数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新公会数据
     *
     * @param clansId
     * @param data
     */
    public void clansSet(long clansId, byte[] data) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新公会获取jedis=null clansId=" + clansId);
                return;
            }
            jedis.set((RedisKey.CLANS.getKey() + clansId).getBytes(), data);
//			log.info("缓存公会数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("上传最新公会数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新公会简易数据
     *
     * @param clansId
     * @param data
     */
    public void clansSimpleSet(long clansId, byte[] data) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新公会简易获取jedis=null clansId=" + clansId);
                return;
            }
            jedis.set((RedisKey.CLANS_SIMPLE.getKey() + clansId).getBytes(), data);
//			log.info("缓存公会数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("上传最新公会简易数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 批量上传最新公会数据
     *
     * @param clansIdList
     * @param dataList
     */
    public void clansSet(List<Long> clansIdList, List<byte[]> dataList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("批量缓存最新公会获取jedis=null clansId=");
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            int index = 0;
            for (int i = 0, len = clansIdList.size(); i < len; i++) {
                Long clansId = clansIdList.get(i);
                byte[] data = dataList.get(i);
                pipelined.set((RedisKey.CLANS.getKey() + clansId).getBytes(), data);
                index++;
                if (index >= 100) {
                    pipelined.sync();
                    index = 0;
                }
            }
            if (index > 0) {
                pipelined.sync();
            }
//			log.info("缓存公会数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("批量上传最新公会数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 批量上传最新公会简易数据
     *
     * @param clansIdList
     * @param dataList
     */
    public void clansSimpleSet(List<Long> clansIdList, List<byte[]> dataList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("批量缓存最新公会简易数据获取jedis=null clansId=");
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            int index = 0;
            for (int i = 0, len = clansIdList.size(); i < len; i++) {
                Long clansId = clansIdList.get(i);
                byte[] data = dataList.get(i);
                pipelined.set((RedisKey.CLANS_SIMPLE.getKey() + clansId).getBytes(), data);
                index++;
                if (index >= 100) {
                    pipelined.sync();
                    index = 0;
                }
            }
            if (index > 0) {
                pipelined.sync();
            }
//			log.info("缓存公会数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("批量上传最新公会简易数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会数据
     *
     * @param clansId
     * @return
     */
    public Clans clansGet(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会获取jedis=null clansId=" + clansId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.CLANS.getKey() + clansId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取公会数据不存在 clansId=" + clansId);
                return null;
            }
            Clans clansData = ClansDbUtils.parseFromClans(DataZipUtil.dataDecode(data));
//			log.info("从redis获取公会数据clansId=" + clansId);
            return clansData;
        } catch (Exception e) {
            log.error("获取缓存公会异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会简易数据
     *
     * @param clansId
     * @return
     */
    public ClansSimple clansSimpleGet(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会简易获取jedis=null clansId=" + clansId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.CLANS_SIMPLE.getKey() + clansId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取公会简易数据不存在 clansId=" + clansId);
                return null;
            }
            ClansSimple clansSimpleData = ClansSimpleDbUtils.parseFromClansSimple(DataZipUtil.dataDecode(data));
//			log.info("从redis获取公会简易数据clansId=" + clansId);
            return clansSimpleData;
        } catch (Exception e) {
            log.error("获取缓存公会简易异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除缓存公会数据
     *
     * @param clansId
     */
    public void clansDel(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("删除缓存公会获取jedis=null clansId=" + clansId);
                return;
            }
            String clansIdsString = Long.toString(clansId);
            Pipeline pipelined = jedis.pipelined();
            pipelined.del((RedisKey.CLANS.getKey() + clansIdsString).getBytes());
            pipelined.del((RedisKey.CLANS_SIMPLE.getKey() + clansIdsString).getBytes());
            pipelined.srem(RedisKey.CLANS_ID_SET.getKey(), clansIdsString);
            pipelined.zrem(RedisKey.CLANS_ACTIVE_RANK.getKey(), clansIdsString);
            pipelined.del((RedisKey.CLANS_MAP.getKey() + clansIdsString).getBytes());
            pipelined.del((RedisKey.CLANS_MAP_LOG.getKey() + clansIdsString).getBytes());
            pipelined.zrem(RedisKey.CLANS_GVE_RANK.getKey(), clansIdsString);
            pipelined.sync();
            log.info("从redis删除公会数据clansId=" + clansIdsString);
        } catch (Exception e) {
            log.error("删除缓存公会异常", e);
            return;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会数据是否存在
     *
     * @param clansId
     * @return
     */
    public boolean clansIsExist(long clansId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会数据是否存在获取jedis=null clansId=" + clansId);
                return false;
            }
            return jedis.exists((RedisKey.CLANS.getKey() + clansId).getBytes());
        } catch (Exception e) {
            log.error("获取缓存公会数据是否存在异常", e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 新增公会id数据
     *
     * @param clansId
     */
    public void clansIdSet(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("新增公会id数据获取jedis=null clansId=" + clansId);
                return;
            }
            jedis.sadd(RedisKey.CLANS_ID_SET.getKey().getBytes(), Long.toString(clansId).getBytes());
//			log.info("新增公会id数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("新增公会id数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传公会排行数据
     * <p>
     * 更新数据 XX: 仅仅更新存在的成员，不添加新成员。 NX: 只添加新成员，不更新存在的成员。 CH:
     * 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。 更改的元素是新添加的成员，已经存在的成员更新分数。
     * 所以在命令中指定的成员有相同的分数将不被计算在内。 注：在通常情况下，ZADD返回值只计算新添加成员的数量。 INCR:
     * 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
     * <p>
     * 老版本zadd方法不带有ZAddParams属性，类似于新版本ch，但是返回数量老版本只返回新增数量，新版本返回新增和修改总数
     *
     * @param key
     * @param score
     * @param member
     */
    public void clansZadd(RedisKey key, double score, String member) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("上传公会活跃度排行数据获取jedis=null");
                return;
            }
            // 上传新的公会活跃值内容
            jedis.zadd(key.getKey(), score, member, ZAddParams.zAddParams().ch());
        } catch (Exception e) {
            log.error("上传公会活跃度排行数据异常 type=" + key.getKey(), e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传公会排行数据
     * <p>
     * 更新数据 XX: 仅仅更新存在的成员，不添加新成员。 NX: 只添加新成员，不更新存在的成员。 CH:
     * 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。 更改的元素是新添加的成员，已经存在的成员更新分数。
     * 所以在命令中指定的成员有相同的分数将不被计算在内。 注：在通常情况下，ZADD返回值只计算新添加成员的数量。 INCR:
     * 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
     * <p>
     * 老版本zadd方法不带有ZAddParams属性，类似于新版本ch，但是返回数量老版本只返回新增数量，新版本返回新增和修改总数
     *
     * @param key
     * @param scoreList
     * @param memberList
     */
    public void clansZadd(RedisKey key, List<Double> scoreList, List<String> memberList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("上传公会活跃度排行数据获取jedis=null");
                return;
            }
            // 上传新的公会活跃值内容
            Pipeline pipelined = jedis.pipelined();
            for (int i = 0, len = scoreList.size(); i < len; i++) {
                pipelined.zadd(key.getKey(), scoreList.get(i), memberList.get(i), ZAddParams.zAddParams().ch());
            }
            pipelined.sync();
        } catch (Exception e) {
            log.error("上传公会活跃度排行数据异常 type=" + key.getKey(), e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 移除公会活跃度排行
     *
     * @param key
     * @param member
     * @return
     */
    public long clansActiveZrem(RedisKey key, String member) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("移除公会活跃度排行key=" + key.getKey() + " jedis=null");
                return 0l;
            }
            // 如果新元素被移除，则为1;如果新元素不是集合的成员，则为0
            long result = jedis.zrem(key.getKey(), member);
            return result;
        } catch (Exception e) {
            log.error("移除公会活跃度排行异常 type=" + key);
            return 0l;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 下载公会活跃度排行数据（根据角色当前的公会活跃度排行条数，下载此条数之前的10条记录）
     *
     * @param key
     * @param num 角色当前已有的公会活跃度排行条数
     * @return
     */
    public List<Tuple> clansActiveZrevrange(RedisKey key, long num) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("下载公会活跃度排行获取jedis=null");
                return Collections.emptyList();
            }
            // 惨痛教训，redis向后超出不足不会出问题，向前成为负数会出现一个都找不到的bug
            if (num < 0) {
                num = 0;
            }
            List<Tuple> chatList = jedis.zrevrangeWithScores(key.getKey(), num, num + 9);
            return chatList;
        } catch (Exception e) {
            log.error("下载公会活跃度排行异常 type=" + key);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新公会副本数据
     *
     * @param clansId
     * @param data
     */
    public void clansMapSet(long clansId, byte[] data) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新公会副本获取jedis=null clansId=" + clansId);
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            pipelined.set((RedisKey.CLANS_MAP.getKey() + clansId).getBytes(), data);
            pipelined.hset(RedisKey.CLANS_MAP_VERSION_MAP.getKey(), Long.toString(clansId), Long.toString(ID.getId()));
//			log.info("缓存公会副本数据clansId=" + clansId + " result=" + result);
            pipelined.sync();
        } catch (Exception e) {
            log.error("上传最新公会副本数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 批量上传最新公会副本数据
     *
     * @param clansIdList
     * @param dataList
     */
    public void clansMapSet(List<Long> clansIdList, List<byte[]> dataList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("批量缓存最新公会副本获取jedis=null clansId=");
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            int index = 0;
            for (int i = 0, len = clansIdList.size(); i < len; i++) {
                Long clansId = clansIdList.get(i);
                byte[] data = dataList.get(i);
                pipelined.set((RedisKey.CLANS_MAP.getKey() + clansId).getBytes(), data);
                pipelined.hset(RedisKey.CLANS_MAP_VERSION_MAP.getKey(), Long.toString(clansId), Long.toString(ID.getId()));
                index++;
                if (index >= 100) {
                    pipelined.sync();
                    index = 0;
                }
            }
            if (index > 0) {
                pipelined.sync();
            }
//			log.info("缓存公会副本数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("批量上传最新公会副本数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会副本数据是否存在
     *
     * @param clansId
     * @return
     */
    public boolean clansMapIsExist(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会副本数据是否存在获取jedis=null clansId=" + clansId);
                return false;
            }
            boolean isExist = jedis.exists((RedisKey.CLANS_MAP.getKey() + clansId).getBytes());
            return isExist;
        } catch (Exception e) {
            log.error("获取缓存公会副本数据是否存在异常", e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会副本数据
     *
     * @param clansId
     * @return
     */
    public ClansMap clansMapGet(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会副本获取jedis=null clansId=" + clansId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.CLANS_MAP.getKey() + clansId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取公会副本数据不存在 clansId=" + clansId);
                return null;
            }
            ClansMap clansMapData = ClansMapDbUtils.parseFromClansMap(DataZipUtil.dataDecode(data));
//			log.info("从redis获取公会副本数据clansId=" + clansId);
            return clansMapData;
        } catch (Exception e) {
            log.error("获取缓存公会副本异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新公会副本日志数据
     *
     * @param clansId
     * @param data
     */
    public void clansMapLogSet(long clansId, byte[] data) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新公会副本日志获取jedis=null clansId=" + clansId);
                return;
            }
            jedis.set((RedisKey.CLANS_MAP_LOG.getKey() + clansId).getBytes(), data);
//			log.info("缓存公会副本日志数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("上传最新公会副本日志数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 批量上传最新公会副本日志数据
     *
     * @param clansIdList
     * @param dataList
     */
    public void clansMapLogSet(List<Long> clansIdList, List<byte[]> dataList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("批量缓存最新公会副本日志获取jedis=null clansId=");
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            int index = 0;
            for (int i = 0, len = clansIdList.size(); i < len; i++) {
                Long clansId = clansIdList.get(i);
                byte[] data = dataList.get(i);
                pipelined.set((RedisKey.CLANS_MAP_LOG.getKey() + clansId).getBytes(), data);
                index++;
                if (index >= 100) {
                    pipelined.sync();
                    index = 0;
                }
            }
            if (index > 0) {
                pipelined.sync();
            }
//			log.info("缓存公会副本日志数据clansId=" + clansId + " result=" + result);
        } catch (Exception e) {
            log.error("批量上传最新公会副本日志数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会副本日志数据是否存在
     *
     * @param clansId
     * @return
     */
    public boolean clansMapLogIsExist(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会副本日志数据是否存在获取jedis=null clansId=" + clansId);
                return false;
            }
            boolean isExist = jedis.exists((RedisKey.CLANS_MAP_LOG.getKey() + clansId).getBytes());
            return isExist;
        } catch (Exception e) {
            log.error("获取缓存公会副本日志数据是否存在异常", e);
            return false;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存公会副本日志数据
     *
     * @param clansId
     * @return
     */
    public ClansMapLog clansMapLogGet(long clansId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存公会副本日志获取jedis=null clansId=" + clansId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.CLANS_MAP_LOG.getKey() + clansId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取公会副本日志数据不存在 clansId=" + clansId);
                return null;
            }
            ClansMapLog clansMapLogData = ClansMapLogDbUtils.parseFromClansMapLog(DataZipUtil.dataDecode(data));
//			log.info("从redis获取公会副本日志数据clansId=" + clansId);
            return clansMapLogData;
        } catch (Exception e) {
            log.error("获取缓存公会副本日志异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新玩家好友数据
     *
     * @param playerId
     * @param data
     */
    public void friendSet(long playerId, byte[] data) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新friend获取jedis=null player=" + playerId);
                return;
            }
            jedis.set((RedisKey.FRIEND_PLAYER.getKey() + playerId).getBytes(), data);
//			log.info("缓存玩家好友数据(老覆盖)player=" + playerId + " result=" + result);
        } catch (Exception e) {
            log.error("上传最新玩家好友数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存friend数据
     *
     * @param playerId
     * @return
     */
    public FriendPublicData friendGet(long playerId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存friend获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.FRIEND_PLAYER.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取展示玩家好友数据不存在 playerId=" + playerId);
                return null;
            }
            FriendPublicData friendPublicData = FriendPublicDataDbUtils.parseFromFriendPublicData(data);
//			log.info("从redis获取展示玩家好友数据player=" + playerId);
            return friendPublicData;
        } catch (Exception e) {
            log.error("获取缓存friend异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 从redis去除日志
     *
     * @param count 取出日志数量
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> logBlpop(int count) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取日志jedis=null");
            }
            // 获取指定数量日志，不够阻塞队列
            List<String> logList = new ArrayList<>();
            Long size = jedis.llen(RedisKey.MDT_LOG.getKey());
            if (size == 0) {
                return logList;
            }
            if (size.longValue() < count) {
                count = size.intValue();
            }
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastLogNumTime > TimeUtil.MIN_MILLIS) {
                log.info("本次处理日志数：" + count + " 目前剩余待处理日志数：" + (size.longValue() - count) + " 剩余日志预计处理还需时间：" + (size.longValue() / MyDefineConstant.REDIS_LOG_NUM * MyDefineConstant.REDIS_LOG_DELAY / TimeUtil.MILLIS) + "秒");
                lastLogNumTime = currentTime;
            }
            Pipeline pipelined = jedis.pipelined();
            for (int i = 0; i < count; i++) {
                // 设置2秒超时，取不出来有可能被别的进程取出，这里直接超时不处理
                pipelined.blpop(2, RedisKey.MDT_LOG.getKey());
            }
            List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
            for (int i = 0, len = syncAndReturnAll.size(); i < len; i++) {
                List<String> blpop = (List<String>) syncAndReturnAll.get(i);
                if (blpop != null && blpop.size() == 2) {
                    logList.add(blpop.get(1));
                }
            }
            return logList;
        } catch (Exception e) {
            log.error("获取日志数据", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新玩家公共日志数据
     *
     * @param playerId
     * @param content
     */
    public void logParamsSet(Long playerId, String content) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("上传最新玩家公共日志数据获取jedis=null player=" + playerId);
                return;
            }
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒,设置两周过期时间，不设置就是CH
            jedis.set(RedisKey.LOG_PLAYER_PARAMS.getKey() + playerId, content);
        } catch (Exception e) {
            log.error("上传最新玩家公共日志数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取最新玩家公共日志数据
     *
     * @param playerId
     * @return
     */
    public String logParamsGet(long playerId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家背包数据获取jedis=null playerId=" + playerId);
                return null;
            }
            String content = jedis.get(RedisKey.LOG_PLAYER_PARAMS.getKey() + playerId);
            // 为空表示没有这个key || key里面没有这个field
            if (content == null || content.length() == 0) {
                log.error("获取最新玩家公共日志数据不存在 playerId=" + playerId);
                return null;
            }
            return content;
        } catch (Exception e) {
            log.error("获取最新玩家公共日志数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新player(覆盖)
     *
     * @param player
     */
    public void playerSet(Player player) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("缓存最新player获取jedis=null player=" + player.getPlayerId());
                return;
            }
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒,设置两周过期时间，不设置就是CH
            jedis.set((RedisKey.PLAYER.getKey() + player.getPlayerId()).getBytes(), player.getSaveData(), SetParams.setParams().ex(TimeUtil.DOUBLE_WEEK_SEC));
        } catch (Exception e) {
            log.error("缓存最新player异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新player(批量覆盖)（关服调用）
     *
     * @param playerList
     */
    public void playerSet(List<Player> playerList) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("批量缓存最新player获取jedis=null playerNum=" + playerList.size());
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            int index = 0;
            for (int i = 0, len = playerList.size(); i < len; i++) {
                Player player = playerList.get(i);
                byte[] playerData = PlayerDbUtils.buildPlayerBean(player).toByteArray();
                byte[] saveData = DataZipUtil.dataEncode(playerData);
                player.setSaveData(saveData);
                // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒,设置两周过期时间，不设置就是CH
                pipelined.set((RedisKey.PLAYER.getKey() + player.getPlayerId()).getBytes(), saveData, SetParams.setParams().ex(TimeUtil.DOUBLE_WEEK_SEC).xx());
                index++;
                if (index >= 20) {
                    pipelined.sync();
                    index = 0;
                }
            }
            if (index > 0) {
                pipelined.sync();
            }
        } catch (Exception e) {
            log.error("缓存最新player异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存player（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public Player playerGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存player获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家数据不存在 playerId=" + playerId);
                return null;
            }
            Player player = PlayerDbUtils.parseFromPlayer(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家数据player=" + player.getPlayerId().longValue());
            return player;
        } catch (Exception e) {
            log.error("获取缓存player异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新玩家其他数据(覆盖)
     *
     * @param playerOther
     */
    public void playerOtherSet(PlayerOther playerOther) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("上传最新玩家其他数据(覆盖)获取jedis=null player=" + playerOther.getPlayerId());
                return;
            }
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒,设置两周过期时间，不设置就是CH
            jedis.set((playerOther.getRedisKey().getKey() + playerOther.getPlayerId()).getBytes(), playerOther.getSaveData(), SetParams.setParams().ex(TimeUtil.DOUBLE_WEEK_SEC));
        } catch (Exception e) {
            log.error("上传最新玩家其他数据(覆盖)异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 上传最新玩家其他数据(批量覆盖)（关服调用）
     *
     * @param playerOtherList 存储玩家其他数据列表
     */
    public void playerOtherSet(List<? extends PlayerOther> playerOtherList) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("批量缓存最新玩家其他数据获取jedis=null playerNum=" + playerOtherList.size());
                return;
            }
            Pipeline pipelined = jedis.pipelined();
            int index = 0;
            for (int i = 0; i < playerOtherList.size(); i++) {
                PlayerOther playerOther = playerOtherList.get(i);
                // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒,设置两周过期时间，不设置就是CH
                pipelined.set((playerOther.getRedisKey().getKey() + playerOther.getPlayerId()).getBytes(), playerOther.getSaveData(), SetParams.setParams().ex(TimeUtil.DOUBLE_WEEK_SEC).xx());
                index++;
                if (index >= 20) {
                    pipelined.sync();
                    index = 0;
                }
            }
            if (index > 0) {
                pipelined.sync();
            }
        } catch (Exception e) {
            log.error("缓存最新玩家其他数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家副本数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerMap playerMapGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家副本数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_MAP.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家副本数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerMap playerMap = PlayerMapDbUtils.parseFromPlayerMap(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家副本数据player=" + playerMap.getPlayerId().longValue());
            return playerMap;
        } catch (Exception e) {
            log.error("获取缓存玩家副本数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家英雄数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerHero playerHeroGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家英雄数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_HERO.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家英雄数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerHero playerHero = PlayerHeroDbUtils.parseFromPlayerHero(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家英雄数据player=" + playerHero.getPlayerId().longValue());
            return playerHero;
        } catch (Exception e) {
            log.error("获取缓存玩家英雄数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家背包数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerPack playerPackGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家背包数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_PACK.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家背包数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerPack playerPack = PlayerPackDbUtils.parseFromPlayerPack(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家背包数据player=" + playerPack.getPlayerId().longValue());
            return playerPack;
        } catch (Exception e) {
            log.error("获取缓存玩家背包数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家商店数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerShop playerShopGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家商店数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_SHOP.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家商店数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerShop playerShop = PlayerShopDbUtils.parseFromPlayerShop(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家商店数据player=" + playerShop.getPlayerId().longValue());
            return playerShop;
        } catch (Exception e) {
            log.error("获取缓存玩家商店数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家任务数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerQuest playerQuestGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家任务数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_QUEST.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家任务数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerQuest playerQuest = PlayerQuestDbUtils.parseFromPlayerQuest(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家任务数据player=" + playerQuest.getPlayerId().longValue());
            return playerQuest;
        } catch (Exception e) {
            log.error("获取缓存玩家任务数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家活动数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerActivity playerActivityGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家活动数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_ACTIVITY.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家活动数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerActivity playerActivity = PlayerActivityDbUtils.parseFromPlayerActivity(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家活动数据player=" + playerActivity.getPlayerId().longValue());
            return playerActivity;
        } catch (Exception e) {
            log.error("获取缓存玩家活动数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家好友数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerFriend playerFriendGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家好友数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_FRIEND.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家好友数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerFriend playerFriend = PlayerFriendDbUtils.parseFromPlayerFriend(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家好友数据player=" + playerFriend.getPlayerId().longValue());
            return playerFriend;
        } catch (Exception e) {
            log.error("获取缓存玩家好友数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家充值数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerPay playerPayGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家充值数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_PAY.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家充值数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerPay playerPay = PlayerPayDbUtils.parseFromPlayerPay(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家充值数据player=" + playerPay.getPlayerId().longValue());
            return playerPay;
        } catch (Exception e) {
            log.error("获取缓存玩家充值数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存玩家聊天数据（只有当本地缓存中找不到此player，才会来redis中获取）
     *
     * @param playerId
     * @return
     */
    public PlayerChat playerChatGet(long playerId) {
        Jedis jedis = getServerJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存玩家聊天数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_CHAT.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("从redis获取玩家聊天数据不存在 playerId=" + playerId);
                return null;
            }
            PlayerChat playerChat = PlayerChatDbUtils.parseFromPlayerChat(DataZipUtil.dataDecode(data));
//			log.info("从redis获取玩家聊天数据player=" + playerChat.getPlayerId().longValue());
            return playerChat;
        } catch (Exception e) {
            log.error("获取缓存玩家聊天数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 保存simplePlayer数据
     *
     * @param simplePlayer
     */
    public void simplePlayerSet(SimplePlayer simplePlayer) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("保存simplePlayer数据获取jedis=null");
                return;
            }
            // 更新SimplePlayer数据
            DbProto.SimplePlayerBean simplePlayerBean = SimplePlayerDbUtils.buildSimplePlayerBean(simplePlayer);
            jedis.set((RedisKey.PLAYER_SIMPLE.getKey() + simplePlayerBean.getPlayerId()).getBytes(), simplePlayerBean.toByteArray());
        } catch (Exception e) {
            log.error("保存simplePlayer数据异常", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存simplePlayer数据
     *
     * @param playerId
     * @return
     */
    public SimplePlayer simplePlayerGet(long playerId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存simplePlayer数据获取jedis=null playerId=" + playerId);
                return null;
            }
            byte[] data = jedis.get((RedisKey.PLAYER_SIMPLE.getKey() + playerId).getBytes());
            // 为空表示没有这个key || key里面没有这个field
            if (data == null || data.length == 0) {
                log.error("获取缓存simplePlayer数据不存在 playerId=" + playerId);
                return null;
            }
            return SimplePlayerDbUtils.parseFromSimplePlayer(data);
        } catch (Exception e) {
            log.error("获取缓存simplePlayer数据异常", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存simplePlayer数据
     *
     * @param playerIdList
     * @return
     */
    public Map<Long, SimplePlayer> simplePlayerGetMap(List<Long> playerIdList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存simplePlayer数据获取jedis=null");
                return null;
            }
            Pipeline pipelined = jedis.pipelined();
            for (int i = 0, len = playerIdList.size(); i < len; i++) {
                Long playerId = playerIdList.get(i);
                pipelined.get((RedisKey.PLAYER_SIMPLE.getKey() + playerId).getBytes());
            }
            Map<Long, SimplePlayer> simplePlayerMap = new LinkedHashMap<>();
            List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
            for (int i = 0; i < syncAndReturnAll.size(); i++) {
                byte[] data = (byte[]) syncAndReturnAll.get(i);
                if (data == null || data.length == 0) {
                    continue;
                }
                SimplePlayer simplePlayer = SimplePlayerDbUtils.parseFromSimplePlayer(data);
                if (simplePlayer != null) {
                    simplePlayerMap.put(simplePlayer.getPlayerId(), simplePlayer);
                }
            }
            return simplePlayerMap;
        } catch (Exception e) {
            log.error("获取缓存simplePlayer数据异常", e);
            return Collections.emptyMap();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存simplePlayer数据
     *
     * @param playerIdList 里面可能会存在为0的参数，是为了占位
     * @return simplePlayerList 里面有0占位
     */
    public List<SimplePlayer> simplePlayerGetList(List<Long> playerIdList) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("获取缓存simplePlayer数据获取jedis=null");
                return null;
            }
            List<SimplePlayer> simplePlayerList = new ArrayList<>();
            Pipeline pipelined = jedis.pipelined();
            for (int i = 0, len = playerIdList.size(); i < len; i++) {
                Long playerId = playerIdList.get(i);
                simplePlayerList.add(null);
                if (playerId != 0) {
                    pipelined.get((RedisKey.PLAYER_SIMPLE.getKey() + playerId).getBytes());
                }
            }
            List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
            for (int i = 0; i < syncAndReturnAll.size(); i++) {
                byte[] data = (byte[]) syncAndReturnAll.get(i);
                if (data == null || data.length == 0) {
                    continue;
                }
                SimplePlayer simplePlayer = SimplePlayerDbUtils.parseFromSimplePlayer(data);
                if (simplePlayer != null) {
                    int index = playerIdList.indexOf(simplePlayer.getPlayerId());
                    simplePlayerList.set(index, simplePlayer);
                }
            }
            return simplePlayerList;
        } catch (Exception e) {
            log.error("获取缓存simplePlayer数据异常", e);
            return Collections.emptyList();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 更新此玩家名字下的id数据
     *
     * @param key
     * @param oldName
     * @param newName
     * @param playerId
     */
    public void playerNameIdUpdate(RedisKey key, String oldName, String newName, Long playerId) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("更新此玩家名字下的id数据获取jedis=null");
                return;
            }
            // 这里不严谨使用hashcode，极小概率相同也无所谓
            if (!oldName.isEmpty()) {
                // 如果移除最后一个元素会自动清除key
                jedis.srem(key.getKey() + oldName.hashCode(), playerId.toString());
            }
            jedis.sadd(key.getKey() + newName.hashCode(), playerId.toString());
        } catch (Exception e) {
            log.error("更新此玩家名字下的id数据", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 返回此玩家名字下的所有id数据
     *
     * @param key
     * @param playerName
     * @return
     */
    public Set<String> playerNameIdSmembers(RedisKey key, String playerName) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("返回此玩家名字下的所有id数据获取jedis=null");
                return Collections.emptySet();
            }
            // 这里不严谨使用hashcode，极小概率相同也无所谓
            return jedis.smembers(key.getKey() + playerName.hashCode());
        } catch (Exception e) {
            log.error("返回此玩家名字下的所有id数据", e);
            return Collections.emptySet();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取玩家上一次登录的逻辑服id，方便快速找到玩家，通知消息，如果不在线也无所谓，会有逻辑处理
     *
     * @param playerId
     * @return
     */
    public Integer getPlayerLastLogicServerId(long playerId) {
        try {
            // 获取玩家上一次登录的逻辑服务器
            byte[] datas = stringGet(getCenterJedis(), RedisKey.PLAYER_LOGIC_SERVER_ID, null, Long.toString(playerId));
            if (datas == null) {
                log.error("找不到玩家上一次登陆逻辑服务器信息，playerId=" + playerId);
                return 0;
            }
            return Integer.parseInt(new String(datas, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("获取玩家上一次登录的逻辑服id，方便快速找到玩家，通知消息，如果不在线也无所谓，会有逻辑处理异常", e);
            return 0;
        }
    }

    /**
     * 获取玩家上一次登录的房间服id，方便快速找到玩家，通知消息，如果不在线也无所谓，会有逻辑处理
     *
     * @param playerId
     * @return
     */
    public Integer getPlayerLastRoomServerId(long playerId) {
        try {
            // 获取玩家上一次登录的逻辑服务器
            byte[] datas = stringGet(getCenterJedis(), RedisKey.PLAYER_ROOM_SERVER_ID, null, Long.toString(playerId));
            if (datas == null) {
                log.error("找不到玩家上一次登陆房间服务器信息，playerId=" + playerId);
                return 0;
            }
            return Integer.parseInt(new String(datas, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("获取玩家上一次登录的房间服id，方便快速找到玩家，通知消息，如果不在线也无所谓，会有逻辑处理异常", e);
            return 0;
        }
    }

    /**
     * 获取玩家上一次进入的房间id，方便快速找到玩家
     *
     * @param playerId
     * @return
     */
    public Integer getPlayerLastRoomId(long playerId) {
        try {
            // 获取玩家上一次登录的逻辑服务器
            byte[] datas = stringGet(getCenterJedis(), RedisKey.PLAYER_ROOM_ID, null, Long.toString(playerId));
            if (datas == null) {
                log.error("找不到玩家上一次进入房间信息，playerId=" + playerId);
                return 0;
            }
            return Integer.parseInt(new String(datas, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("获取玩家上一次进入的房间id，方便快速找到玩家异常", e);
            return 0;
        }
    }

    /**
     * redis往hash结构中存储key和value
     *
     * @param redisKey
     * @param key
     * @param value
     */
    public void mapSet(RedisKey redisKey, String key, String value) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("redis往hash结构中存储key和value，数据获取jedis=null");
                return;
            }
            jedis.hset(redisKey.getKey(), key, value);
        } catch (Exception e) {
            log.error("redis往hash结构中存储key和value", e);
            return;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * redis获取hash结构中存储value
     *
     * @param redisKey
     * @param key
     * @return
     */
    public String mapGet(RedisKey redisKey, String key) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error("redis获取hash结构中存储value，数据获取jedis=null");
                return null;
            }
            return jedis.hget(redisKey.getKey(), key);
        } catch (Exception e) {
            log.error("redis获取hash结构中存储value", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * redis获取hash结构中所有存储value
     *
     * @param redisKey
     * @return
     */
    public Map<String, String> mapGetAll(RedisKey redisKey) {
        Jedis jedis = getCenterJedis();
        try {
            if (jedis == null) {
                log.error(" redis获取hash结构中所有存储value，数据获取jedis=null");
                return null;
            }
            return jedis.hgetAll(redisKey.getKey());
        } catch (Exception e) {
            log.error(" redis获取hash结构中所有存储value", e);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 清空整个redis数据库数据（慎用）(提供给工具调用)
     */
    public String flushAll() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, JedisPool> stringJedisPoolEntry : getRedisPoolMap().entrySet()) {
            String key = stringJedisPoolEntry.getKey();
            JedisPool value = stringJedisPoolEntry.getValue();
            Jedis jedis = getJedis(key);
            try {
                if (jedis == null) {
                    log.error("清空整个数据库数据（慎用）jedis=null");
                    sb.append("清空整个数据库数据（慎用）jedis=null");
                    return sb.toString();
                }
                long keyCount = keyCount(jedis, false);
                log.info("清空前redis数据库[" + key + "]key数量：" + keyCount);
                sb.append("清空前redis数据库[" + key + "]key数量：" + keyCount).append(System.lineSeparator());
                jedis.flushAll();
                keyCount = keyCount(jedis, false);
                log.info("清空后redis数据库[" + key + "]key数量：" + keyCount);
                sb.append("清空后redis数据库[" + key + "]key数量：" + keyCount).append(System.lineSeparator());
            } catch (Exception e) {
                log.error("清空整个数据库数据（慎用）异常: ", e);
                sb.append("清空整个数据库数据（慎用）异常: " + e.getMessage()).append(System.lineSeparator());
                return sb.toString();
            } finally {
                returnResource(jedis);
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前redis所有key数量(提供给工具调用)
     */
    public long keyCount(Jedis jedis, boolean returnResource) {
        try {
            if (jedis == null) {
                log.error("清获取当前redis所有key数量jedis=null");
                return 0;
            }
            return jedis.dbSize();
//            int count = 0;
//            String cursor = ScanParams.SCAN_POINTER_START;
//            ScanParams scanParams = new ScanParams().count(100); // 每次扫描的数量
//            ScanResult<String> scanResult;
//            do {
//                scanResult = jedis.scan(cursor, scanParams);
//                count += scanResult.getResult().size(); // 累加当前批次的key数量
//                cursor = scanResult.getCursor(); // 更新游标
//            } while (!scanResult.isCompleteIteration()); // 当游标返回0时，代表迭代完成
        } catch (Exception e) {
            log.error("获取当前redis所有key数量异常: ", e);
            return 0;
        } finally {
            if (returnResource) {
                returnResource(jedis);
            }
        }
    }

}
