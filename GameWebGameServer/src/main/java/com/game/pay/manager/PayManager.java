package com.game.pay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.award.manager.AwardManager;
import com.game.award.structs.AwardList;
import com.game.config.RestTemplateFactory;
import com.game.controller.structs.ResponseBean;
import com.game.data.bean.B_pay_wallet_Bean;
import com.game.data.define.MyDefineItemChangeReason;
import com.game.data.manager.DataManager;
import com.game.datagroup.structs.SolanaTransferSolQueueData;
import com.game.pay.structs.PayOrderState;
import com.game.pay.structs.WalletPayOrder;
import com.game.player.manager.PlayerManager;
import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.PlayerPay;
import com.game.player.structs.WebPlayer;
import com.game.player.timer.SolanaAddTransferSolQueueTimer;
import com.game.solana.manager.SolanaManager;
import com.game.thread.manager.ThreadManager;
import com.game.utils.CodeUtils;
import com.game.utils.GameUtil;
import com.game.utils.ID;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 充值管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/4 9:22
 */
@Component
@Log4j2
public class PayManager {

    // moonPay参数
    private @Value("${server.moonPay.url}") String moonPayUrl;
    private @Value("${server.moonPay.apiKey}") String moonPayApiKey;
    private @Value("${server.moonPay.secret}") String moonPaySecret;
    private @Value("${server.moonPay.webhook}") String moonPayWebhook;
    public @Value("${server.solana.payWalletAddress}") String payWalletAddress;
    private @Resource RestTemplateFactory restTemplateFactory;
    private @Resource DataManager dataManager;
    private @Resource MongoTemplate mongoTemplate;
    private @Resource ThreadManager threadManager;
    private @Resource PlayerManager playerManager;
    private @Resource PlayerOtherManager playerOtherManager;
    private @Resource AwardManager awardManager;
    @Lazy
    private @Resource SolanaManager solanaManager;

    /**
     * moonPay获取加密后的url
     *
     * @param player
     * @param originalUrl
     * @return
     */
    public String moonPayUrlSign(WebPlayer player, String originalUrl) {
        try {
            String signature = CodeUtils.createHmacSha256(originalUrl, moonPaySecret);
            String encodeString = URLEncoder.encode(signature, StandardCharsets.UTF_8);
            String urlWithSignature = originalUrl + "&signature=" + encodeString;
            log.info("MoonPay Url签名,originalUrl=" + originalUrl + ",signature=" + signature + ",urlWithSignature=" + urlWithSignature);
            return urlWithSignature;
        } catch (Exception e) {
            log.error("moonPay获取加密后的url异常", e);
            return originalUrl;
        }
    }

