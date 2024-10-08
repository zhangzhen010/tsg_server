package com.game.command;

/**
 * 需要执行的命令
 *
 * @author zhangzhen
 * @version 1.0
 * @date 2017年8月15日
 */
public interface ICommand extends Cloneable {
    /**
     * 执行动作
     */
    void action();
    /**
     * 创建时间
     *
     * @return
     */
    long getCreateTime();
    /**
     * 克隆对象
     *
     * @return
     * @throws CloneNotSupportedException
     */
    Object clone() throws CloneNotSupportedException;
}