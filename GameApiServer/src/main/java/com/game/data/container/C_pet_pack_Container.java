package com.game.data.container;

import com.game.data.bean.B_pet_pack_Bean;
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

/**
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/21 13:57
 * B_pet_pack_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_pet_pack_Container implements IContainer {

	private List<B_pet_pack_Bean> list;

	private final HashMap<Integer, B_pet_pack_Bean> map = new HashMap<>();

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_pet_pack_Bean.class);
		for (B_pet_pack_Bean bean : list) {
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

		} catch (Exception e) {
		    log.error("B_pet_pack_Bean配置加载异常：", e);
		}
	}

}