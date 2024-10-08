package com.game.data.container;

import com.game.data.bean.B_potential_skill_Bean;
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
 * @time 2024/08/05 13:53
 * B_potential_skill_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_potential_skill_Container implements IContainer {

	private List<B_potential_skill_Bean> list;

	private final HashMap<Integer, B_potential_skill_Bean> map = new HashMap<>();
	/**
	 * <技能组id，等级>
	 */
	private Map<Integer, Map<Integer, B_potential_skill_Bean>> groupIdLvMap = new HashMap<>();

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_potential_skill_Bean.class);
		for (B_potential_skill_Bean bean : list) {
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
			Map<Integer, Map<Integer, B_potential_skill_Bean>> tempGroupIdLvMap = new HashMap<>();
			for (B_potential_skill_Bean bean : list) {
				tempGroupIdLvMap.computeIfAbsent(bean.getGroupId(), k -> new HashMap<>()).put(bean.getLevel(), bean);
			}
			groupIdLvMap = tempGroupIdLvMap;
		} catch (Exception e) {
		    log.error("B_potential_skill_Bean配置加载异常：", e);
		}
	}

}