package com.game.server.filter;

import com.game.command.ICommand;

/**
 * 
 * @author 命令执行过滤器
 *
 */
public interface ICommandFilter
{
	boolean filter(ICommand paramICommand);
}
