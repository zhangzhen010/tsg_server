package com.game.cache.impl;

import lombok.extern.log4j.Log4j2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
public class CodeLinkedHashMap extends LinkedHashMap<String, Integer> {

    private int max = 16;
    private static final int START_NUMBER = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private final Lock lock = new ReentrantLock();

    public CodeLinkedHashMap(int max) {
        super(START_NUMBER, DEFAULT_LOAD_FACTOR, true);
        this.max = max;
    }

    /**
     * LinkedHashMap有一个removeEldestEntry(Map.Entry eldest)方法
     * 通过覆盖这个方法，加入一定的条件，满足条件返回true。当put进新的值方法返回true时，便移除该map中最老的键和值。
     */
    public boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        try {
            this.lock.lock();
            return size() > this.max;
        } finally {
            this.lock.unlock();
        }
    }

    public Integer get(Object k) {
        try {
            this.lock.lock();
            return super.get(k);
        } finally {
            this.lock.unlock();
        }
    }

    public Integer put(String key, Integer value) {
        try {
            this.lock.lock();
            return super.put(key, value);
        } finally {
            this.lock.unlock();
        }
    }

    public Integer remove(Object key) {
        try {
            this.lock.lock();
            return super.remove(key);
        } finally {
            this.lock.unlock();
        }
    }
}