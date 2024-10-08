package com.game.data.container;

import com.game.data.bean.B_function_unlock_Bean;
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
 * @time 2024/06/14 11:42
 * B_function_unlock_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_function_unlock_Container implements IContainer {

    private List<B_function_unlock_Bean> list;

    private final HashMap<Integer, B_function_unlock_Bean> map = new HashMap<>();
    /**
     * key=解锁枚举MyEnumFunctionUnlockType.type value=所属类型配置列表
     */
    private Map<Integer, List<B_function_unlock_Bean>> unlockTypeMap;

    private @Resource
    @Qualifier("dataMongo") MongoTemplate mongoTemplate;

    public void load() {
        list = mongoTemplate.findAll(B_function_unlock_Bean.class);
        for (B_function_unlock_Bean bean : list) {
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
        Map<Integer, List<B_function_unlock_Bean>> tempUnlockTypeMap = new HashMap<>();
        for (B_function_unlock_Bean bean : list) {
            tempUnlockTypeMap.computeIfAbsent(bean.getUnlockType(), k -> new ArrayList<>()).add(bean);
        }
        unlockTypeMap = tempUnlockTypeMap;
    }

}