package com.game.utils.openlogi;

import lombok.Data;

@Data
public class OtherAddress {

    /**
     * 国コード
     */
    private String region_code;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * 都市名
     */
    private String city;
    /**
     * 填写人名称
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 地址
     */
    private String address;

}
