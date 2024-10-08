package com.game.prop.structs;

/**
 * 道具使用类型
 * 
 * @author zhangzhen
 * @time 2023年3月14日
 */
public enum PropType {

	/**
	 * 临时占位
	 */
	ZW(0),
	/**
	 * 常规道具
	 */
	COMMON(1),
	/**
	 * 生成道具
	 */
	PRODUCTION(2),
	/**
	 * 英雄卡牌
	 */
	HERO(3),
	/**
	 * 英雄碎片
	 */
	HERO_DEBRIS(4),
	/**
	 * 经验道具
	 */
	EXP(5),
	/**
	 * 道具碎片
	 */
	PROP_DEBRIS(6),
	/**
	 * 加速道具
	 */
	SPEED_UP(7),

	/**
	 * 符咒
	 */
	INCANT(9),

	/**
	 * 升星道具（加速道具)
	 */
	STAR(10),
	/**
	 * 掉落组包道具
	 */
	DROP(11),
	/**
	 * 装备图纸
	 */
	DRAW(13),
	/**
	 * 图纸碎片
	 */
	DRAW_FRAG(14),
	/**
	 * 装备
	 */
	EQUIP(15),
	/**
	 * 第二被动
	 */
	SECOND(17);

	;

	int type;

	PropType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
