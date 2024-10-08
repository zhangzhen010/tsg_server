package com.game.utils;

import com.game.data.define.MyDefineConstant;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机数生成工具
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/16 14:52
 */
@Log4j2
public class RandomUtils {

    private static final int[] randomArray = new int[]{15, 83, 73, 87, 32, 51, 86, 16, 57, 4, 5, 3, 14, 85, 59, 28, 42, 96, 78, 54, 37, 67, 50, 52, 23, 90, 66, 44, 25, 99, 71, 47, 89, 48, 61, 2, 13, 27, 92, 29, 1, 34, 94, 39, 62, 20, 60, 72, 11, 93, 31, 68, 69, 10, 70, 18, 91, 7, 12, 80, 41, 9, 35, 64, 6, 84, 76, 30, 79, 19, 45, 55, 46, 65, 98, 40, 26, 82, 43, 33, 49, 81, 58, 21, 17, 24, 22, 36, 38, 97, 88, 77, 74, 63, 95, 75, 56, 53, 8, 100};

    /**
     * 不包含最大值
     *
     * @param max
     * @return
     */
    public static int random(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    /**
     * 包含最大最小值
     *
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        if (min > max) {
            min = min ^ max;
            max = min ^ max;
            min = min ^ max;
        }
        return min + ThreadLocalRandom.current().nextInt(max - min + 1);
    }

    /**
     * 根据几率是否生成 规则:大于等于最小 小于等于最大 的一个万分比概率
     *
     * @param max 最大的值
     * @param min 最小的值
     * @return
     */
    public static boolean isGenerate(int max, int min) {
        int ranNum = ThreadLocalRandom.current().nextInt(MyDefineConstant.MAX_PROBABILITY);
        if (ranNum >= min && ranNum <= max) {
            return true;
        }
        return false;
    }

    /**
     * 根据几率是否生成 万分比
     *
     * @param probablility
     * @return
     */
    public static boolean isGenerate(int probablility) {
        return probablility >= ThreadLocalRandom.current().nextInt(MyDefineConstant.MAX_PROBABILITY);
    }

    /**
     * 根据几率是否生成 万分比(支持参数为long)
     *
     * @param probablility
     * @return
     */
    public static boolean isGenerate(long probablility) {
        return probablility >= ThreadLocalRandom.current().nextInt(MyDefineConstant.MAX_PROBABILITY);
    }

    /**
     * 根据几率是否生成 百分比
     *
     * @param probability
     * @return
     */
    public static boolean isGenerateBai(int probability) {
        return probability >= ThreadLocalRandom.current().nextInt(MyDefineConstant.MAX_PROBABILITY_BAI);
    }

    /**
     * 按帧取随机数(包含最大值, 包含最小值0)
     *
     * @param mapFrame     地图帧数
     * @param addtionFrame 附加帧数
     * @param maxCount     最大数量
     * @return
     */
    public static int getRandomResult(int mapFrame, int addtionFrame, int maxCount) {
        return getRandomResult(mapFrame, addtionFrame, 0, maxCount);
    }

    /**
     * 按帧取随机数(包含最大值, 包含最小值)
     *
     * @param mapFrame     地图帧数
     * @param addtionFrame 附加帧数
     * @param minCount     最小数量
     * @param maxCount     最大数量
     * @return
     */
    public static int getRandomResult(int mapFrame, int addtionFrame, int minCount, int maxCount) {
        int frame = (mapFrame + addtionFrame) % randomArray.length;
        // 获取随机值
        int random = (int) Math.ceil((double) randomArray[frame] / 100 * (maxCount - minCount + 1));

        return minCount + random - 1;
    }

    public static int[] getRandomArray() {
        return randomArray;
    }

    public static <T> void shuffle(List<T> list) {
        int size = list.size();
        int value;
        T temp;
        for (int index = 0; index < size; index++) {
            value = index + random(0, 32767) % (size - index);
            temp = list.get(index);
            list.set(index, list.get(value));
            list.set(value, temp);
        }
    }

    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = ThreadLocalRandom.current().nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 权重随机
     */
    public static int randomWeight(List<Integer> weightList) {
        try {
            int total = 0;
            for (int i = 0, len = weightList.size(); i < len; i++) {
                total += weightList.get(i);
            }
            int num = random(total);
            int min = 0;
            for (int i = 0; i < weightList.size(); i++) {
                min += weightList.get(i);
                if (num < min) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            log.error("权重随机", e);
            return -1;
        }
    }

    /**
     * 随机物品指定数量 随机取的T不重复
     *
     * @param list  物品容器
     * @param count 需要随机的数量
     * @param <T>   具体的物品类
     */
    public static <T> List<T> randomItem(List<T> list, int count) {
        List<T> tempList = new ArrayList<>(list);
        List<T> resList = new ArrayList<>();
        for (int i = 0; i < count && !tempList.isEmpty(); i++) {
            int index = random(tempList.size());
            resList.add(tempList.remove(index));
        }
        return resList;
    }

    /**
     * 根据权重随机指定不重复个数，不修改原权重列表
     * @param sourceList 源列表
     * @param weightList 权重列表，一一对应源列表
     * @param count      个数
     * @return           随机到的不重复源
     */
    public static List<Integer> randomWeight(List<Integer> sourceList, List<Integer> weightList, int count) {
        if (count > weightList.size()) {
            return new ArrayList<>();
        }
        ArrayList<Integer> tmpWeightList = new ArrayList<>(weightList);
        ArrayList<Integer> tmpSourceList = new ArrayList<>(sourceList);
        List<Integer> retList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = randomWeight(tmpWeightList);
            if (index == -1) {
                return new ArrayList<>();
            }
            tmpWeightList.remove(index);
            retList.add(tmpSourceList.remove(index));
        }
        return retList;
    }
}
