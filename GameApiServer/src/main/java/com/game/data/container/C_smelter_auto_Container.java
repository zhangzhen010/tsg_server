package com.game.data.container;

import com.game.data.bean.B_smelter_auto_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 * B_smelter_auto_Bean数据容器
 */
@Component
public class C_smelter_auto_Container implements IContainer {

	private List<B_smelter_auto_Bean> list;

	private final HashMap<Integer, B_smelter_auto_Bean> map = new HashMap<>();

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_smelter_auto_Bean.class);
		for (B_smelter_auto_Bean bean : list) {
			map.put(bean.getId(), bean);
		}

		init();
	}

	public List<B_smelter_auto_Bean> getList(){
		return list;
	}

	public HashMap<Integer, B_smelter_auto_Bean> getMap(){
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