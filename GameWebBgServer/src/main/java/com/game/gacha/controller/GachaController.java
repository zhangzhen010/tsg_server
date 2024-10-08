package com.game.gacha.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.config.CurrentUser;
import com.game.controller.structs.ResponseBean;
import com.game.datagroup.structs.DataGroup;
import com.game.datagroup.structs.DataGroupType;
import com.game.datagroup.structs.GachaPoolAnimationData;
import com.game.datagroup.structs.SolanaMintQueueData;
import com.game.gacha.structs.ReqMintCardInfo;
import com.game.gacha.structs.ReqUploadGachaPoolImageInfo;
import com.game.player.structs.*;
import com.game.solana.manager.SolanaManager;
import com.game.solana.timer.SolanaAddNftMintQueueTimer;
import com.game.solana.timer.SolanaCancelNftMintQueueTimer;
import com.game.thread.manager.ThreadManager;
import com.game.user.structs.WebBgUser;
import com.game.utils.ID;
import com.game.utils.StringUtil;
import com.mongodb.client.result.DeleteResult;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台Gacha设置
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsgbg/gacha")
public class GachaController {

    private @Resource MongoTemplate mongoTemplate;
    private @Resource ThreadManager threadManager;
    private @Resource SolanaManager solanaManager;

    /**
     * 卡池离散数据
     */
    private DataGroup dataGroupGachaPool;


    @PostConstruct
    public void init() {
        dataGroupGachaPool = mongoTemplate.findOne(new Query(Criteria.where("id").is(DataGroupType.GACHA_POOL)), DataGroup.class);
        if (dataGroupGachaPool == null) {
            dataGroupGachaPool = new DataGroup();
            dataGroupGachaPool.setId(DataGroupType.GACHA_POOL);
            mongoTemplate.insert(dataGroupGachaPool);
            log.info("初始化卡池离散数据成功！");
        }
        // 测试数据
        GachaPool gachaPool = new GachaPool();
        gachaPool.setId(Long.toString(ID.getId()));
        gachaPool.setName("testName");
        gachaPool.setRecommend(true);
        gachaPool.setDescription("test");
        gachaPool.getImageUrlList().add("https://www.baidu.com/img/bd_logo1.png");
        gachaPool.getImageUrlList().add("https://www.baidu.com/img/bd_logo1.png");
        gachaPool.setAnimationUrl("https://www.baidu.com/img/bd_logo1.png");
        gachaPool.setStartTime(System.currentTimeMillis());
        gachaPool.setEndTime(System.currentTimeMillis() + 1000000000);
        gachaPool.setCandy(100);
        GachaPoolWeight gachaPoolWeight1 = new GachaPoolWeight();
        gachaPoolWeight1.setWeight(10);
        gachaPoolWeight1.setQuality("SSR");
        gachaPoolWeight1.setBurnCandyRatio(50);
        gachaPoolWeight1.setBurnFtRatio(10);
        gachaPoolWeight1.getCardInfoList().add(new GachaPoolWeightCardInfo("11111111111111", 5));
        gachaPool.getWeightList().add(gachaPoolWeight1);
        gachaPoolWeight1 = new GachaPoolWeight();
        gachaPoolWeight1.setWeight(20);
        gachaPoolWeight1.setQuality("SR");
        gachaPoolWeight1.setBurnCandyRatio(50);
        gachaPoolWeight1.setBurnFtRatio(10);
        gachaPoolWeight1.getCardInfoList().add(new GachaPoolWeightCardInfo("22222222222222", 5));
        gachaPool.getWeightList().add(gachaPoolWeight1);
//        System.out.println("GachaPool 示例JSON : " + JSON.toJSONString(gachaPool));

        GachaCardTemplate gachaCardTemplate = new GachaCardTemplate();
        gachaCardTemplate.setId(Long.toString(ID.getId()));
        gachaCardTemplate.setName("testName");
        gachaCardTemplate.setDescription("test");
        gachaCardTemplate.setUsd(10000);
        gachaCardTemplate.setImage("https://www.baidu.com/img/bd_logo1.png");
        gachaCardTemplate.getAttributes().add(new CardAttribute("level", "1"));
        gachaCardTemplate.getAttributes().add(new CardAttribute("quality", "SSR"));
//        System.out.println("GachaCardTemplate 示例JSON : " + JSON.toJSONString(gachaCardTemplate));
    }

