package com.game.cache.impl;

import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.WebPlayer;
import com.game.utils.SpringBootUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerLinkedHashMap extends LinkedHashMap<Long, WebPlayer> {

    Logger log = LogManager.getLogger(PlayerLinkedHashMap.class);

    private static final long serialVersionUID = -3791412708654730531L;
    private int max = 16;
    private static final int START_NUMBER = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private final Lock lock = new ReentrantLock();

    public PlayerLinkedHashMap(int max) {
        super(START_NUMBER, DEFAULT_LOAD_FACTOR, true);
        this.max = max;
    }

    /**
     * LinkedHashMap有一个removeEldestEntry(Map.Entry eldest)方法
     * 通过覆盖这个方法，加入一定的条件，满足条件返回true。当put进新的值方法返回true时，便移除该map中最老的键和值。
     */
    public boolean removeEldestEntry(Map.Entry<Long, WebPlayer> eldest) {
        try {
            this.lock.lock();
            return size() > this.max;
        } finally {
            this.lock.unlock();
        }
    }

    public WebPlayer get(Object k) {
        try {
            this.lock.lock();
            return super.get(k);
        } finally {
            this.lock.unlock();
        }
    }

    public WebPlayer put(Long key, WebPlayer value) {
        try {
            this.lock.lock();
            return super.put(key, value);
        } finally {
            this.lock.unlock();
        }
    }

    public WebPlayer remove(Object key) {
        try {
            PlayerOtherManager playerOtherManager = SpringBootUtils.getBean(PlayerOtherManager.class);
            this.lock.lock();
            playerOtherManager.removePlayerOther((Long) key, true);
            return super.remove(key);
        } finally {
            this.lock.unlock();
        }
    }
}