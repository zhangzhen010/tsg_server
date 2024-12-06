package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 卡片历史数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/10/25 21:05
 */
@Getter
@Setter
@Document(collection = "gachaCardHistory")
public class GachaCardHistory {

    /**
     * 历史记录唯一id
     */
    @Id
    private long id;
    /**
     * 玩家唯一id
     */
    private long playerId;
    /**
     * 卡片唯一id
     */
    private String gachaCardId = "";
    /**
     * 卡片历史记录类型（MyEnumCardHistoryType.name）
     */
    private String historyType = "";
    /**
     * 卡片历史记录值(返现candy or sql or 实体卡)
     */
    private String value;
    /**
     * 卡片历史记录状态 0=进行中 1=已完成
     */
    private int state;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * burn交易id
     */
    private String transactionId;
    /**
     * 卡片信息（不存储，根据卡片唯一id获取卡片）
     */
    @Transient
    private transient GachaCard gachaCard;

}
