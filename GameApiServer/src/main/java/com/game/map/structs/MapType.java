package com.game.map.structs;

/**
 * 关卡类型(对应配置表Level表type字段)
 * 
 * @author zhangzhen
 * @time 2023年3月30日
 */
public enum MapType {

	/**
	 * 占位
	 */
	TYPE0(0),
	/**
	 * 主关卡
	 */
	TYPE1(1),

	;

	private int type;

	private MapType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
