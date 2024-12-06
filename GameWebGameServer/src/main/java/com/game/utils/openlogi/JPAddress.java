package com.game.utils.openlogi;

import lombok.Data;

@Data
public class JPAddress {

    /**
     * 邮编
     */
    private String postcode;
    /**
     * 填写人名称
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 都道府県
     */
    private String prefecture;
    /**
     * 地址
     */
    private String address1;
    /**
     * 地址二(可为空
     */
    private String address2;
    /**
     * 公司(可为空
     */
    private String company;
    /**
     * 可为空
     */
    private String division;
}
