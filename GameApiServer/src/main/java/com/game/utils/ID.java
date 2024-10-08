package com.game.utils;

/**
 * 唯一id生成器
 * 
 * @author zhangzhen
 * @date 2017年8月15日
 * @version 1.0
 */
public class ID {

	// 0标示
	private static short id = 0;
	// 系统启动时间
	private static long time = System.currentTimeMillis();
	// 锁
	private static final Object obj = new Object();

	// 本服随机数标示
	private static int id1 = 0;
	// 本服系统时间
	private static long time1 = System.currentTimeMillis();
	// 本服锁
	private static final Object obj1 = new Object();

	// 本服随机数标示
	private static short id2 = 0;
	// 本服系统时间
	private static long time2 = System.currentTimeMillis();
	// 本服锁
	private static final Object obj2 = new Object();

	// 跨服随机数标示（serverId可超过32768）
	private static short id3 = 0;
	// 跨服系统启动时间（serverId可超过32768）
	private static long time3 = System.currentTimeMillis();
	// 跨服锁（serverId可超过32768）
	private static final Object obj3 = new Object();

	// 跨服随机数标示
	private static byte id4 = 0;
	// 跨服系统启动时间
	private static long time4 = System.currentTimeMillis();
	// 跨服锁
	private static final Object obj4 = new Object();

	// 本服随机数标示
	private static int id5 = 0;
	// 本服系统时间
	private static long time5 = System.currentTimeMillis();

	/**
	 * 跨服唯一id
	 * 
	 * @param serverId 服务器id必须小于32768,初心这边改为可以大于上限值，专服之间不会出现合服，大于%10000
	 * @return
	 */
	public static long getId(int serverId) {
		synchronized (obj) {
			long now = System.currentTimeMillis();
			if (now > time) {
				time = now;
			}
			id += 1;
			if (id >= Short.MAX_VALUE) {
				id %= Short.MAX_VALUE;
				time += 1000L;
			}
			long ser = serverId;
			// 初心这边改为可以大于上限值，专服之间不会出现合服，大于%10000
			if (serverId >= 32768) {
				ser = serverId % 10000;
			}
			return ((ser & 0xFFFF) << 48) | ((time / 1000L & 0xFFFFFFFF) << 16) | (id & 0xFFFF);
		}
	}

	/**
	 * 跨服唯一id
	 * 
	 * @param serverId serverId可超过32768
	 * @return
	 */
	public static long getPublicId(int serverId) {
		synchronized (obj3) {
			long now = System.currentTimeMillis();
			if (now > time3) {
				time3 = now;
			}
			id3 += 1;
			if (id3 >= 10000) {
				id3 %= 10000;
				time3 += 1000L;
			}
			return (serverId * 100000000000000l) + ((time3 / 1000L) * 10000) + id3;
		}
	}

	/**
	 * 本服唯一id
	 * 
	 * @return
	 */
	public static long getId() {
		synchronized (obj1) {
			long now = System.currentTimeMillis();
			if (now / 1000L > time1 / 1000L) {
				time1 = now;
			}
			id1 += 1;
			if (id1 >= Integer.MAX_VALUE) {
				id1 %= Integer.MAX_VALUE;
				time1 += 1000L;
			}
			return ((time1 / 1000L & 0xFFFFFFFF) << 32) | (id1 & 0xFFFFFFFF);
		}
	}

	/**
	 * 本服唯一id（数字更短）
	 * 
	 * @return
	 */
	public static long getShortId() {
		synchronized (obj2) {
			long now = System.currentTimeMillis();
			if (now / 1000L > time2 / 1000L) {
				time2 = now;
			}
			id2 += 1;
			if (id2 >= Short.MAX_VALUE) {
				id2 %= Short.MAX_VALUE;
				time2 += 1000L;
			}
			return ((time2 / 1000L & 0xFFFFFFFF) << 16) | (id2 & 0xFFFF);
		}
	}

	/**
	 * 跨服唯一id（数字更短）服务器id小于1000最好
	 * 
	 * @return
	 */
	public static long getShortId(int serverId) {
		synchronized (obj4) {
			long now = System.currentTimeMillis();
			if (now / 1000L > time4 / 1000L) {
				time4 = now;
			}
			id4 += 1;
			if (id4 >= Byte.MAX_VALUE) {
				id4 %= Byte.MAX_VALUE;
				time4 += 1000L;
			}
			long ser = serverId;
			// 初心这边改为可以大于上限值，专服之间不会出现合服，大于%10000
			if (serverId >= 1000) {
				ser = serverId % 1000;
			}
			return ((ser & 0xFFFF) << 40) | ((time4 / 1000L & 0xFFFFFFFF) << 8) | (id4 & 0xFF);
		}
	}

	/**
	 * 本服唯一id
	 * 
	 * @return
	 */
	public static long getNoLockId() {
		long now = System.currentTimeMillis();
		if (now / 1000L > time5 / 1000L) {
			time5 = now;
		}
		id5 += 1;
		if (id5 >= Integer.MAX_VALUE) {
			id5 %= Integer.MAX_VALUE;
			time5 += 1000L;
		}
		return ((time5 / 1000L & 0xFFFFFFFF) << 32) | (id5 & 0xFFFFFFFF);
	}

//	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//		System.out.println(getShortId(300));
//		System.out.println(getShortId());
//		}
//	}

}
