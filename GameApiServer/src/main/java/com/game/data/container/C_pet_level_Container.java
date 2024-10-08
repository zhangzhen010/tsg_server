package com.game.data.container;

import com.game.data.bean.B_pet_level_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/22 11:51
 * B_pet_level_Bean数据容器
 */
@Component
@Getter
public class C_pet_level_Container implements IContainer {

    private List<B_pet_level_Bean> list;

    private final HashMap<Integer, B_pet_level_Bean> map = new HashMap<>();
    /**
     * 品质->等级->配置
     */
    private Map<Integer, Map<Integer, B_pet_level_Bean>> qualityLvBeanMap;

    private @Resource
    @Qualifier("dataMongo") MongoTemplate mongoTemplate;

    public void load() {
        list = mongoTemplate.findAll(B_pet_level_Bean.class);
        for (B_pet_level_Bean bean : list) {
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
        Map<Integer, Map<Integer, B_pet_level_Bean>> tempQualityLvBeanMap = new HashMap<>();
        for (B_pet_level_Bean bean : list) {
            tempQualityLvBeanMap.computeIfAbsent(bean.getQuality(), k -> new HashMap<>()).put(bean.getLevel(), bean);
        }
        qualityLvBeanMap = tempQualityLvBeanMap;
    }

}