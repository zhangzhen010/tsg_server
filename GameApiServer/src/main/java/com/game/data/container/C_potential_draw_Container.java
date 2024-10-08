package com.game.data.container;

import com.game.data.bean.B_potential_draw_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
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
 * B_potential_draw_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_potential_draw_Container implements IContainer {

	private List<B_potential_draw_Bean> list;

	private final HashMap<Integer, B_potential_draw_Bean> map = new HashMap<>();
	/**
	 * 区间取bean
	 */
	private Map<Pair<Integer, Integer>, B_potential_draw_Bean> rangeMap = new HashMap<>();
	/**
	 * 最大的提升量
	 */
	private int maxNum;

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_potential_draw_Bean.class);
		for (B_potential_draw_Bean bean : list) {
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
			Map<Pair<Integer, Integer>, B_potential_draw_Bean> tempRangeMap = new HashMap<>();
			for (B_potential_draw_Bean bean : list) {
				Pair<Integer, Integer> range = Pair.of(bean.getRangeMin(), bean.getRangeMax());
				tempRangeMap.put(range, bean);
				if (bean.getRangeMax() > maxNum) {
					maxNum = bean.getRangeMax();
				}
			}
			rangeMap = tempRangeMap;
		} catch (Exception e) {
		    log.error("B_potential_draw_Bean配置加载异常：", e);
		}
	}

}