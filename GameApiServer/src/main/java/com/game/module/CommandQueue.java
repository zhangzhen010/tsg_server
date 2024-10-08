package com.game.module;

import com.game.command.ICommandQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列（线程安全）
 * 
 * @author zhangzhen
 * @time 2022年9月28日
 * @param <V>
 */
public class CommandQueue<V> implements ICommandQueue<V> {
	private Lock lock = new ReentrantLock();

	private final ArrayDeque<V> tasksQueue = new ArrayDeque<V>();

	public V poll() {
		try {
			this.lock.lock();
			return this.tasksQueue.poll();
		} finally {
			this.lock.unlock();
		}
	}

	public boolean add(V value) {
		try {
			this.lock.lock();
			return this.tasksQueue.add(value);
		} finally {
			this.lock.unlock();
		}
	}

	public boolean offerFirst(V value) {
		try {
			this.lock.lock();
			return this.tasksQueue.offerFirst(value);
		} finally {
			this.lock.unlock();
		}
	}

	public List<V> pollAll() {
		List<V> list = new ArrayList<V>();
		try {
			this.lock.lock();
			list.addAll(this.tasksQueue);
			this.tasksQueue.clear();
			return list;
		} finally {
			this.lock.unlock();
		}
	}
}