package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumEquipType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumEquipType {
	/**
	 * 帽子
	 */
	HAT(1, 0),
	/**
	 * 鞋子
	 */
	SHOES(2, 1),
	/**
	 * 项链
	 */
	NECKLACE(3, 2),
	/**
	 * 猎刀
	 */
	HUNTING_KNIFE(4, 3),
	/**
	 * 裤子
	 */
	PANTS(5, 4),
	/**
	 * 手套
	 */
	GLOVE(6, 5),
	/**
	 * 灯
	 */
	LIGHTS(7, 6),
	/**
	 * 武器
	 */
	WEAPON(8, 7),
	/**
	 * 耳饰
	 */
	EARRING(9, 8),
	/**
	 * 背包
	 */
	BACKPACK(10, 9),
	/**
	 * 戒子
	 */
	RING(11, 10),
	/**
	 * 衣服
	 */
	CLOTHES(12, 11),

	;

	private final Integer type;

	private final Integer index;

	private static final Map<Integer, MyEnumEquipType> map = new HashMap<>();

	private static final List<MyEnumEquipType> list = new ArrayList<>();

	MyEnumEquipType(Integer type, Integer index) {
		this.type = type;
		this.index = index;
	}

	public static MyEnumEquipType getMyEnumEquipType(Integer type) {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					MyEnumEquipType[] values = MyEnumEquipType.values();
					for (MyEnumEquipType value : values) {
						map.put(value.getType(), value);
					}
				}
			}
		}
		return map.get(type);
	}

	public static List<MyEnumEquipType> getList() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					list.addAll(Arrays.asList(MyEnumEquipType.values()));
				}
			}
		}
		return list;
	}

}