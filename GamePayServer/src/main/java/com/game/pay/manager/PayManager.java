package com.game.pay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.data.bean.B_pay_Bean;
import com.game.data.bean.B_paymap_Bean;
import com.game.data.bean.B_z_server_agent_Bean;
import com.game.data.define.MyDefineSdkId;
import com.game.data.manager.DataManager;
import com.game.grpc.manager.GrpcStubManager;
import com.game.grpc.proto.GrpcProto;
import com.game.grpc.proto.PayServer2LogicServerServiceGrpc;
import com.game.log.bean.PayLog;
import com.game.pay.structs.MoonPayOrder;
import com.game.pay.structs.PayOrder;
import com.game.pay.structs.PayOrderState;
import com.game.pay.timer.PayBackTimer;
import com.game.proto.structs.ServerProtobuf;
import com.game.redis.manager.RedisManager;
import com.game.thread.manager.ThreadManager;
import com.game.utils.ID;
import com.game.utils.TimeUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 充值服务器充值管理
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 16:41
 */
@Component
@Log4j2
public class PayManager {

    private @Resource DataManager dataManager;
    private @Resource MongoTemplate mongoTemplate;
    @Qualifier("logsMongo")
    private @Resource MongoTemplate logsMongoTemplate;
    private @Resource RedisManager redisManager;
    private @Resource ThreadManager threadManager;
    private @Resource GrpcStubManager grpcStubManager;

    /**
     * 未处理订单
     */
    private final Map<Long, PayOrder> payOrderMap = new ConcurrentHashMap<>();
    /**
     * 未处理订单
     */
    private final Map<String, MoonPayOrder> moonPayOrderMap = new ConcurrentHashMap<>();

