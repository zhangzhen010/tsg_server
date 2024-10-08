package com.game.redis.structs;

/**
 * redis客户端配置
 * 
 * @author zhangzhen
 * @time 2020年4月22日
 */
public class RedisClientConfig {

	// rediskey
	private String key;
	// Redis服务器IP
	private String ip;
	// redis端口
	private int port;
	// redis身份验证 密码
	private String auth;
	// 最大连接数,最大jedis实例， 默认8个
	private int maxTotal;
	// 最大空闲连接数, 默认8个(控制一个pool最多有多少个状态为idle(空闲的)的jedis实例)
	private int maxIdle;
	// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
	private boolean blockWhenExhausted;
	// 获取连接时jedis池没有对象返回时，的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常,
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private int maxWaitMillis;
	// 连接超时
	private int timeout;
	// 在获取连接的时候检查有效性, 默认false
	private boolean testOnBorrow;
	// 调用returnObject方法时，是否进行有效检查
	private boolean testOnReturn;
	// 是否显示reids内存信息
	private boolean showMemoryInfo;

	// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
	private String evictionPolicyClassName;
	// 是否启用pool的jmx管理功能, 默认true
	private boolean jmxEnabled;
	// MBean ObjectName = new
	// ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" +
	// "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
	private String jmxNamePrefix;
	// 是否启用后进先出, 默认true
	private boolean setLifo;
	// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
	private int minEvictableIdleTimeMillis;
	// 最小空闲连接数, 默认0
	private int minIdle;
	// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
	private int numTestsPerEvictionRun;
	// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断
	// (默认逐出策略)
	private int softMinEvictableIdleTimeMillis;
	// 在空闲时检查有效性, 默认false
	private boolean testWhileIdle;
	// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
	private int timeBetweenEvictionRunsMillis;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public boolean isBlockWhenExhausted() {
		return blockWhenExhausted;
	}

	public void setBlockWhenExhausted(boolean blockWhenExhausted) {
		this.blockWhenExhausted = blockWhenExhausted;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isShowMemoryInfo() {
		return showMemoryInfo;
	}

	public void setShowMemoryInfo(boolean showMemoryInfo) {
		this.showMemoryInfo = showMemoryInfo;
	}

	public String getEvictionPolicyClassName() {
		return evictionPolicyClassName;
	}

	public void setEvictionPolicyClassName(String evictionPolicyClassName) {
		this.evictionPolicyClassName = evictionPolicyClassName;
	}

	public boolean isJmxEnabled() {
		return jmxEnabled;
	}

	public void setJmxEnabled(boolean jmxEnabled) {
		this.jmxEnabled = jmxEnabled;
	}

	public String getJmxNamePrefix() {
		return jmxNamePrefix;
	}

	public void setJmxNamePrefix(String jmxNamePrefix) {
		this.jmxNamePrefix = jmxNamePrefix;
	}

	public boolean isSetLifo() {
		return setLifo;
	}

	public void setSetLifo(boolean setLifo) {
		this.setLifo = setLifo;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public int getSoftMinEvictableIdleTimeMillis() {
		return softMinEvictableIdleTimeMillis;
	}

	public void setSoftMinEvictableIdleTimeMillis(int softMinEvictableIdleTimeMillis) {
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

}
