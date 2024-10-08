package com.game.log.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

/**
 * 玩家资源道具变化日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/19 16:49
 */
@Getter
@Setter
public class PlayerItemLog extends PlayerLog {

    @Transient
    private String tabName = "playerItemLog_";

    /**
     * 资源道具唯一值
     */
    private long itemId;
    /**
     * 资源道具配置id
     */
    private int itemConfigId;
    /**
     * 资源道具名字（不存储，web后台根据原因id解析）
     */
    @Transient
    private String itemName;
    /**
     * 资源道具变化前数量
     */
    private long before;
    /**
     * 资源道具变化后数量
     */
    private long after;
    /**
     * 资源道具变化原因id
     */
    private int logReason;
    /**
     * 物品扩展信息(根据不同的物品信息解析内容)
     */
    private String extInfo = "";
    /**
     * 资源道具变化原因文字内容（不存储，web后台根据原因id解析）
     */
    @Transient
    private String logReasonString;

}
