package com.game.player.structs;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * OpenLogI发货信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 20:57
 */
@Getter
@Setter
@Document(collection = "openLogiShipments")
public class OpenLogiShipments {

    /**
     * 唯一id
     */
    @Id
    private String id;
    /**
     * 发货成功，openLogI返回信息
     */
    private JSONObject data;
    /**
     * 创建时间
     */
    private long createTime;

}
