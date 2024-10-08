package com.game.manager.structs;

import com.game.data.structs.IContainer;

/**
 * 管理器接口
 * 
 * @author zhangzhen
 * @time 2023年5月23日
 */
public interface IManager {

	/**
	 * 当此管理器相关的配置发生变化时触发次方法
	 * 
	 * @param container 发生配置变化的container
	 */
	void updateConfig(IContainer container);

}
