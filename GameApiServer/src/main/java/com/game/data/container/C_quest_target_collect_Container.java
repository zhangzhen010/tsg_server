package com.game.data.container;

import com.game.data.bean.B_quest_target_collect_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangzhen
 *
 * @version 1.0.0
 * B_quest_target_collect_Bean数据容器
 */
@Component
public class C_quest_target_collect_Container implements IContainer {

	private List<B_quest_target_collect_Bean> list;

	private HashMap<Integer, B_quest_target_collect_Bean> map = new HashMap<Integer, B_quest_target_collect_Bean>();

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_quest_target_collect_Bean.class);
		for (B_quest_target_collect_Bean bean : list) {
			map.put(bean.getId(), bean);
		}

		init();
	}

	public List<B_quest_target_collect_Bean> getList(){
		return list;
	}

	public HashMap<Integer, B_quest_target_collect_Bean> getMap(){
		return map;
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

	}

}