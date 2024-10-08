package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumAdvertisingType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumAdvertisingType {
	/**
	 * 在线
	 */
	ONLINE(1),
	/**
	 * 熔炉升级时间缩短
	 */
	SMELTER_LV_TIME(2),
	/**
	 * 英雄免费抽取
	 */
	HERO_SUMMON(3),
	/**
	 * 幸存者免费营救
	 */
	SURVIVOR_RESCUE(4)
	;

	private final Integer type;

	private static final Map<Integer, MyEnumAdvertisingType> map = new HashMap<>();

	private static final List<MyEnumAdvertisingType> list = new ArrayList<>();

	MyEnumAdvertisingType(Integer type) {
		this.type = type;
	}

	public static MyEnumAdvertisingType getMyEnumAdvertisingType(Integer type) {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					MyEnumAdvertisingType[] values = MyEnumAdvertisingType.values();
					for (MyEnumAdvertisingType value : values) {
						map.put(value.getType(), value);
					}
				}
			}
		}
		return map.get(type);
	}

	public static List<MyEnumAdvertisingType> getList() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					list.addAll(Arrays.asList(MyEnumAdvertisingType.values()));
				}
			}
		}
		return list;
	}

}