    /**
     * 获取所有卡池
     */
    @GetMapping("/gachaPoolList")
    public ResponseBean<Object> gachaPoolList(@CurrentUser WebBgUser user) {
        try {
            List<GachaPool> poolList = mongoTemplate.findAll(GachaPool.class);
            return ResponseBean.success(poolList);
        } catch (Exception e) {
            log.error("获取所有卡池异常：", e);
            return ResponseBean.fail("gachaPoolList error" + e.getMessage());
        }
    }

    /**
     * 新增卡池
     *
     * @param user
     * @param gachaPool
     * @return
     */
    @PostMapping("/addGachaPool")
    public ResponseBean<Object> addGachaPool(@CurrentUser WebBgUser user, @RequestBody GachaPool gachaPool) {
        try {
            // 设置唯一id
            gachaPool.setId(Long.toString(ID.getId()));
            // 检查本次更新的卡片是否已经被绑定（其他卡池绑定 or 已被玩家抽取 or 已被销毁）
            for (GachaPoolWeight gachaPoolWeight : gachaPool.getWeightList()) {
                for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                    // 查询此类型卡片剩余数量
                    Query query = new Query();
                    query.addCriteria(Criteria.where("gachaCardTemplateId").is(entry.getCardTemplateId()));
                    // 卡片为非销毁状态的卡片才更新绑定卡池
                    query.addCriteria(Criteria.where("burn").is(false));
                    // 排除已被抽走的卡片
                    query.addCriteria(Criteria.where("ownerPlayerId").is(0));
                    // 排除已被其他卡池选中的卡片
                    query.addCriteria(Criteria.where("gachaPoolId").is(0));
                    List<GachaCard> gachaCardList = mongoTemplate.find(query, GachaCard.class);
                    if (entry.getNum() > gachaCardList.size()) {
                        log.error("新增卡池失败，卡牌数量不足 需要的数量:" + entry.getNum() + " 剩余数量：" + gachaCardList.size());
                        return ResponseBean.fail("updateGachaPool error, card no enough");
                    }
                    // 这里gachaCardList的数量大于entry.getValue()的数量，前面有验证，其他多线程先不考虑，先实现功能，性能也后面再来优化
                    for (int i = 0; i < entry.getNum(); i++) {
                        query = new Query(Criteria.where("id").is(gachaCardList.get(i).getId()));
                        Update updateBind = new Update();
                        updateBind.set("gachaPoolId", gachaPool.getId());
                        mongoTemplate.updateFirst(query, updateBind, GachaCard.class);
                    }
                }
            }
            mongoTemplate.insert(gachaPool);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("新增卡池异常：", e);
            return ResponseBean.fail("addGachaPool error" + e.getMessage());
        }
    }

    /**
     * 删除卡池
     */
    @PostMapping("/deleteGachaPool")
    public ResponseBean<Object> deleteGachaPool(@CurrentUser WebBgUser user, @RequestBody String id) {
        try {
            DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("id").is(id)), GachaPool.class);
            if (result.getDeletedCount() > 0) {
                return ResponseBean.success();
            } else {
                return ResponseBean.fail("delete error id:" + id);
            }
        } catch (Exception e) {
            log.error("删除卡池异常：", e);
            return ResponseBean.fail("deleteGachaPool error" + e.getMessage());
        }
    }

    /**
     * 更新卡池
     *
     * @param user
     * @param gachaPool
     * @return
     */
    @PostMapping("/updateGachaPool")
    public ResponseBean<Object> updateGachaPool(@CurrentUser WebBgUser user, @RequestBody GachaPool gachaPool) {
        try {
            GachaPool oldGachaPool = mongoTemplate.findOne(new Query(Criteria.where("id").is(gachaPool.getId())), GachaPool.class);
            if (oldGachaPool == null) {
                log.error("更新卡池，卡池不存在 id:" + gachaPool.getId());
                return ResponseBean.fail("updateGachaPool id no exist id:" + gachaPool.getId());
            }
            // 当前时间
            long currentTime = System.currentTimeMillis();
            // 必须在卡池被关闭的情况下才能修改卡池，防止后台在更新，玩家正在抽卡
            if (oldGachaPool.getStartTime() <= currentTime && currentTime <= oldGachaPool.getEndTime()) {
                log.error("更新卡池失败，卡池还未关闭 id:" + oldGachaPool.getId());
                return ResponseBean.fail("updateGachaPool error, gachaPool is running");
            }
            // 统计本次修改的卡片数量
            Map<String, Integer> newCardNumMap = new HashMap<>();
            for (GachaPoolWeight gachaPoolWeight : gachaPool.getWeightList()) {
                for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                    newCardNumMap.put(entry.getCardTemplateId(), entry.getNum());
                }
            }
            // 统计当前的卡片数量
            Map<String, Integer> oldCardNumMap = new HashMap<>();
            for (GachaPoolWeight gachaPoolWeight : oldGachaPool.getWeightList()) {
                for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                    oldCardNumMap.put(entry.getCardTemplateId(), entry.getNum());
                }
            }
            // 本次更新是否需要修改卡池卡片信息
            boolean updateCard = !JSON.toJSONString(newCardNumMap).equals(JSON.toJSONString(oldCardNumMap));
            log.info("修改抽卡卡池信息：本次是否修改卡池卡片信息：" + updateCard);
            // 记录不同的模板需要新增的卡片数据 key=模板唯一id value=可用的卡片列表
            Map<String, List<GachaCard>> addCardMap = new HashMap<>();
            if (updateCard) {
                // 通过新的卡片信息统计出需要更新的卡片数量
                for (Map.Entry<String, Integer> entry : newCardNumMap.entrySet()) {
                    // 现在的数量
                    int addNum = entry.getValue();
                    Integer cardNum = oldCardNumMap.get(entry.getKey());
                    if (cardNum == null) {
                        // 表示这是新增的卡片模板类型
                    } else {
                        // 变化值
                        addNum -= cardNum;
                    }
                    if (addNum == 0) {
                        // 表示此类型没有变化，不处理
                    } else if (addNum < 0) {
                        // 表示减少了卡片，不处理
                    } else {
                        // 表示新增补充了此类型的卡片数量
                        // 查询库里面已被此卡池绑定的，还有效可被抽取的卡片数量
                        Query query = new Query();
                        query.addCriteria(Criteria.where("gachaCardTemplateId").is(entry.getKey()));
                        // 卡片为非销毁状态的卡片才更新绑定卡池
                        query.addCriteria(Criteria.where("burn").is(false));
                        // 排除已被抽走的卡片
                        query.addCriteria(Criteria.where("ownerPlayerId").is(0));
                        // 排除已被其他卡池选中的卡片
                        query.addCriteria(Criteria.where("gachaPoolId").is(0));
                        // 可用的卡片数
                        List<GachaCard> gachaCardList = mongoTemplate.find(query, GachaCard.class);
                        if (addNum > gachaCardList.size()) {
                            log.error("修改卡池失败，卡池中卡片数量不足，无法添加新卡片，gachaCardTemplateId:" + entry.getKey());
                            return ResponseBean.fail("updateGachaPool error, gachaPool card num is not enough");
                        }
                        // 只取前addNum个
                        gachaCardList = gachaCardList.subList(0, addNum);
                        for (GachaCard gachaCard : gachaCardList) {
                            addCardMap.computeIfAbsent(entry.getKey(), v -> new ArrayList<>()).add(gachaCard);
                        }
                    }
                }
            }
            if (!gachaPool.getName().isEmpty()) {
                oldGachaPool.setName(gachaPool.getName());
            }
            if (!gachaPool.getImageUrlList().isEmpty()) {
                oldGachaPool.getImageUrlList().clear();
                oldGachaPool.getImageUrlList().addAll(gachaPool.getImageUrlList());
            }
            if (!gachaPool.getAnimationUrl().isEmpty()) {
                oldGachaPool.setAnimationUrl(gachaPool.getAnimationUrl());
            }
            oldGachaPool.setRecommend(gachaPool.isRecommend());
            if (!gachaPool.getDescription().isEmpty()) {
                oldGachaPool.setDescription(gachaPool.getDescription());
            }
            if (gachaPool.getStartTime() != 0) {
                oldGachaPool.setStartTime(gachaPool.getStartTime());
            }
            if (gachaPool.getEndTime() != 0) {
                oldGachaPool.setEndTime(gachaPool.getEndTime());
            }
            if (gachaPool.getCandy() != 0) {
                oldGachaPool.setCandy(gachaPool.getCandy());
            }
            // 本次修改是否修改了卡池卡片
            if (updateCard && !gachaPool.getWeightList().isEmpty()) {
                oldGachaPool.getWeightList().clear();
                oldGachaPool.getWeightList().addAll(gachaPool.getWeightList());
                // 解除数据库之前绑定此卡池的卡片
                Query queryUnbind = new Query(Criteria.where("gachaPoolId").is(gachaPool.getId()));
                // 增加一个查询修改条件，卡片为非销毁状态的卡片才更新绑定卡池
                queryUnbind.addCriteria(Criteria.where("burn").is(false));
                // 排除已被抽走的卡片
                queryUnbind.addCriteria(Criteria.where("ownerPlayerId").is(0));
                Update updateUnbind = new Update();
                updateUnbind.set("gachaPoolId", 0);
                mongoTemplate.updateMulti(queryUnbind, updateUnbind, GachaCard.class);
                // 批量更新卡片绑定卡池ID
                for (GachaPoolWeight gachaPoolWeight : gachaPool.getWeightList()) {
                    for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                        List<GachaCard> gachaCardList = addCardMap.get(entry.getCardTemplateId());
                        // 这里gachaCardList的数量大于entry.getValue()的数量，前面有验证，其他多线程先不考虑，先实现功能，性能也后面再来优化
                        for (GachaCard gachaCard : gachaCardList) {
                            Query query = new Query(Criteria.where("id").is(gachaCard.getId()));
                            Update updateBind = new Update();
                            updateBind.set("gachaPoolId", gachaPool.getId());
                            mongoTemplate.updateFirst(query, updateBind, GachaCard.class);
                        }
                    }
                }
            }
            mongoTemplate.save(oldGachaPool);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("更新卡池异常：", e);
            return ResponseBean.fail("updateGachaPool error" + e.getMessage());
        }
    }

    /**
     * 获取卡池图片信息列表
     *
     * @param user
     * @param type image=背景图片 animation=抽卡动画
     * @return
     */
    @GetMapping("/gachaPoolImageList")
    public ResponseBean<Object> gachaPoolImageList(@CurrentUser WebBgUser user, @RequestParam("type") String type) {
        try {
            JSONObject resultJson = new JSONObject();
            resultJson.put("type", type);
            if ("image".equalsIgnoreCase(type)) {
                resultJson.put("list", dataGroupGachaPool.getGachaPoolImageUrlList());
            } else if ("animation".equalsIgnoreCase(type)) {
                resultJson.put("list", dataGroupGachaPool.getGachaPoolAnimationDataList());
            }
            return ResponseBean.success(resultJson);
        } catch (Exception e) {
            log.error("获取卡池图片信息列表异常：", e);
            return ResponseBean.fail("gachaPoolImageList error" + e.getMessage());
        }
    }

    /**
     * 上传卡池图片信息
     *
     * @param user
     * @param info
     * @return
     */
    @PostMapping("/uploadGachaPoolImage")
    public ResponseBean<Object> uploadGachaPoolImage(@CurrentUser WebBgUser user, @RequestBody ReqUploadGachaPoolImageInfo info) {
        try {
            if ("image".equalsIgnoreCase(info.getType())) {
                if (StringUtil.isEmptyOrNull(info.getImageUrl())) {
                    return ResponseBean.fail("image is empty");
                }
                if (dataGroupGachaPool.getGachaPoolImageUrlList().contains(info.getImageUrl())) {
                    return ResponseBean.fail("image is exist");
                }
                dataGroupGachaPool.getGachaPoolImageUrlList().add(info.getImageUrl());
            } else if ("animation".equalsIgnoreCase(info.getType())) {
                if (StringUtil.isEmptyOrNull(info.getImageUrl())) {
                    return ResponseBean.fail("image is empty");
                }
                if (StringUtil.isEmptyOrNull(info.getVideoUrl())) {
                    return ResponseBean.fail("video is empty");
                }
                for (int i = 0; i < dataGroupGachaPool.getGachaPoolAnimationDataList().size(); i++) {
                    GachaPoolAnimationData gachaPoolAnimationData = dataGroupGachaPool.getGachaPoolAnimationDataList().get(i);
                    if (gachaPoolAnimationData.getImageUrl().equals(info.getImageUrl()) && gachaPoolAnimationData.getVideoUrl().equals(info.getVideoUrl())) {
                        return ResponseBean.fail("animation is exist");
                    }
                }
                GachaPoolAnimationData gachaPoolAnimationData = new GachaPoolAnimationData(info.getImageUrl(), info.getVideoUrl());
                dataGroupGachaPool.getGachaPoolAnimationDataList().add(gachaPoolAnimationData);
            }
            mongoTemplate.save(dataGroupGachaPool);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("上传卡池图片信息异常：", e);
            return ResponseBean.fail("uploadGachaPoolImage error" + e.getMessage());
        }
    }

    /**
     * 新增卡片模板
     *
     * @param user
     * @param gachaCardTemplate
     * @return
     */
    @PostMapping("/addCardTemplate")
    public ResponseBean<Object> addCardTemplate(@CurrentUser WebBgUser user, @RequestBody GachaCardTemplate gachaCardTemplate) {
        try {
            // 设置唯一id
            gachaCardTemplate.setId(Long.toString(ID.getId()));
            mongoTemplate.insert(gachaCardTemplate);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("新增卡片模板异常：", e);
            return ResponseBean.fail("addCardTemplate error" + e.getMessage());
        }
    }

    /**
     * 删除卡片模板
     *
     * @param user
     * @param id
     * @return
     */
    @PostMapping("/deleteCardTemplate")
    public ResponseBean<Object> deleteCardTemplate(@CurrentUser WebBgUser user, @RequestBody String id) {
        try {
            // 卡片绑定了卡片模板，为了安全，不支持删除
//            DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("id").is(id)), GachaCardTemplate.class);
//            if (result.getDeletedCount() > 0) {
//                return ResponseBean.success();
//            } else {
//                return ResponseBean.fail("deleteCardTemplate error id:" + id);
//            }
            log.info("卡片绑定了卡片模板，为了安全，不支持删除 id:" + id);
            return ResponseBean.fail("deleteCardTemplate error id:" + id);
        } catch (Exception e) {
            log.error("删除卡片模板异常：", e);
            return ResponseBean.fail("deleteCardTemplate error" + e.getMessage());
        }
    }

    /**
     * 修改卡片模板
     *
     * @param user
     * @param gachaCardTemplate
     * @return
     */
    @PostMapping("/updateCardTemplate")
    public ResponseBean<Object> updateCardTemplate(@CurrentUser WebBgUser user, @RequestBody GachaCardTemplate gachaCardTemplate) {
        try {
            GachaCardTemplate oldGachaCardTemplate = mongoTemplate.findOne(new Query(Criteria.where("id").is(gachaCardTemplate.getId())), GachaCardTemplate.class);
            if (oldGachaCardTemplate == null) {
                log.error("更新卡片模板，卡片模板不存在 id:" + gachaCardTemplate.getId());
                return ResponseBean.fail("updateCardTemplate id no exist id:" + gachaCardTemplate.getId());
            }
            // 不支持修改美元价值

            // 修改其他信息
            if (!gachaCardTemplate.getName().isEmpty()) {
                oldGachaCardTemplate.setName(gachaCardTemplate.getName());
            }
            if (!gachaCardTemplate.getDescription().isEmpty()) {
                oldGachaCardTemplate.setDescription(gachaCardTemplate.getDescription());
            }
            if (!gachaCardTemplate.getImage().isEmpty()) {
                oldGachaCardTemplate.setImage(gachaCardTemplate.getImage());
            }
            if (!gachaCardTemplate.getAttributes().isEmpty()) {
                oldGachaCardTemplate.getAttributes().clear();
                oldGachaCardTemplate.getAttributes().addAll(gachaCardTemplate.getAttributes());
            }
            mongoTemplate.save(oldGachaCardTemplate);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("修改卡片模板异常：", e);
            return ResponseBean.fail("updateCardTemplate error" + e.getMessage());
        }
    }

    /**
     * 查看卡片模板列表
     */
    @GetMapping("/cardTemplateList")
    public ResponseBean<Object> cardTemplateList() {
        try {
            List<GachaCardTemplate> gachaCardTemplateList = mongoTemplate.findAll(GachaCardTemplate.class);
            return ResponseBean.success(gachaCardTemplateList);
        } catch (Exception e) {
            log.error("查看卡片模板列表异常：", e);
            return ResponseBean.fail("cardTemplateList error" + e.getMessage());
        }
    }

    /**
     * 获取卡片列表
     */
    @GetMapping("/cardList")
    public ResponseBean<Object> cardList(@CurrentUser WebBgUser user, @RequestParam("quality") String quality, @RequestParam("all") boolean all) {
        try {
            // 根据卡片分类统计数量 key=卡片模板唯一id value=相同image地址的卡片信息
            Map<String, GachaCard> resultMap = new HashMap<>();
            // 查询所有卡片模板
            List<GachaCardTemplate> gachaCardTemplateList = mongoTemplate.findAll(GachaCardTemplate.class);
            for (int i = 0; i < gachaCardTemplateList.size(); i++) {
                GachaCardTemplate gachaCardTemplate = gachaCardTemplateList.get(i);
                Query query = new Query();
                if (!quality.isEmpty()) {
                    query.addCriteria(Criteria.where("quality").is(quality));
                }
                // 如果不是查看所有，就只查询未被抽走的
                if (!all) {
                    // 排除已被抽走的卡片
                    query.addCriteria(Criteria.where("ownerPlayerId").is(0));
                    // 排除已被其他卡池选中的卡片（用于后台往卡池添加卡片）
                    query.addCriteria(Criteria.where("gachaPoolId").is(0));
                }
                // 只查询未被销毁的卡片
                query.addCriteria(Criteria.where("burn").is(false));
                // 查询对应模板卡片数量
                query.addCriteria(Criteria.where("gachaCardTemplateId").is(gachaCardTemplate.getId()));
                GachaCard card = mongoTemplate.findOne(query, GachaCard.class);
                if (card == null) {
                    // 表示此模板下没有卡片
                    continue;
                }
                int count = (int) mongoTemplate.count(query, GachaCard.class);
                GachaCard copyCard = JSON.parseObject(JSON.toJSONString(card), GachaCard.class);
                copyCard.setNum(count);
                resultMap.put(card.getGachaCardTemplateId(), copyCard);
            }

            JSONObject dataTableJson = new JSONObject();
            dataTableJson.put("list", resultMap.values());
            dataTableJson.put("total", resultMap.size());
            return ResponseBean.success(dataTableJson);
        } catch (Exception e) {
            log.error("获取卡片列表异常：", e);
            return ResponseBean.fail("cardList error" + e.getMessage());
        }
    }

    /**
     * 铸造卡片
     *
     * @param user
     * @param info
     * @return
     */
    @PostMapping("/mintCard")
    public ResponseBean<Object> mintCard(@CurrentUser WebBgUser user, @RequestBody ReqMintCardInfo info) {
        try {
            GachaCardTemplate gachaCardTemplate = mongoTemplate.findOne(new Query(Criteria.where("id").is(info.getId())), GachaCardTemplate.class);
            if (gachaCardTemplate == null) {
                log.error("铸造卡片，卡片模板不存在 id:" + info.getId());
                return ResponseBean.fail("mintCard id no exist id:" + info.getId());
            }
            if (info.getCount() <= 0) {
                log.error("铸造卡片，数量过小 count:" + info.getCount());
                return ResponseBean.fail("mintCard count too small");
            }
            if (info.getCount() > 10000) {
                log.error("铸造卡片，数量过大 count:" + info.getCount());
                return ResponseBean.fail("mintCard count too large");
            }
            if (solanaManager.getMintTask().getMintRemain() > 0) {
                log.error("铸造卡片，请稍后再试，已有卡片正在铸造中..");
                return ResponseBean.fail("mintCard minting...");
            }
            SolanaAddNftMintQueueTimer solanaAddNftMintQueueTimer = new SolanaAddNftMintQueueTimer(gachaCardTemplate, info.getCount());
            threadManager.getThread(threadManager.getSolanaNftThreadName()).addTimerEvent(solanaAddNftMintQueueTimer);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("铸造卡片异常：", e);
            return ResponseBean.fail("mintCard error" + e.getMessage());
        }
    }

    /**
     * 铸造卡片进度
     */
    @GetMapping("/mintCardProgress")
    public ResponseBean<Object> mintCardProgress(@CurrentUser WebBgUser user) {
        try {
            JSONObject data = new JSONObject();
            SolanaMintQueueData mintTask = solanaManager.getMintTask();
            // 当前是否正在mint中(有可能中途取消，剩余数量改为0，但是还有一笔正在交易中的交易)
            data.put("minting", mintTask.getMintRemain() > 0 || (mintTask.getMintRemain() == 0 && !mintTask.getTransactionId().isEmpty()));
            // 当前已经mint数量
            data.put("mintCurrentCount", mintTask.getMintTotal() - mintTask.getMintRemain());
            // 总数量
            data.put("mintTotal", mintTask.getMintTotal());
            // 当前mint卡片模板信息
            if (!mintTask.getGachaCardTemplateId().isEmpty()) {
                GachaCardTemplate gachaCardTemplate = mongoTemplate.findOne(new Query(Criteria.where("id").is(mintTask.getGachaCardTemplateId())), GachaCardTemplate.class);
                if (gachaCardTemplate != null) {
                    data.put("gachaCardTemplate", gachaCardTemplate);
                }
            } else {
                data.put("gachaCardTemplate", "");
            }
            return ResponseBean.success(data);
        } catch (Exception e) {
            log.error("铸造卡片进度异常：", e);
            return ResponseBean.fail("mintCardProgress error" + e.getMessage());
        }
    }

    /**
     * 取消铸造卡片
     */
    @PostMapping("/mintCardCancel")
    public ResponseBean<Object> mintCardCancel(@CurrentUser WebBgUser user) {
        try {
            SolanaCancelNftMintQueueTimer timer = new SolanaCancelNftMintQueueTimer();
            threadManager.getThread(threadManager.getSolanaNftThreadName()).addTimerEvent(timer);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("取消铸造卡片异常：", e);
            return ResponseBean.fail("cancelMintCard error" + e.getMessage());
        }
    }

    /**
     * 查看销毁卡片返现FT列表
     */
    @GetMapping("/burnCardRefundList")
    public ResponseBean<Object> burnCardRefundList(@CurrentUser WebBgUser user, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        try {
            Query query = new Query();
            // 分页降序查询
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
            // 查询最大条数，用于设置分页
            long max = mongoTemplate.count(query, GachaCardRefund.class);
            query.with(pageable);
            List<GachaCardRefund> list = mongoTemplate.find(query, GachaCardRefund.class);
            JSONObject dataTableJson = new JSONObject();
            dataTableJson.put("list", list);
            dataTableJson.put("total", max);
            return ResponseBean.success(dataTableJson);
        } catch (Exception e) {
            log.error("查看销毁卡片返现FT列表异常：", e);
            return ResponseBean.fail("burnCardRefundList error" + e.getMessage());
        }
    }

    /**
     * 修改销毁卡片返现FT信息
     */
    @PostMapping("/updateBurnCardRefund")
    public ResponseBean<Object> updateBurnCardRefund(@CurrentUser WebBgUser user, @RequestBody String gachaCardRefundId) {
        try {
            GachaCardRefund gachaCardRefund = mongoTemplate.findOne(new Query(Criteria.where("id").is(gachaCardRefundId)), GachaCardRefund.class);
            if (gachaCardRefund == null) {
                log.error("修改销毁卡片返现FT信息，不存在 id:" + gachaCardRefundId);
                return ResponseBean.fail("updateCardTemplate id no exist id:" + gachaCardRefundId);
            }
            gachaCardRefund.setRefund(true);
            mongoTemplate.save(gachaCardRefund);
            return ResponseBean.success(gachaCardRefund);
        } catch (Exception e) {
            log.error("修改销毁卡片返现FT信息异常：", e);
            return ResponseBean.fail("updateCardTemplate error" + e.getMessage());
        }
    }

}
