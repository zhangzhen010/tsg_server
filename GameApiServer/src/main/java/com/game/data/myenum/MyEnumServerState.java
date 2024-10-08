package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumServerState
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/16 22:19
 */
@Getter
public enum MyEnumServerState {
	/**
	 * 维护
	 */
	WH(1, "维护"),
	/**
	 * 期待
	 */
	QD(2, "期待"),
	/**
	 * 流畅(推荐)
	 */
	LC(3, "流畅(推荐)"),
	/**
	 * 爆满
	 */
	BM(4, "爆满"),

	;

	private final Integer type;

	private final String name;

	private static final Map<Integer, MyEnumServerState> map = new HashMap<>();

	private static final List<MyEnumServerState> list = new ArrayList<>();

	MyEnumServerState(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static MyEnumServerState getMyEnumServerState(Integer type) {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					MyEnumServerState[] values = MyEnumServerState.values();
					for (MyEnumServerState value : values) {
						map.put(value.getType(), value);
					}
				}
			}
		}
		return map.get(type);
	}

	public static List<MyEnumServerState> getList() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					list.addAll(Arrays.asList(MyEnumServerState.values()));
				}
			}
		}
		return list;
	}

}