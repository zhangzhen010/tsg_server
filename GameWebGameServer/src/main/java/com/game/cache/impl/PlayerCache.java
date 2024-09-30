package com.game.cache.impl;

import com.game.cache.Cache;
import com.game.player.structs.WebPlayer;

/**
 * 角色本地缓存
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/5 20:40
 */
public class PlayerCache implements Cache<Long, WebPlayer> {

	/**
	 * 最大存储5000个
	 */
	public static int MAX_SIZE = 5000;
	/**
	 * 本地缓存不删除，现在用来保存角色缓存
	 */
	private final PlayerLinkedHashMap cache;

	public PlayerCache() {
		this(MAX_SIZE);
	}

	public PlayerCache(int maxSize) {
		this.cache = new PlayerLinkedHashMap(maxSize);
	}

	public void put(Long key, WebPlayer value) {
		this.cache.put(key, value);
	}

	public WebPlayer get(Long key) {
        return this.cache.get(key);
	}

	public WebPlayer remove(Long key) {
		return this.cache.remove(key);
	}

	public PlayerLinkedHashMap getCache() {
		return cache;
	}

	/**
	 * 获取本地缓存的所有玩家
	 * 
	 * @return 缓存数量
	 */
	public int getCacheSize() {
		return cache.size();
	}
}