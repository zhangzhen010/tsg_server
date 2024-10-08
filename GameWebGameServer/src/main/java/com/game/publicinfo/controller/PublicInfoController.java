package com.game.publicinfo.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.controller.structs.ResponseBean;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.*;
import com.game.utils.ID;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家控制器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 11:09
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsg/publicinfo")
public class PublicInfoController {

    private @Resource MongoTemplate mongoTemplate;
    private @Resource PlayerManager playerManager;
    /**
     * 空卡片
     */
    private final GachaCard nullCard = new GachaCard();

    /**
     * 获取tsg存储的文件资源（废弃，走第三方下载资源）
     */
    /**
     * 获取文件资源（http://192.168.110.234:19401/tsg/publicinfo/getTsgFile?fileUrl=/tsg/gacha/2024/09/09/gachaPool_20240909192557A002.png 修改参数可以直接浏览器获取）
     * @param fileUrl
     * @param response
     */
//    @GetMapping("/getTsgFile")
//    public void resourceDownload(@RequestParam("fileUrl") String fileUrl, HttpServletResponse response) {
//        try {
//            if (StringUtil.isEmptyOrNull(fileUrl)) {
//                log.error("获取tsg存储的文件资源失败，fileUrl为空!");
//                return;
//            }
//            if (!FileUtils.checkAllowDownload(fileUrl)) {
//                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", fileUrl));
//            }
//            // 本地资源路径
//            String localPath = OrangeWebConfig.getTsg();
//            // 数据库资源地址
//            String downloadPath = localPath + StringUtils.substringAfter(fileUrl, Constants.RESOURCE_PREFIX_TSG);
//            // 下载名称
//            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
//            // 获取文件扩展名以确定 Content-Type
//            String extension = getFileExtension(downloadName);
//            String contentType = getContentType(extension);
//            // 设置 Content-Type
//            response.setContentType(contentType);
//            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma", "no-cache");
//            response.setDateHeader("Expires", 0);
//            // 读取文件并输出到响应流
//            Path path = Paths.get(downloadPath);
//            Files.copy(path, response.getOutputStream());
//            response.getOutputStream().flush();
//        } catch (Exception e) {
//            log.error("获取tsg存储的文件资源失败", e);
//        }
//    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
//    private String getFileExtension(String fileName) {
//        int dotIndex = fileName.lastIndexOf('.');
//        if (dotIndex == -1) {
//            return "";
//        }
//        return fileName.substring(dotIndex + 1).toLowerCase();
//    }

    /**
     * 根据tsg后台上传的文件扩展名，返回对应的Content-Type
     *
     * @param extension
     * @return
     */
//    private String getContentType(String extension) {
//        switch (extension) {
//            case "jpg":
//            case "jpeg":
//                return MediaType.IMAGE_JPEG_VALUE;
//            case "png":
//                return MediaType.IMAGE_PNG_VALUE;
//            case "gif":
//                return MediaType.IMAGE_GIF_VALUE;
//            case "mp4":
//                return "video/mp4";
//            case "webm":
//                return "video/webm";
//            case "ogg":
//                return "video/ogg";
//            default:
//                return MediaType.APPLICATION_OCTET_STREAM_VALUE; // 默认二进制流
//        }
//    }

