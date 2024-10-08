package com.game.utils;

import java.util.*;

/**
 * 大自然搬运工，可替换成 org.springframework.util.CollectionUtils和MapUtils
 * 
 * @author zhaoyong
 *
 */
public class CollectionUtils {

	private static final Integer INTEGER_ONE = Integer.valueOf(1);
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 交集
	 * @return 求两个list的交集，即共有的部分
	 */
	public static <T> Collection<T> intersection(Collection<T> a, Collection<T> b) {
		ArrayList<T> list = new ArrayList<>();
		Map<T, Integer> mapA = getCardinalityMap(a);
		Map<T, Integer> mapB = getCardinalityMap(b);
		Set<T> elements = new HashSet<>(a);
		elements.addAll(b);
		Iterator<T> it = elements.iterator();
		while(it.hasNext()) {
			T obj = it.next();
			int i = 0;
			for(int m = Math.min(getFreq(obj, mapA), getFreq(obj, mapB)); i < m; ++i) {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * 并集
	 * @return 取并集（去重）
	 */
	public static <T> Collection<T> union(Collection<T> a, Collection<T> b) {
		ArrayList<T> list = new ArrayList<>();
		Map<T, Integer> mapA = getCardinalityMap(a);
		Map<T, Integer> mapB = getCardinalityMap(b);
		Set<T> elements = new HashSet<>(a);
		elements.addAll(b);
		Iterator<T> it = elements.iterator();
		while(it.hasNext()) {
			T obj = it.next();
			int i = 0;
			for(int m = Math.max(getFreq(obj, mapA), getFreq(obj, mapB)); i < m; ++i) {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * 差集
	 * @return 取差集，给定俩集合A、B,由所有属于A且不属于B的元素组成的集合,叫做集合A减集合B(或集合A与集合B之差)
	 */
	public static <T> Collection<T> subtract(Collection<T> a, Collection<T> b) {
		ArrayList<T> list = new ArrayList<>(a);
		Iterator<T> it = b.iterator();
		while(it.hasNext()) {
			list.remove(it.next());
		}
		return list;
	}

	/**
	 * 交集的补集
	 * @return 取交集的补集。(补集一般指绝对补集，即一般地，设S是一个集合，A是S的一个子集，由S中所有不属于A的元素组成的集合，叫做子集A在S中的绝对补集)
	 */
	public static <T> Collection<T> disjunction(Collection<T> a, Collection<T> b) {
		ArrayList<T> list = new ArrayList<>();
		Map<T, Integer> mapA = getCardinalityMap(a);
		Map<T, Integer> mapB = getCardinalityMap(b);
		Set<T> elements = new HashSet<>(a);
		elements.addAll(b);
		Iterator<T> it = elements.iterator();
		while(it.hasNext()) {
			T obj = it.next();
			int i = 0;

			for(int m = Math.max(getFreq(obj, mapA), getFreq(obj, mapB)) - Math.min(getFreq(obj, mapA), getFreq(obj, mapB)); i < m; ++i) {
				list.add(obj);
			}
		}
		return list;
	}

	public static <T> Map<T, Integer> getCardinalityMap(Collection<T> coll) {
		Map<T, Integer> count = new HashMap<>();
		Iterator<T> it = coll.iterator();
		while(it.hasNext()) {
			T obj = it.next();
			Integer c = count.get(obj);
			if (c == null) {
				count.put(obj, INTEGER_ONE);
			} else {
				count.put(obj, c + 1);
			}
		}
		return count;
	}

	private static <T> int getFreq(T obj, Map<T, Integer> freqMap) {
		Integer count = freqMap.get(obj);
		return count != null ? count : 0;
	}
}
