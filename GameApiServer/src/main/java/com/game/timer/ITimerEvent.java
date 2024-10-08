package com.game.timer;

import com.game.command.ICommand;

/**
 * 事件的接口
 * 
 * @author zhangzhen
 * @date 2017年8月15日
 * @version 1.0
 */
public interface ITimerEvent extends ICommand {

	/**
	 * 保持时间
	 * 
	 * @return
	 */
    long remain();

}
