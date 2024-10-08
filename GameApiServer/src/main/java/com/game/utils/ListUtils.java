package com.game.utils;

import com.alibaba.fastjson2.JSON;
import com.game.bean.structs.IntId;
import com.game.bean.structs.LongId;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * list操作工具
 * <p>
 * 当某些存储数据比较小，而且有遍历的情况下，不使用map,使用list来代替操作
 *
 * @author zhangzhen
 * @time 2022年4月15日
 */
@Log4j2
public class ListUtils {

    /**
     * 添加或更新列表中的键值对
     *
     * @param <T>   泛型，支持Integer和Long类型
     * @param list  列表，元素为(T, T)对
     * @param key   键
     * @param value 值
     */
    public static <T extends Number & Comparable<T>> void addList(List<T> list, T key, T value) {
        try {
            boolean foundKey = false;
            for (int i = 0, len = list.size(); i < len; i += 2) {
                T gid = list.get(i);
                if (gid.equals(key)) {
                    T v = list.get(i + 1);
                    // 根据T的实际类型执行加法操作
                    if (gid instanceof Integer && v instanceof Integer && value instanceof Integer) {
                        list.set(i + 1, (T) Integer.valueOf(v.intValue() + value.intValue()));
                    } else if (gid instanceof Long && v instanceof Long && value instanceof Long) {
                        list.set(i + 1, (T) Long.valueOf(v.longValue() + value.longValue()));
                    } else {
                        throw new IllegalArgumentException("不支持的Number类型组合");
                    }
                    foundKey = true;
                    break;
                }
            }
            if (!foundKey) {
                list.add(key);
                list.add(value);
            }
        } catch (Exception e) {
            log.error("添加或更新列表值失败", e);
        }
    }

    /**
     * list批量添加值
     *
     * @param list1
     * @param list2
     * @return
     */
    public static <T extends Number & Comparable<T>> List<T> addList(List<T> list1, List<T> list2) {
        try {
            for (int i = 0, len = list2.size(); i < len; i += 2) {
                T goodsId = list2.get(i);
                T value = list2.get(i + 1);
                addList(list1, goodsId, value);
            }
            return list1;
        } catch (Exception e) {
            log.error("list批量添加值", e);
            return list1;
        }
    }

    /**
     * 合并列表中相同的key
     *
     * @param list
     * @return
     */
    public static <T extends Number & Comparable<T>> List<T> mergeList(List<T> list) {
        try {
            List<T> newList = new ArrayList<>();
            addList(newList, list);
            return newList;
        } catch (Exception e) {
            log.error("合并奖励", e);
            return list;
        }
    }

    /**
     * list获取值
     *
     * @param key
     * @return
     */
    public static <T> T get(List<T> list, T key) {
        try {
            for (int i = 0, len = list.size(); i < len; i += 2) {
                T gid = list.get(i);
                if (gid.equals(key)) {
                    return list.get(i + 1);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("list获取值", e);
            return null;
        }
    }

    /**
     * 根据给定的ID从列表中查找并返回具有相同ID的元素。
     * 注意：这个方法假设列表元素实现了getId()方法，并且返回值类型与传入的id类型一致。
     *
     * @param list 元素列表
     * @param id   要查找的ID
     * @param <T>  泛型类型，必须实现 getId() 方法并且返回与 id 相同类型的值
     * @return 找到的元素，如果没有找到则返回 null
     */
    public static <T extends IntId> T getByIntId(List<T> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            T v = list.get(i);
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    /**
     * 根据给定的ID从列表中查找并返回具有相同ID的元素。
     * 注意：这个方法假设列表元素实现了getId()方法，并且返回值类型与传入的id类型一致。
     *
     * @param list 元素列表
     * @param id   要查找的ID
     * @param <T>  泛型类型，必须实现 getId() 方法并且返回与 id 相同类型的值
     * @return 找到的元素，如果没有找到则返回 null
     */
    public static <T extends LongId> T getByLongId(List<T> list, long id) {
        for (int i = 0; i < list.size(); i++) {
            T v = list.get(i);
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    /**
     * list赋值
     *
     * @param key
     * @return
     */
    public static <T> void set(List<T> list, T key, T value) {
        try {
            boolean newAward = true;
            for (int i = 0, len = list.size(); i < len; i += 2) {
                T gid = list.get(i);
                if (gid.equals(key)) {
                    list.set(i + 1, value);
                    newAward = false;
                    break;
                }
            }
            if (newAward) {
                list.add(key);
                list.add(value);
            }
        } catch (Exception e) {
            log.error("list赋值", e);
        }
    }

    /**
     * list移除值
     *
     * @param list
     * @param key
     * @return
     */
    public static <T> T remove(List<T> list, T key) {
        try {
            for (int i = 0, len = list.size(); i < len; i += 2) {
                T gid = list.get(i);
                if (gid.equals(key)) {
                    list.remove(i);
                    T value = list.remove(i);
                    i -= 2;
                    len = list.size();
                    return value;
                }
            }
            return null;
        } catch (Exception e) {
            log.error("list移除值", e);
            return null;
        }
    }

    /**
     * list查询key是否存在
     *
     * @param key
     * @return
     */
    public static <T> boolean containsKey(List<T> list, T key) {
        try {
            for (int i = 0, len = list.size(); i < len; i += 2) {
                T gid = list.get(i);
                if (gid.equals(key)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("list查询key是否存在", e);
            return false;
        }
    }

    /**
     * 偶数位翻指定倍数(对新列表进行操作，不修改原列表)
     *
     * @param list
     * @param multiNum
     */
    public static List<Integer> multiList(List<Integer> list, int multiNum) {
        if (multiNum <= 1) {
            return list;
        }
        try {
            List<Integer> newList = new ArrayList<>(list);
            int size = newList.size();
            if (size % 2 != 0) {
                log.error("对一个list执行翻倍操作，异常：" + JSON.toJSONString(newList));
                return newList;
            }
            int idx = 0;
            for (int i = 0; i < size; i += 2) {
                idx = i + 1;
                newList.set(idx, newList.get(idx) * multiNum);
            }
            return newList;
        } catch (Exception e) {
            log.error("对一个list执行翻倍操作", e);
            return list;
        }
    }

    /**
     * 断list是否null或者是否为空列表
     *
     * @param list
     * @return
     */
    public static boolean isEmptyOrNull(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 随机获取列表中的一个元素
     *
     * @param list
     * @return
     */
    public static <T> T getRandom(List<T> list) {
        if (isEmptyOrNull(list)) {
            return null;
        }
        return list.get(RandomUtils.random(list.size()));
    }

    /**
     * 随机获取列表中的指定数量元素，不重复获取，如果长度不满足，返回原列表
     *
     * @param list
     * @return
     */
    public static <T> List<T> getRandom(List<T> list, int num) {
        try {
            if (list.size() < num) {
                return list;
            }
            List<T> newList = new ArrayList<>(list);
            Collections.shuffle(newList);
            return newList.subList(0, num);
        } catch (Exception e) {
            log.error("随机获取列表中的指定数量元素，不重复获取，如果长度不满足，返回原列表异常：", e);
            return list;
        }
    }

}
