package com.game.command;

import java.util.List;

/**
 * 指令队列
 *
 * @param <V>
 * @author zhangzhen
 * @version 1.0
 * @date 2017年8月15日
 */
public interface ICommandQueue<V> {
    V poll();

    boolean add(V paramV);

    boolean offerFirst(V paramV);

    List<V> pollAll();
}