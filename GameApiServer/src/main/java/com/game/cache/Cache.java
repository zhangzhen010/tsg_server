package com.game.cache;

/**
 * 缓存
 *
 * @param <K>
 * @param <V>
 * @author zhangzhen
 * @version 1.0
 * @date 2017年8月15日
 */
public interface Cache<K, V> {
    /**
     * 获取
     *
     * @param paramK
     * @return
     */
    V get(K paramK);

    /**
     * 添加
     *
     * @param paramK
     * @param paramV
     */
    void put(K paramK, V paramV);

    /**
     * 移除
     *
     * @param paramK
     */
    V remove(K paramK);

}