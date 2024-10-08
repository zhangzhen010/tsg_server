package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumActivityType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/14 16:38
 */
@Getter
public enum MyEnumActivityType {
	/**
	 * 小月卡活动
	 */
	YUEKA(1, "小月卡活动"),
	/**
	 * 大月卡活动
	 */
	YUEKA_SUPER(2, "大月卡活动"),
	/**
	 * 抽卡活动
	 */
	LUCKY_DRAW(3, "抽卡活动"),

	;

	private final Integer type;

	private final String name;

	private static final Map<Integer, MyEnumActivityType> map = new HashMap<>();

	private static final List<MyEnumActivityType> list = new ArrayList<>();

	MyEnumActivityType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static MyEnumActivityType getMyEnumActivityType(Integer type) {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					MyEnumActivityType[] values = MyEnumActivityType.values();
					for (MyEnumActivityType value : values) {
						map.put(value.getType(), value);
					}
				}
			}
		}
		return map.get(type);
	}

	public static List<MyEnumActivityType> getList() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					list.addAll(Arrays.asList(MyEnumActivityType.values()));
				}
			}
		}
		return list;
	}

}