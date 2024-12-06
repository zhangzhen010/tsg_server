package com.game.utils.openlogi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.config.RestTemplateFactory;
import com.game.player.structs.PlayerShippingAddress;
import com.game.utils.ID;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * openLogi工具类（文档地址：<a href="https://api.openlogi.com/doc/api.html#operation/getItemById">...</a>）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/11/13 10:14
 */
@Component
@Log4j2
public class OpenLogiUtils {

    private @Resource RestTemplateFactory restTemplateFactory;
    private @Resource MongoTemplate mongoTemplate;

    private final String url = "https://api.openlogi.com/api";

    private final String bearerToken = "Bearer eyJ0eXsAiOiJKfV1QsdfiLCJhbGciONiJ9.eyJhdWQiOiIxIiwianRpIjoiMzFhMWQxYmU2ZTFmNThhNWRasmNWU2ZDdjZGY4NWM3YTM5M2JiMjZmNGQwYTJhMWJiODQ2NDZkMDZmOTFlYTkwYmFlN2RmMmNiMzExNjgxOTIiLCJpYXQiOjE3MTkzMDAyMDEsIm5iZiI6MTcxOTMwMDIwMSwiZXhwIjoxNzUwODM2MjAxLCJzdWIiOiI1YzczNWQxNy0wMTEwLTRlZmdMtOWU5NC0xMDc0YWMxZjA0ZGMiLCJzY29wZXMiOltdfQ.HxeLU98CJGzYCXJNxNOC_p_IzAuUoDS0XK9HVmK3tfEQ_aaMX0gcE7TgZMxp3jfJScaHcO1tXIf69SF-UcdPyWLimBgIZNiiAoV0GQfPHV3KMLb9QE9ikrpndaJyB4rPL0N-nRTgIZpnwmS0RlHJTVoM-Q290RWnx8qOT99FSuAhtyuXYZhJuju41bGySSuSDG2CUH59Vvc-kq7rDyN77y7Z7x-dDd3r_6BsssrC6hzLauBNZ8bu70OLsZb0y5K8eTfpi6LwUUTTIfgQTJ0H7fid5V9UnaVsJa077EuaM8J1nIRRNToziimXNF8WGlqTMtSWQvCa2XZHEmU0XzDs-STKgMrouezAC2B4_Y9Sma7gG1t5f0Jb33rdFaWa7Vwd2gt7JNxGyhxFxPaWl5P4sRh4oajr2tF5gaSOijJ6sM7GBNhemO1wpVZ4HADRjTJ9nFrE6xK1QlkbcNUkLJFszkPp9s_0yOw-ZWhvWiBDKJNrXGZo-FW4czUlgDS3u2vtBJyOes19zKcjfl6ql0Hve1nuRG825isIepMW4VneIrL8W8zDNj6jLO1dO6RXw_l0XFnzieOm4VRiCoJbB-sU3QAfIKrOQY-vN3E3tVgEUAYp_RF7VjDhwUHdI9j7NJ7Ya7EZ1B_WGUq2Ohwedh5qnAvBqwA";

    @PostConstruct
    public void init() {
        // 添加商品
//        addItems();
        // 获取所有商品信息
//        getItemsAll();
        // 查询商品信息
//        getItemsByItemCode("TSG_custom_maekake_abble");
        // 发货
//        {
//            PlayerShippingAddress address = new PlayerShippingAddress();
//            address.setCountry("中国");
//            address.setRegionCode("CN");
//            address.setPostcode("200040");
//            address.setCity("Shanghai");
//            address.setName("jerry");
//            address.setPhone("+8613564681722");
//            address.setAddress("Room 802, 758 Nanjing West Road, Jing'an District");
//            address.setAddressType(1);
//            address.setCode("YGY-wappan-set");
//            // 发送
//            JSONObject resultJsonObject = send(address);
//            // 记录数据
//            OpenLogiShipments openLogiShipments = new OpenLogiShipments();
//            openLogiShipments.setId(resultJsonObject.getString("id"));
//            openLogiShipments.setData(resultJsonObject);
//            openLogiShipments.setCreateTime(System.currentTimeMillis());
//            mongoTemplate.insert(openLogiShipments);
//        }
        // 查询物流订单
//        List<OpenLogiShipments> all = mongoTemplate.findAll(OpenLogiShipments.class);
//        // 模拟查询2个
////        JSONObject id = getShipmentsInfo(List.of(all.get(0).getData().getString("id")));
//        JSONObject id = getShipmentsInfo(List.of("FM305-S010312","FM305-S010293"));
//        System.out.println("查询订单：" + id.toString());
    }