    /**
     * 请求钱包创建订单充值
     *
     * @param baseCurrencyCode
     * @param baseCurrencyAmount
     * @param currencyCode
     * @return
     */
    public ResponseBean<Object> reqWalletPayCreateOrder(WebPlayer player, String baseCurrencyCode, int baseCurrencyAmount, String currencyCode) {
        try {
            // 以后如果不使用moonpay了，可以使用免费的方式查询usd与sol的汇率
            // https://api.coingecko.com/api/v3/simple/price?ids=solana&vs_currencies=usd
            log.info("请求钱包充值，创建订单，player=" + player.getPlayerId() + " baseCurrencyCode=" + baseCurrencyCode + " baseCurrencyAmount=" + baseCurrencyAmount + " currencyCode=" + currencyCode);
            // 构建请求URL
            String url = moonPayUrl + currencyCode + "/buy_quote?" + "baseCurrencyCode=" + baseCurrencyCode + "&baseCurrencyAmount=" + baseCurrencyAmount + "&apiKey=" + moonPayApiKey;
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            // 发送请求
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);
            // 返回数据
            JSONObject resultJsonObject = new JSONObject();
            // 处理响应
            if (response.getStatusCode().is2xxSuccessful()) {
                // 获取充值配置
                B_pay_wallet_Bean bean = dataManager.c_pay_wallet_Container.getWalletPayBeanMap().get(baseCurrencyCode).get(baseCurrencyAmount);
                JSONObject jsonObject = JSON.parseObject(response.getBody());
                double quoteCurrencyAmount = jsonObject.getDoubleValue("quoteCurrencyAmount");
                // 放入实时报价
                resultJsonObject.put("realTime", jsonObject);
                // 创建订单
                WalletPayOrder payOrder = new WalletPayOrder();
                payOrder.setGameOrderId(Long.toString(ID.getId()));
                payOrder.setPfOrderId("");
                payOrder.setOrderType("wallet");
                payOrder.setPayId(bean.getId());
                payOrder.setSol(quoteCurrencyAmount);
                payOrder.setPlayerId(player.getPlayerId());
                payOrder.setPlayerName(player.getPlayerName());
                payOrder.setPayState(PayOrderState.CREATE.getValue());
                payOrder.setCreateTime(System.currentTimeMillis());
                payOrder.setExtInfo("");
                // 放入订单
                resultJsonObject.put("order", payOrder);
                mongoTemplate.insert(payOrder);
                log.info("请求钱包充值，创建订单成功player=" + player.getPlayerId() + " 充值货币：" + baseCurrencyCode + " 充值金额：" + baseCurrencyAmount + " 充值币种：" + currencyCode + " 充值配置：" + bean + " 充值订单：" + JSON.toJSONString(payOrder));
                // 返回客户端
                return ResponseBean.success(resultJsonObject);
            } else {
                log.error("请求钱包充值，创建订单失败，获取实时报价错误：" + response.getBody());
                return ResponseBean.fail("MoonPay API request failed with status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            log.error("请求钱包充值，创建订单异常：", e);
            return ResponseBean.fail("request wallet pay create order exception:" + e.getMessage());
        }
    }

    /**
     * 请求钱包充值
     *
     * @param player
     * @param gameOrderId
     * @param transactionId
     * @return
     */
    public ResponseBean<Object> reqWalletPay(WebPlayer player, String gameOrderId, String transactionId) {
        try {
            log.info("请求钱包充值，player=" + player.getPlayerId() + " gameOrderId=" + gameOrderId + " transactionId=" + transactionId);
            // 调用Solana转移NFT
            SolanaAddTransferSolQueueTimer solanaAddTransferQueueTimer = new SolanaAddTransferSolQueueTimer(player.getPlayerId(), gameOrderId, transactionId);
            threadManager.getThread(threadManager.getSolanaNftThreadName()).addTimerEvent(solanaAddTransferQueueTimer);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("请求钱包充值异常：", e);
            return ResponseBean.fail("request wallet pay exception:" + e.getMessage());
        }
    }

    /**
     * 请求钱包充值查询充值状态
     *
     * @param player
     * @return
     */
    public ResponseBean<Object> reqWalletPayState(WebPlayer player) {
        try {
            Iterator<SolanaTransferSolQueueData> iterator = solanaManager.getDataGroupTransferSolQueue().getSolanaTransferSolQueueDataList().iterator();
            while (iterator.hasNext()) {
                SolanaTransferSolQueueData data = iterator.next();
                if (data.getPlayerId() == player.getPlayerId()) {
                    // 已有充值任务，不能充值
                    return ResponseBean.success(false);
                }
            }
            // 可以充值
            return ResponseBean.success(true);
        } catch (Exception e) {
            log.error("请求钱包充值查询充值状态异常：", e);
            return ResponseBean.fail("request wallet pay state exception:" + e.getMessage());
        }
    }

    /**
     * 更新玩家充值数据
     *
     * @param data
     * @param sourceWalletAddress
     * @param targetWalletAddress
     * @param transferSol
     */
    public void updatePlayerPayData(SolanaTransferSolQueueData data, String sourceWalletAddress, String targetWalletAddress, Long transferSol) {
        try {
            long nowPayTime = System.currentTimeMillis();
            // 获取充值订单
            WalletPayOrder payOrder = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(data.getGameOrderId())), WalletPayOrder.class);
            if (payOrder == null) {
                log.error("找不到钱包充值订单或已处理 gameOrderId=" + data.getGameOrderId());
                return;
            }
            if (payOrder.getPayState() == PayOrderState.SUCCESS.getValue() || payOrder.getPayState() == PayOrderState.REPAIR.getValue()) {
                log.error("充值订单已经处理过，不进行充值payOrderId=" + payOrder.getGameOrderId());
                return;
            }
            // 获取玩家
            WebPlayer player = playerManager.getPlayer(data.getPlayerId(), true);
            if (!player.getWalletAddress().equals(sourceWalletAddress)) {
                log.error("钱包充值失败，充值订单，支付者钱包地址不匹配，playerId=" + data.getPlayerId() + " sourceWalletAddress=" + sourceWalletAddress + " playerWalletAddress=" + player.getWalletAddress());
                return;
            }
            if (!payWalletAddress.equals(targetWalletAddress)) {
                log.error("钱包充值失败，充值订单，收款钱包地址不匹配，playerId=" + data.getPlayerId() + " targetWalletAddress=" + targetWalletAddress + " payWalletAddress=" + payWalletAddress);
                return;
            }
            //验证金额
            long solValue = (long) (payOrder.getSol() * GameUtil.sol);
            if (solValue != transferSol) {
                log.error("钱包充值失败，充值sol数量不匹配，playerId=" + data.getPlayerId() + " 订单需要充值sol=" + solValue + " 实际转账充值sol=" + transferSol);
                return;
            }
            payOrder.setPfOrderId(data.getTransactionId());
            payOrder.setSourceWalletAddress(sourceWalletAddress);
            payOrder.setTargetWalletAddress(targetWalletAddress);
            payOrder.setSolNum(transferSol);
            // 修改充值状态
            payOrder.setPayState(PayOrderState.SUCCESS.getValue());
            // 保存订单
            mongoTemplate.save(payOrder);
            // 获取充值配置
            B_pay_wallet_Bean walletBean = dataManager.c_pay_wallet_Container.getMap().get(payOrder.getPayId());
            AwardList awardList = awardManager.getAwardList(walletBean.getRewardId());
            // 发放奖励
            awardManager.sendAwardList(player, awardList.getAwardList(), MyDefineItemChangeReason.PAY_GET);
            // 获取玩家充值数据
            PlayerPay playerPay = playerOtherManager.getPlayerPay(player, true);
            // 统计充值次数
            playerPay.getWalletPayNumMap().merge(walletBean.getId(), 1, Integer::sum);
            // 记录实际充值的钱(单位：分)
            playerPay.setSolBalance(playerPay.getSolBalance() + transferSol);
            // 统计付费次数（gm也算在内）
            playerPay.setPayCount(playerPay.getPayCount() + 1);
            // 更新充值时间
            playerPay.setLastPayTime(nowPayTime);
            playerManager.savePlayer(player);
            log.info("充值成功发放奖励，更新玩家充值数据 playerId=" + player.getPlayerId() + " payOrderId=" + payOrder.getGameOrderId() + " awardList=" + JSON.toJSONString(awardList));
        } catch (Exception e) {
            log.error("更新玩家充值数据", e);
        }
    }

}
