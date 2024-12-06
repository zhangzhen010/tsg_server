package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家与客服的聊天消息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/11/18 9:21
 */
@Getter
@Setter
@Document(collection = "tsgChat")
public class TsgChat {

    /**
     * 玩家唯一id（也是玩家对应的聊天数据）
     */
    @Id
    private Long playerId;
    /**
     * 消息列表
     */
    private List<TsgChatMessage> msgList = new ArrayList<>();
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;

}
