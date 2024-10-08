package com.game.module;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * 队列（非线程安全）
 * 
 * @author zhangzhen
 * @time 2020年6月29日
 * @param <V>
 */
public class SingleCommandQueue<V> {

	private final LinkedList<V> list = new LinkedList<V>();

	private final HashSet<V> set = new HashSet<V>();

	public void add(V value) {
		if (!this.set.contains(value)) {
			this.set.add(value);
			this.list.add(value);
		}
	}

	public V poll() {
		V value = this.list.poll();
		if (value != null) {
			this.set.remove(value);
		}
		return value;
	}

	public boolean contains(V value) {
		return this.set.contains(value);
	}

	public void remove(V value) {
		this.set.remove(value);
		this.list.remove(value);
	}

	public int size() {
		return set.size();
	}
}