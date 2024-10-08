package com.game.data.structs;

/**
 * 数据容器
 * 
 * @author zhangzhen
 * @date 2017年8月14日
 * @version 1.0
 */
public interface IContainer {

	/**
	 * 加载
	 */
    void load();

	/**
	 * 配置是否为空
	 * 
	 * @return
	 */
    boolean isEmpty();

}