    /**
     * 添加商品
     *
     * @return
     */
    public void addItems() {
        try {
            // 创建请求体
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("code", "TSG_TEST_00001");
            bodyJson.put("name", "测试添加商品1");
            bodyJson.put("price", "1000");
            String bodyString = bodyJson.toString();
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            // 创建 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);
            // 发送 POST 请求
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url + "/items", HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                // 打印响应体
                log.info("添加商品成功：" + response.getBody());
            } else {
                log.error("添加商品异常：" + response.getStatusCode() + " msg=" + response.getBody());
            }
        } catch (Exception e) {
            log.error("添加商品异常：", e);
        }
    }

    /**
     * 获取所有商品，这个接口文档是说指定id获取商品信息，但是测试的时候发现，这个接口返回的也是所有商品，所以这里就不写了
     */
    public void getItemsAll() {
        try {
            // 创建请求体
            JSONObject bodyJson = new JSONObject();
//        bodyJson.put("id", "FM305-I000257");
            bodyJson.put("stock", 1);
            String bodyString = bodyJson.toString();
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            // 创建 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);
            // 发送 GET 请求
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url + "/items", HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("查询所有商品返回：" + response.getBody());
                JSONObject resultJsonObject = JSON.parseObject(response.getBody());
            } else {
                log.error("获取所有商品数据异常：code=" + response.getStatusCode() + " msg=" + response.getBody());
            }
        } catch (Exception e) {
            log.error("获取所有商品数据异常：", e);
        }
    }

    /**
     * 根据账号查询商品
     */
    public JSONObject getItemsByItemCode(String code) {
        try {
            // 创建请求体
            JSONObject bodyJson = new JSONObject();
            // 注意，这里identifier 和 code只能二选一，不能同时指定
//        bodyJson.put("identifier", "SHOPIFY1670726418526-16066680651870");
            bodyJson.put("code", code);
            bodyJson.put("stock", 1);
            String bodyString = bodyJson.toString();
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            // 创建 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);
            // 发送 GET 请求
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url + "/items/FM305", HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // 打印响应体
                log.info("查询商品返回：" + response.getBody());
                return JSON.parseObject(response.getBody());
            } else {
                log.error("获取商品数据异常：" + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("获取商品数据异常：", e);
            return null;
        }
    }

    /**
     * 发货
     *
     * @param data
     */
    public JSONObject send(PlayerShippingAddress data) {
        try {
            // 创建请求体
            JSONObject bodyJson = new JSONObject();
            List<OpenLogiItems> openLogiItemsList = new ArrayList<>();
            OpenLogiItems item = new OpenLogiItems();
            item.setCode(data.getCode());
            item.setQuantity(1);
            if (data.getAddressType() == 0) {
                JPAddress jpDto = new JPAddress();
                BeanUtils.copyProperties(data, jpDto);
                jpDto.setAddress1(data.getAddress());

                bodyJson.put("recipient", jpDto);
            } else {
                OtherAddress otherDto = new OtherAddress();
                BeanUtils.copyProperties(data, otherDto);
                otherDto.setRegion_code(data.getRegionCode());

                bodyJson.put("recipient", otherDto);
                bodyJson.put("international", true);
                bodyJson.put("delivery_service", "DHL-EXPRESS");
                bodyJson.put("currency_code", "JPY");
                item.setPrice(1);
                item.setUnit_price(1);
            }
            // 货到付款5美元
            bodyJson.put("cash_on_delivery", true);
            bodyJson.put("total_for_cash_on_delivery", "5");
            bodyJson.put("tax_for_cash_on_delivery", "1");
            bodyJson.put("currency_code", "USD");

            openLogiItemsList.add(item);
            bodyJson.put("items", openLogiItemsList);
            bodyJson.put("identifier", "TSG_" + ID.getId());
            String bodyString = bodyJson.toString();

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            // 创建 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);
            // 发送 POST 请求
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url + "/shipments", HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                JSONObject resultJsonObject = JSON.parseObject(response.getBody());
                log.info("发送商品返回：" + response.getBody());
                return resultJsonObject;
            } else {
                log.error("发送商品异常：" + response.getStatusCode() + " msg=" + response.getBody());
                return null;
            }
        } catch (Exception e) {
            log.error("发送商品数据异常：", e);
            return null;
        }
    }

    /**
     * 查询物流信息
     *
     * @param idList 出库id列表
     * @return
     */
    public JSONObject getShipmentsInfo(List<String> idList) {
        try {
            // 创建请求体
            JSONObject bodyJson = new JSONObject();
            StringBuilder sb = new StringBuilder();
            // idList最大支持100个
            if (idList.size() > 100) {
                log.error("查询物流信息idList最大支持100个,idListSize=" + idList.size());
                return null;
            }
            for (int i = 0; i < idList.size(); i++) {
                String s = idList.get(i);
                sb.append(s);
                if (i != idList.size() - 1) {
                    sb.append(",");
                }
            }
            bodyJson.put("id", sb.toString());
            String bodyString = bodyJson.toString();
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            // 创建 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(bodyString, headers);
            // 发送 GET 请求
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url + "/shipments", HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // 打印响应体
                log.info("查询物流信息返回：" + response.getBody());
                return JSON.parseObject(response.getBody());
            } else {
                log.error("查询物流信息异常：" + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("查询物流信息异常：", e);
            return null;
        }
    }

}
