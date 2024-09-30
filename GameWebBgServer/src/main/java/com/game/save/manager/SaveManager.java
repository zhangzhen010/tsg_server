package com.game.save.manager;

import com.game.server.SaveServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 保存线程管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/15 11:42
 */
@Component
@Log4j2
@Getter
@Setter
public class SaveManager {

    private @Resource MongoTemplate mongoTemplate;
    /**
     * 保存玩家数据线程
     */
    private SaveServer playerSave;
    /**
     * 保存离散数据
     */
    private SaveServer groupSave;
    /**
     * 保存事件数据
     */
    private SaveServer eventSave;
    /**
     * 保存活动数据
     */
    private SaveServer activitySave;
    /**
     * 保存逻辑服务器数据
     */
    private SaveServer serverSave;

    @PostConstruct
    public void init() {
        // 保存玩家数据线程
        setPlayerSave(new SaveServer("savePlayerServer", mongoTemplate));
        getPlayerSave().start();
        // 保存离散数据
        setGroupSave(new SaveServer("saveGroup", mongoTemplate));
        getGroupSave().start();
        // 保存事件数据
        setEventSave(new SaveServer("saveEvent", mongoTemplate));
        getEventSave().start();
        // 保存活动数据
        setActivitySave(new SaveServer("saveActivity", mongoTemplate));
        getActivitySave().start();
        // 保存逻辑服务器数据
        setServerSave(new SaveServer("saveLogicServer", mongoTemplate));
        getServerSave().start();
    }

}
