package com.game.data.container;

import com.game.data.bean.B_survivor_level_Bean;
import com.game.data.structs.IContainer;
import com.game.utils.ListUtils;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/18 17:53
 * B_survivor_level_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_survivor_level_Container implements IContainer {

    private List<B_survivor_level_Bean> list;

    private final HashMap<Integer, B_survivor_level_Bean> map = new HashMap<>();
    /**
     * key=精怪id value=<精怪等级，对应的精怪配置>
     */
    private HashMap<Integer, HashMap<Integer, B_survivor_level_Bean>> idLvMap;
    /**
     * key=精怪id value=<精怪等级，等级累积属性>
     */
    private HashMap<Integer, HashMap<Integer, List<Long>>> idLvAttrSumMap;

    private @Resource
    @Qualifier("dataMongo") MongoTemplate mongoTemplate;

    public void load() {
        list = mongoTemplate.findAll(B_survivor_level_Bean.class);
        for (B_survivor_level_Bean bean : list) {
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
        try {
            HashMap<Integer, HashMap<Integer, B_survivor_level_Bean>> tempIdLvMap = new HashMap<>();
            HashMap<Integer, HashMap<Integer, List<Long>>> tmpLvAttrSumMap = new HashMap<>();
            for (B_survivor_level_Bean bean : list) {
                tempIdLvMap.computeIfAbsent(bean.getGroupId(), v -> new HashMap<>()).put(bean.getLevel(), bean);
                if (bean.getAttribute() != 0) {
                    List<Long> attrList = tmpLvAttrSumMap.computeIfAbsent(bean.getGroupId(), v -> new HashMap<>()).computeIfAbsent(bean.getLevel(), k -> new ArrayList<>());
                    ListUtils.addList(attrList, bean.getAttribute().longValue(), bean.getAttributeValue().longValue());
                }
            }
            idLvMap = tempIdLvMap;
            idLvAttrSumMap = tmpLvAttrSumMap;
        } catch (Exception e) {
            log.error("加载B_survivor_level_Bean配置异常：", e);
        }
    }

}