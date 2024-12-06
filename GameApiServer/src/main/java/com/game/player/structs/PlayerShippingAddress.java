package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

/**
 * 收货地址
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/10/28 10:52
 */
@Getter
@Setter
public class PlayerShippingAddress {

    /**
     * 国家
     */
    private String country = "";
    /**
     * 国コード
     */
    private String regionCode = "";
    /**
     * 邮编
     */
    private String postcode = "";
    /**
     * 都市名
     */
    private String city = "";
    /**
     * 填写人名称
     */
    private String name = "";
    /**
     * 电话
     */
    private String phone = "";
    /**
     * 都道府県
     */
    private String prefecture = "";
    /**
     * 地址
     */
    private String address = "";
    /**
     * 地址二(可为空)
     */
    private String address2 = "";
    /**
     * 公司(可为空)
     */
    private String company = "";
    /**
     * 可为空
     */
    private String division = "";
    /**
     * 地址类型(0:国内,1国外)
     */
    private int addressType;
    /**
     * 物品code
     */
    @Transient
    private transient String code = "";

}
