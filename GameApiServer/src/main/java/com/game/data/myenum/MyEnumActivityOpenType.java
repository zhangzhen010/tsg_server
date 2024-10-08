package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumActivityOpenType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/14 16:38
 */
@Getter
public enum MyEnumActivityOpenType {
	/**
	 * 永远开启
	 */
	FOREVER(0, "永远开启"),
	/**
	 * 指定日期
	 */
	TIME(1, "指定日期"),
	/**
	 * 开服天数
	 */
	DAY(2, "开服天数"),

	;

	private final Integer type;

	private final String name;

	private static final Map<Integer, MyEnumActivityOpenType> map = new HashMap<>();

	private static final List<MyEnumActivityOpenType> list = new ArrayList<>();

	MyEnumActivityOpenType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static MyEnumActivityOpenType getMyEnumActivityOpenType(Integer type) {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					MyEnumActivityOpenType[] values = MyEnumActivityOpenType.values();
					for (MyEnumActivityOpenType value : values) {
						map.put(value.getType(), value);
					}
				}
			}
		}
		return map.get(type);
	}

	public static List<MyEnumActivityOpenType> getList() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					list.addAll(Arrays.asList(MyEnumActivityOpenType.values()));
				}
			}
		}
		return list;
	}

}