    /**
     * 请求获取所有卡池列表
     *
     * @return
     */
    @GetMapping("/gachalist")
    public ResponseBean<Object> gachalist() {
        try {
            // 获取当前时间
            long currentTimeMillis = System.currentTimeMillis();
            // 构建查询条件：startTime 小于当前时间且 endTime 大于当前时间
            Criteria criteria = Criteria.where("startTime").lte(currentTimeMillis).and("endTime").gte(currentTimeMillis);
            Query query = new Query(criteria);
            List<GachaPool> poolList = mongoTemplate.find(query, GachaPool.class);
            // 推荐的卡池
            List<GachaPool> recommendPoolList = new ArrayList<>();
            // 所有卡池
            List<GachaPool> allPoolList = new ArrayList<>();
            for (GachaPool gachaPool : poolList) {
                GachaPool newGachaPool = JSON.parseObject(JSON.toJSONString(gachaPool), GachaPool.class);
                // 未抽取卡片数量
                int count = 0;
                for (GachaPoolWeight gachaPoolWeight : newGachaPool.getWeightList()) {
                    for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                        count += entry.getNum();
                    }
                }
                // 卡片总数(已抽取卡片数量+剩余未抽取卡片数量)
                newGachaPool.setTotalCardCount(gachaPool.getDrawCardCount() + count);
                newGachaPool.setDrawCardCount(count);
                // 不发送卡片信息
                newGachaPool.getWeightList().clear();
                // 放入推荐列表
                if (newGachaPool.isRecommend()) {
                    recommendPoolList.add(newGachaPool);
                }
                allPoolList.add(newGachaPool);
            }
            JSONObject data = new JSONObject();
            data.put("recommendPoolList", recommendPoolList);
            data.put("allPoolList", allPoolList);
            return ResponseBean.success(data);
        } catch (Exception e) {
            log.error("请求获取卡池列表异常：", e);
            return ResponseBean.fail("gachalist:" + e.getMessage());
        }
    }

    /**
     * 请求获取卡池详细信息
     *
     * @return
     */
    @GetMapping("/gachaInfo")
    public ResponseBean<Object> gachaInfo(@RequestParam("gachaPoolId") String gachaPoolId) {
        try {
            // 获取当前时间
            GachaPool gachaPool = mongoTemplate.findOne(Query.query(Criteria.where("id").is(gachaPoolId)), GachaPool.class);
            GachaPool newGachaPool = JSON.parseObject(JSON.toJSONString(gachaPool), GachaPool.class);
            // 未抽取卡片数量
            int count = 0;
            for (GachaPoolWeight gachaPoolWeight : newGachaPool.getWeightList()) {
                for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                    GachaCardTemplate gachaCardTemplate = mongoTemplate.findOne(Query.query(Criteria.where("id").is(entry.getCardTemplateId())), GachaCardTemplate.class);
                    entry.setImage(gachaCardTemplate.getImage());
                    entry.setUsd(gachaCardTemplate.getUsd());
                    count += entry.getNum();
                }
                // 不把卡片权重信息带过去
                gachaPoolWeight.setWeight(0);
            }
            // 卡片总数(已抽取卡片数量+剩余未抽取卡片数量)
            newGachaPool.setTotalCardCount(gachaPool.getDrawCardCount() + count);
            newGachaPool.setDrawCardCount(count);
            return ResponseBean.success(newGachaPool);
        } catch (Exception e) {
            log.error("请求获取卡池详细信息异常：", e);
            return ResponseBean.fail("gachaInfo:" + e.getMessage());
        }
    }

    /**
     * 请求获取展示卡片列表
     *
     * @return
     */
    @GetMapping("/showCardList")
    public ResponseBean<Object> showCardList() {
        try {
            GachaCard gachaCard = new GachaCard();
            gachaCard.setId(Long.toString(ID.getId()));
            gachaCard.setName("name");
            gachaCard.setImage("www.google.com");
            gachaCard.setCost(888);
            gachaCard.setQuality("SSR");
            gachaCard.setOwnerPlayerId(10001);
            List<GachaCard> gachaCardList = new ArrayList<>();
            gachaCardList.add(gachaCard);
            return ResponseBean.success(gachaCardList);
        } catch (Exception e) {
            log.error("请求获取展示卡片列表异常：", e);
            return ResponseBean.fail("showCardList:" + e.getMessage());
        }
    }

    /**
     * 请求获取抽卡历史记录
     *
     * @return
     */
    @GetMapping("/gachaDrawLog")
    public ResponseBean<Object> gachaDrawLog() {
        try {
            GachaDrawLog gachaDrawLog = new GachaDrawLog();
            gachaDrawLog.setId(1L);
            gachaDrawLog.setGachaPoolId(1L);
            gachaDrawLog.setGachaPoolName("gachaPoolName");
            gachaDrawLog.setPlayerId(10001L);
            gachaDrawLog.setPrice(888);
            gachaDrawLog.setCost(888);
            WebPlayer player = playerManager.getPlayer(gachaDrawLog.getPlayerId(), false);
            gachaDrawLog.setPlayerName(player.getPlayerName());
            gachaDrawLog.setPlayerAvatar(player.getAvatarUrl());
            List<GachaDrawLog> gachaDrawLogList = new ArrayList<>();
            gachaDrawLogList.add(gachaDrawLog);
            return ResponseBean.success(gachaDrawLogList);
        } catch (Exception e) {
            log.error("请求获取抽卡历史记录异常：", e);
            return ResponseBean.fail("gachaDrawLog:" + e.getMessage());
        }
    }

    /**
     * 请求单个卡片信息
     */
    @GetMapping("/cardInfo")
    public GachaCard cardInfo(@RequestParam("cardId") String cardId) {
        try {
            GachaCard gachaCard = mongoTemplate.findOne(Query.query(Criteria.where("id").is(cardId)), GachaCard.class);
            if (gachaCard != null) {
                return gachaCard;
            }
            return nullCard;
        } catch (Exception e) {
            return nullCard;
        }
    }

}
