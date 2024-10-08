package com.game.data.container;

import com.game.data.bean.B_quest_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzhen
 * @version 1.0.0
 * B_quest_Bean数据容器
 */
@Component
@Getter
public class C_quest_Container implements IContainer {

    private List<B_quest_Bean> list;

    private HashMap<Integer, B_quest_Bean> map = new HashMap<>();
    /**
     * 任务map key=任务类型QuestType value=所属任务列表（B_quest_Bean表）
     */
    private Map<Integer, List<B_quest_Bean>> questTypeMap;

    private @Resource
    @Qualifier("dataMongo") MongoTemplate mongoTemplate;

    public void load() {
        list = mongoTemplate.findAll(B_quest_Bean.class);
        for (B_quest_Bean bean : list) {
            map.put(bean.getId(), bean);
        }

        init();
    }

    /**
     * 配置是否为空
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 初始化(实现加载完成后数据处理)
     */
    public void init() {
        // 任务类型对应任务配置
        Map<Integer, List<B_quest_Bean>> tempQuestTypeMap = new HashMap<>();
        for (B_quest_Bean bean : list) {
            tempQuestTypeMap.computeIfAbsent(bean.getQuestType(), k -> new ArrayList<>()).add(bean);
        }
        questTypeMap = tempQuestTypeMap;
    }

}