package com.game.cache.structs;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义LinkedHashMap 支持超出最大上限移除Map
 * 
 * @author zhangzhen
 * @time 2022年9月27日
 * @param <K>
 * @param <V>
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = -2712935159964577772L;
	private int max = 16;
	private static final int START_NUMBER = 16;
	private static final float DEFAULT_LOAD_FACTOR = 0.75F;
	private Lock lock = new ReentrantLock();

	public LRULinkedHashMap(int max) {
		super(START_NUMBER, DEFAULT_LOAD_FACTOR, true);
		this.max = max;
	}

	public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > this.max;
	}

	public List<V> getEldestEntry(int size) {
		try {
			this.lock.lock();
			List<V> result = new ArrayList<V>();
			Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
			while (iterator.hasNext()) {
				if (result.size() >= size) {
					break;
				}
				Map.Entry<K, V> entry = iterator.next();
				result.add(entry.getValue());
			}

			return result;
		} finally {
			this.lock.unlock();
		}
	}

	public V get(Object k) {
		try {
			this.lock.lock();
			return super.get(k);
		} finally {
			this.lock.unlock();
		}
	}

	public V put(K key, V value) {
		try {
			this.lock.lock();
			return super.put(key, value);
		} finally {
			this.lock.unlock();
		}
	}

	public V remove(Object key) {
		try {
			this.lock.lock();
			return super.remove(key);
		} finally {
			this.lock.unlock();
		}
	}

}