    /**
     * 创建订单
     *
     * @param payMapId
     * @param playerId
     * @param playerName
     * @param serverId
     * @param userId
     * @param pfId
     * @param sdkId
     * @param paramenterList
     * @param payMapBean
     * @return
     */
    public PayOrder createPayOrder(int payMapId, long playerId, String playerName, int serverId, long userId, int pfId, int sdkId, List<String> paramenterList, B_paymap_Bean payMapBean) {
        try {
            B_pay_Bean payBean = dataManager.c_pay_Container.getMap().get(payMapBean.getPayId());
            PayOrder payOrder = new PayOrder();
            payOrder.setGameOrderId(ID.getId());
            payOrder.setPayId(payMapBean.getPayId());
            payOrder.setPayMapId(payMapId);
            payOrder.setType(payMapBean.getType());
            payOrder.setMoney(payBean.getMoney());
            payOrder.setUserId(userId);
            payOrder.setPlayerId(playerId);
            payOrder.setPlayerName(playerName);
            payOrder.setServerId(serverId);
            payOrder.setPayState(PayOrderState.CREATE.getValue());
            payOrder.setPfId(pfId);
            payOrder.setSdkId(sdkId);
            payOrder.setCreateTime(System.currentTimeMillis());
            if (paramenterList.isEmpty()) {
                payOrder.setExtInfo("");
            } else {
                // 目前用于自选礼包选择的礼物下标
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = paramenterList.size(); i < len; i++) {
                    sb.append(paramenterList.get(i));
                    sb.append(",");
                }
                payOrder.setExtInfo(sb.toString());
            }
            log.info("创建充值订单成功orderId=" + payOrder.getGameOrderId() + " payMapId=" + payMapId + " money（单位：分）=" + payOrder.getMoney());
            return payOrder;
        } catch (Exception e) {
            log.error("创建订单异常", e);
            return null;
        }
    }

    /**
     * 添加订单到缓存
     *
     * @param payOrder
     */
    public void addPayOrder(PayOrder payOrder) {
        payOrderMap.put(payOrder.getGameOrderId(), payOrder);
    }

    /**
     * 定期移除订单缓存
     */
    public void removePayOrder() {
        try {
            long time = System.currentTimeMillis();
            List<Long> delList = new ArrayList<>();
            for (PayOrder payOrder : payOrderMap.values()) {
                if (time - payOrder.getCreateTime() > TimeUtil.HOUR_MILLIS) {
                    delList.add(payOrder.getGameOrderId());
                }
            }
            for (int i = 0, len = delList.size(); i < len; i++) {
                payOrderMap.remove(delList.get(i));
            }
        } catch (Exception e) {
            log.error("定期移除订单缓存", e);
        }
    }

    /**
     * 根据游戏充值订单号取订单信息
     *
     * @param gameOrderId
     * @return
     */
    public PayOrder getPayOrder(long gameOrderId) {
        try {
            PayOrder payOrder = payOrderMap.get(gameOrderId);
            if (payOrder == null) {
                payOrder = mongoTemplate.findOne(Query.query(Criteria.where("gameOrderId").is(gameOrderId)), PayOrder.class);
                // 记录内存
                if (payOrder != null) {
                    addPayOrder(payOrder);
                }
            }
            return payOrder;
        } catch (Exception e) {
            log.error("获取订单信息", e);
            return null;
        }
    }

    /**
     * 根据平台订单号获取订单信息
     *
     * @param pfOrderId
     * @return
     */
    public PayOrder getPayOrderByPfOrderId(String pfOrderId) {
        try {
            PayOrder payOrder = payOrderMap.values().stream().filter(o -> o.getPfOrderId().equals(pfOrderId)).findAny().orElse(null);
            if (payOrder == null) {
                payOrder = mongoTemplate.findOne(Query.query(Criteria.where("pfOrderId").is(pfOrderId)), PayOrder.class);
                // 记录内存
                if (payOrder != null) {
                    addPayOrder(payOrder);
                }
            }
            return payOrder;
        } catch (Exception e) {
            log.error("获取订单信息", e);
            return null;
        }
    }

    /**
     * 处理充值回调
     *
     * @param serverId
     * @param gameOrderId
     * @param pfOrderId
     * @param state
     * @param msg
     * @param extInfo
     */
    public void payBack(int serverId, long gameOrderId, String pfOrderId, PayOrderState state, String msg, String extInfo) {
        try {
            log.info("处理充值回调订单" + gameOrderId);
            PayOrder payOrder = getPayOrder(gameOrderId);
            if (payOrder == null) {
                log.error("找不到订单或已处理serverId=" + serverId + " gameOrderId=" + gameOrderId);
                return;
            }
            if (payOrder.getPayState() == PayOrderState.SUCCESS.getValue() || payOrder.getPayState() == PayOrderState.REPAIR.getValue()) {
                log.error("充值订单已经处理过payOrderId=" + payOrder.getGameOrderId());
                return;
            }
            // 获取创建订单是临时存入的扩展参数（这里目前仅有自选礼包参数）
            List<String> extList = new ArrayList<>();
            if (payOrder.getExtInfo() != null && !payOrder.getExtInfo().isEmpty()) {
                String[] exts = payOrder.getExtInfo().split(",");
                for (int i = 0; i < exts.length; i++) {
                    if (exts[i].isEmpty()) {
                        continue;
                    }
                    extList.add(exts[i]);
                }
            }
            // 记录状态消息
            payOrder.setMsg(msg);
            // 记录扩展参数
            if (!extInfo.isEmpty()) {
                payOrder.setExtInfo(extInfo);
            }
            // 记录平台订单号
            payOrder.setPfOrderId(pfOrderId);
            // 处理成功
            if (state == PayOrderState.SUCCESS || state == PayOrderState.REPAIR) {
                // 保存数据库状态
                payOrder.setPayState(state.getValue());
                // 保存数据库状态
                mongoTemplate.save(payOrder);
                // 通知服务器发放充值奖励
                sendPayMsg(serverId, payOrder.getPlayerId(), gameOrderId, pfOrderId, payOrder.getPayMapId(), state, payOrder.getCreateTime(), extList);
                log.info("充值验证流程成功，通知游戏服务器发奖！gameOrderId=" + payOrder.getGameOrderId());
            } else {
                // 通知服务器失败(失败，充值配置id都发送0，以防万一)
                sendPayMsg(serverId, payOrder.getPlayerId(), gameOrderId, pfOrderId, 0, PayOrderState.FAIL, payOrder.getCreateTime(), extList);
                payOrder.setPayState(state.getValue());
                // 保存数据库状态
                mongoTemplate.save(payOrder);
                log.info("充值验证流程失败，通知游戏服务器！gameOrderId=" + payOrder.getGameOrderId());
            }
            // 修改订单状态日志
            PayLog payLog = JSON.parseObject(JSON.toJSONString(payOrder), PayLog.class);
            payLog.setLogId(ID.getId());
            logsMongoTemplate.insert(payLog, payLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("处理充值回调异常", e);
        }
    }

    /**
     * 通知服务器充值消息
     *
     * @param serverId
     * @param playerId
     * @param payOrderId
     * @param pfOrderId
     * @param payMapId
     * @param state
     */
    private void sendPayMsg(int serverId, long playerId, long payOrderId, String pfOrderId, int payMapId, PayOrderState state, long createTime, List<String> extList) {
        try {
            if (state.getValue() == PayOrderState.SUCCESS.getValue()) {
                log.info("通知逻辑服务器发送充值奖励playerId=" + playerId + " serverId=" + serverId + " payOrderId=" + payOrderId);
                // 获取玩家上一次登录的逻辑服务器(此版本为世界服游戏，服务器id改为从redis获取，后面如果为滚服游戏，注释即可)
                serverId = redisManager.getPlayerLastLogicServerId(playerId);
                GrpcProto.ResPublicPayBack.Builder builder = GrpcProto.ResPublicPayBack.newBuilder();
                builder.setPlayerId(playerId);
                builder.setPayOrderId(payOrderId);
                builder.setPfOrderId(pfOrderId);
                builder.setPayMapId(payMapId);
                builder.setState(state.getValue());
                builder.setPayOrderCreateTime(createTime);
                if (!extList.isEmpty()) {
                    builder.addAllOrderExtInfo(extList);
                }
                // 发送消息到逻辑服，通知充值成功
                PayServer2LogicServerServiceGrpc.PayServer2LogicServerServiceStub stub = grpcStubManager.getPayServer2LogicServerStub(serverId);
                if (stub == null) {
                    log.error("发送消息到逻辑服，通知充值消息失败，找不到逻辑服grpc连接serverId=" + serverId + " 打印充值信息：" + builder);
                    return;
                }
                stub.resPayBack(builder.build(), new StreamObserver<>() {
                    @Override
                    public void onNext(Empty empty) {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onCompleted() {
                        log.info("通知服务器发送充值奖励成功，逻辑服返回收到消息！");
                    }
                });
            } else {
                log.info("充值失败playerId=" + playerId + " serverId=" + serverId + " payOrderId=" + payOrderId);
            }
        } catch (Exception e) {
            log.error("通知服务器充值消息异常", e);
        }
    }

    /**
     * 谷歌支付校验
     *
     * @param packageName 包名
     * @param productId   商品id
     * @param token       token
     * @param appName     应用名
     * @return 返回购买信息
     */
    public ProductPurchase googlePayCheck(String packageName, String productId, String token, String appName) {
        try {
//            log.info("进入谷歌校验: packageName: " + packageName + " 商品id: " + productId + "appName: " + " token: " + token);

            JSONObject jsonObject = JSONObject.parseObject(token);
            token = jsonObject.getJSONObject("Payload").getJSONObject("json").getString("purchaseToken");

            log.info("json: " + token);

            //使用服务帐户Json文件获取Google凭据
            List<String> scopes = new ArrayList<>();
            scopes.add(AndroidPublisherScopes.ANDROIDPUBLISHER);
//            ResourceLoader resourceLoader = new DefaultResourceLoader();
            String jsonFilePath = "/data/pay/googlePay.json";
//            org.springframework.core.io.Resource resource = resourceLoader.getResource(getClass().getResource("/").getPath() + "googlePay.json");
//            GoogleCredential credential = GoogleCredential.fromStream(resource.getInputStream()).createScoped(scopes);
            GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(jsonFilePath)).createScoped(scopes);
            // 使用谷歌凭据和收据从谷歌获取购买信息
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JacksonFactory jsonFactory = new JacksonFactory();
            AndroidPublisher publisher = new AndroidPublisher.Builder(httpTransport, jsonFactory, credential).setApplicationName(appName).build();
            AndroidPublisher.Purchases purchases = publisher.purchases();
            AndroidPublisher.Purchases.Products.Get request = purchases.products().get(packageName, productId, token);
            return request.execute();
        } catch (Exception e) {
            log.error("谷歌支付校验异常：", e);
            return null;
        }
    }

    /**
     * 请求充值服创建一个充值订单
     *
     * @param reqData
     */
    public GrpcProto.ResPublicPayServerCreateOrder reqPublicPayServerCreateOrder(GrpcProto.ReqPublicPayServerCreateOrder reqData) {
        try {
            // 处理逻辑[请求充值服创建一个充值订单]
            log.error("请求充值并创建订单sdkId=" + reqData.getSdkId() + " pfId=" + reqData.getPfId());
            B_paymap_Bean payMapBean = dataManager.c_paymap_Container.getMap().get(reqData.getPayMapId());
            if (payMapBean == null) {
                log.error("充值服务器充值配置找不到sid=" + reqData.getPayMapId());
                return null;
            }
            PayOrder payOrder = createPayOrder(reqData.getPayMapId(), reqData.getPlayerId(), reqData.getPlayerName(), reqData.getServerId(), reqData.getUserId(), reqData.getPfId(), reqData.getSdkId(), reqData.getParamsList(), payMapBean);
            if (payOrder == null) {
                log.error("充值创建订单异常playerId=" + reqData.getPlayerId());
                return null;
            }
            // 订单数据落地
            mongoTemplate.insert(payOrder);
            // 添加一个订单到缓存
            addPayOrder(payOrder);
            B_z_server_agent_Bean agentBean = dataManager.c_z_server_agent_Container.getList().stream().filter(b -> b.getPfId() == reqData.getPfId() && b.getSdkId() == reqData.getSdkId()).findAny().orElse(null);
            // 返回服务器创建订单成功
            GrpcProto.ResPublicPayServerCreateOrder.Builder resPublicPayServerCreateOrderBuilder = ServerProtobuf.getResPublicPayServerCreateOrderBuilder();
            resPublicPayServerCreateOrderBuilder.setPlayerId(reqData.getPlayerId());
            resPublicPayServerCreateOrderBuilder.setPayOrderId(payOrder.getGameOrderId());
            resPublicPayServerCreateOrderBuilder.setPayMapId(payOrder.getPayMapId());
            if (agentBean != null) {
                resPublicPayServerCreateOrderBuilder.setBackUrl(agentBean.getBackUrl());
            }
            StringBuilder sb = new StringBuilder();
//            sb.append(payOrder.getServerId());
//            sb.append(",");
            // 透传参数目前只是用游戏订单id
            sb.append(payOrder.getGameOrderId());
//            sb.append(",");
//            sb.append(payOrder.getPfId());
//            sb.append(",");
//            sb.append(payOrder.getSdkId());
//            sb.append(",");
//            sb.append(payOrder.getPlayerId());
            if (reqData.getSdkId() == MyDefineSdkId.TEST) {
                // 占位
            }
            resPublicPayServerCreateOrderBuilder.setInfos(sb.toString());
            // 插入订单创建日志
            PayLog payLog = JSON.parseObject(JSON.toJSONString(payOrder), PayLog.class);
            payLog.setLogId(ID.getId());
            logsMongoTemplate.insert(payLog, payLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
            log.info("发送充值订单数据给玩家player=" + reqData.getPlayerId() + " payMapId=" + reqData.getPayMapId());
            // 返回逻辑服务器
            return resPublicPayServerCreateOrderBuilder.build();
        } catch (Exception e) {
            log.error("请求充值服创建一个充值订单", e);
            return null;
        }
    }


    /**
     * 请求充值服充值订单验证
     *
     * @param reqData
     */
    public void reqPublicPayCheck(GrpcProto.ReqPublicPayServerVerify reqData) {
        try {
            // 处理逻辑[请求充值服充值订单验证]
            if (reqData.getSdkId() == MyDefineSdkId.GOOGLE) {
                log.info("进入谷歌校验");
                ProductPurchase productPurchase = googlePayCheck(reqData.getGoogleInfo().getPackageName(), reqData.getGoogleInfo().getProductId(), reqData.getGoogleInfo().getToken(), reqData.getGoogleInfo().getAppName());
                if (productPurchase != null) {
                    String jsonString = JSONObject.toJSONString(productPurchase);
                    JSONObject jsonObject = JSONObject.parseObject(jsonString);
                    log.info("谷歌携带参数: " + jsonString);
                    if (productPurchase.getPurchaseState() == 0) {
                        // 目前版本透传参数就是游戏订单id
                        long gameOrderId = jsonObject.getLong("obfuscatedExternalAccountId");
                        PayOrder payOrder = getPayOrder(gameOrderId);
                        if (payOrder == null) {
                            log.error("失败，Google充值回调找不到订单: gameOrderId=" + gameOrderId);
                            return;
                        }
                        // 根据商品id找到金额是否正确
                        B_pay_Bean payBean = dataManager.c_pay_Container.getList().stream().filter(b -> b.getProductId().equals(reqData.getGoogleInfo().getProductId())).findAny().orElse(null);
                        if (payBean == null) {
                            log.error("失败，Google验证商品不合法，服务器不存在此商品productId=" + productPurchase.getProductId());
                            return;
                        }
                        if (payOrder.getMoney() != payBean.getMoney()) {
                            log.error("失败，Google验证金额不合法: orderMoney（单位：分）: " + payOrder.getMoney() + ", backMoney=" + payBean.getMoney());
                            return;
                        }
                        log.info("Google充值验证成功!");
                        // 处理成功
                        PayBackTimer timer = new PayBackTimer(payOrder.getServerId(), gameOrderId, productPurchase.getOrderId(), PayOrderState.SUCCESS, "充值", "");
                        threadManager.getMainThread().addTimerEvent(timer);
                    }
                }
            }
        } catch (Exception e) {
            log.error("请求充值服充值订单验证异常", e);
        }
    }

    /**
     * moonPay交易回调
     *
     * @param data
     */
    public void moonPayBack(JSONObject data) {
        try {
            // 获取回调类型
            String type = data.getString("type");
            // 获取关键数据
            String id = data.getString("id");
            // 创建买入交易订单
            if ("transaction_created".equalsIgnoreCase(type)) {
                MoonPayOrder moonPayOrder = moonPayOrderMap.get(id);
                if (moonPayOrder == null) {
                    moonPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), MoonPayOrder.class);
                    if (moonPayOrder != null) {
                        moonPayOrderMap.put(id, moonPayOrder);
                    }
                }
                if (moonPayOrder == null) {
                    moonPayOrder = new MoonPayOrder();
                    String createdAt = data.getString("createdAt");
                    String updatedAt = data.getString("updatedAt");
                    String status = data.getString("status");
                    double baseCurrencyAmount = data.getDouble("baseCurrencyAmount");
                    double quoteCurrencyAmount = data.getDouble("quoteCurrencyAmount");
                    double feeAmount = data.getDouble("feeAmount");
                    double networkFeeAmount = data.getDouble("networkFeeAmount");
                    String walletAddress = data.getString("walletAddress");
                    String cryptoTransactionId = data.getString("cryptoTransactionId");
                    String customerId = data.getString("customerId");
                    String paymentMethod = data.getString("paymentMethod");
                    String baseCurrency = data.getString("baseCurrency");
                    String currency = data.getString("currency");
                    moonPayOrder.setId(id);
                    moonPayOrder.setCreatedAt(createdAt);
                    moonPayOrder.setUpdatedAt(updatedAt);
                    moonPayOrder.setStatus(status);
                    moonPayOrder.setBaseCurrencyAmount(baseCurrencyAmount);
                    moonPayOrder.setQuoteCurrencyAmount(quoteCurrencyAmount);
                    moonPayOrder.setFeeAmount(feeAmount);
                    moonPayOrder.setNetworkFeeAmount(networkFeeAmount);
                    moonPayOrder.setWalletAddress(walletAddress);
                    moonPayOrder.setCryptoTransactionId(cryptoTransactionId);
                    moonPayOrder.setCustomerId(customerId);
                    moonPayOrder.setPaymentMethod(paymentMethod);
                    moonPayOrder.setBaseCurrency(baseCurrency);
                    moonPayOrder.setCurrency(currency);
                    moonPayOrder.setPayState(PayOrderState.CREATE.getValue());
                    // 保存原始完整数据
                    moonPayOrder.setData(data.toJSONString());
                    mongoTemplate.insert(moonPayOrder);
                    log.info("moonPay创建购买订单成功: " + id);
                }
            } else if ("transaction_updated".equalsIgnoreCase(type)) {
                // 买入成功
                String status = data.getString("status");
                if ("completed".equalsIgnoreCase(status)) {
                    // 买入成功
                    MoonPayOrder moonPayOrder = moonPayOrderMap.get(id);
                    if (moonPayOrder == null) {
                        moonPayOrder = mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), MoonPayOrder.class);
                        if (moonPayOrder != null) {
                            moonPayOrderMap.put(id, moonPayOrder);
                        }
                    }
                    if (moonPayOrder == null) {
                        log.error("收到购买交易完成，moonPay订单不存在，id=" + id);
                        return;
                    }
                    if (moonPayOrder.getPayState() == PayOrderState.SUCCESS.getValue() || moonPayOrder.getPayState() == PayOrderState.REPAIR.getValue()) {
                        log.error("moonPay交易订单已经处理过Id=" + moonPayOrder.getId());
                        return;
                    }
                    // 修改交易订单状态
                    moonPayOrder.setPayState(PayOrderState.SUCCESS.getValue());
                    mongoTemplate.save(moonPayOrder);
                    // 移除内存订单
                    moonPayOrderMap.remove(id);
                    // 买入成功逻辑处理

                }
            } else if ("sell_transaction_created".equalsIgnoreCase(type)) {
                // 创建卖出交易订单（暂不接入）
            } else if ("sell_transaction_updated".equalsIgnoreCase(type)) {
                // 卖出成功（暂不接入）
            }
        } catch (Exception e) {
            log.error("moonPay交易回调异常：", e);
        }
    }

}