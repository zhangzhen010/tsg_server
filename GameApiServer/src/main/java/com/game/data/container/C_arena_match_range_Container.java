package com.game.data.container;

import com.game.data.bean.B_arena_match_range_Bean;
import com.game.data.structs.IContainer;
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
import java.util.Map;

/**
 * @author zhangzhen
 * @version 1.0
 * @time 2024/07/29 14:42
 * B_arena_match_range_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_arena_match_range_Container implements IContainer {

    private List<B_arena_match_range_Bean> list;

    private final HashMap<Integer, B_arena_match_range_Bean> map = new HashMap<>();
    /**
     * 积分区间上限列表(包含保底范围，总共6个)
     */
    private Map<Integer, List<Integer>> scoreMaxMap = null;
    /**
     * 积分区间下限列表(包含保底范围，总共6个)
     */
    private Map<Integer, List<Integer>> scoreMinMap = null;

    private @Resource
    @Qualifier("dataMongo") MongoTemplate mongoTemplate;

    public void load() {
        list = mongoTemplate.findAll(B_arena_match_range_Bean.class);
        for (B_arena_match_range_Bean bean : list) {
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
            Map<Integer, List<Integer>> tempScoreMaxMap = new HashMap<>();
            Map<Integer, List<Integer>> tempScoreMinMap = new HashMap<>();
            for (B_arena_match_range_Bean bean : list) {
                List<Integer> scoreMaxMap = new ArrayList<>();
                scoreMaxMap.add(bean.getRange1().get(0));
                scoreMaxMap.add(bean.getRange2().get(0));
                scoreMaxMap.add(bean.getRange3().get(0));
                scoreMaxMap.add(bean.getRange4().get(0));
                scoreMaxMap.add(bean.getRange5().get(0));
                scoreMaxMap.add(bean.getFixRange().get(0));
                tempScoreMaxMap.put(bean.getId(), scoreMaxMap);
                List<Integer> scoreMinMap = new ArrayList<>();
                scoreMinMap.add(bean.getRange1().get(1));
                scoreMinMap.add(bean.getRange2().get(1));
                scoreMinMap.add(bean.getRange3().get(1));
                scoreMinMap.add(bean.getRange4().get(1));
                scoreMinMap.add(bean.getRange5().get(1));
                scoreMinMap.add(bean.getFixRange().get(1));
                tempScoreMinMap.put(bean.getId(), scoreMinMap);
            }
            scoreMaxMap = tempScoreMaxMap;
            scoreMinMap = tempScoreMinMap;
        } catch (Exception e) {
            log.error("B_arena_match_range_Bean配置加载异常：", e);
        }
    }

}