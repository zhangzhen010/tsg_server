package com.game.data.container;

import com.game.data.bean.B_survivor_fetter_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/31 17:27
 * B_survivor_fetter_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_survivor_fetter_Container implements IContainer {

	private List<B_survivor_fetter_Bean> list;

	private final HashMap<Integer, B_survivor_fetter_Bean> map = new HashMap<>();
	/**
	 * 组，等级
	 */
	private Map<Integer, Map<Integer, B_survivor_fetter_Bean>> groupLvMap = new HashMap<>();

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_survivor_fetter_Bean.class);
		for (B_survivor_fetter_Bean bean : list) {
			map.put(bean.getId(), bean);
		}

		init();
	}

	/**
	 * 配置是否为空
	 */
	public boolean isEmpty(){
		return list.isEmpty();
	}

	/**
	 * 初始化(实现加载完成后数据处理)
	 */
	public void init() {
		try {
			Map<Integer, Map<Integer, B_survivor_fetter_Bean>> tempGroupLvMap = new HashMap<>();
			for (B_survivor_fetter_Bean bean : list) {
				tempGroupLvMap.computeIfAbsent(bean.getGroupId(), k -> new HashMap<>()).put(bean.getLevel(), bean);
			}
			groupLvMap = tempGroupLvMap;
		} catch (Exception e) {
			log.error("加载B_survivor_fetter_Bean配置异常：", e);
		}
	}

}