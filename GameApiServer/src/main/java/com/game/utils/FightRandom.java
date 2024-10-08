package com.game.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FightRandom {

    private int seed;
    private int min = 0;
    private int max = 10000;
    private long roundInt = 1; // 循环次数（以前这里随机完列表，这里+1，但是现在改为随机一次不remove，只get，所以这里永远都是1）
    /**
     * 战斗随机数源数据列表
     */
    private List<Integer> fightRandomValueList = new ArrayList<>();

    {
        for (int i = 0; i < 10000; i++) {
            fightRandomValueList.add(i);
        }
    }

    /**
     * 战斗随机数参数列表
     */
    private HashMap<Long, Long> fightRandomParaList = new HashMap<>();

    {
        // 战斗随机数参数列表
        fightRandomParaList.put(1l, 981l);
        fightRandomParaList.put(2l, 42l);
        fightRandomParaList.put(3l, 566612l);
        fightRandomParaList.put(4l, 104l);
        fightRandomParaList.put(5l, 3040l);
        fightRandomParaList.put(6l, 340l);
        fightRandomParaList.put(7l, 56221l);
        fightRandomParaList.put(8l, 3405l);
        fightRandomParaList.put(9l, 4023l);
        fightRandomParaList.put(10l, 2467l);
        fightRandomParaList.put(11l, 5320l);
        fightRandomParaList.put(12l, 5l);
        fightRandomParaList.put(13l, 972l);
        fightRandomParaList.put(14l, 15l);
        fightRandomParaList.put(15l, 52l);
        fightRandomParaList.put(16l, 10976l);
        fightRandomParaList.put(17l, 97l);
        fightRandomParaList.put(18l, 3782921l);
        fightRandomParaList.put(19l, 342l);
        fightRandomParaList.put(20l, 78862l);
        fightRandomParaList.put(22l, 89l);
        fightRandomParaList.put(38l, 692l);
        fightRandomParaList.put(50l, 3l);
        fightRandomParaList.put(90l, 309l);
        fightRandomParaList.put(234l, 33l);
        fightRandomParaList.put(3245l, 348l);
    }


    public int getSeed() {
        return seed;
    }

    public FightRandom(int seed) {
        this.seed = seed;
    }

    /**
     * 获取随机数必须先执行此方法1(注意：调用此方法第一次传入0)
     *
     * @param getSum 获取次数
     * @return
     */
    public int updateRandomNum(int getSum) {
        // 修改变量
        if (getSum < (this.max - this.min) / 3) {
            getSum += (int) ((this.max - this.min) / 28.9f * 8f);
        } else {
            getSum += (int) ((this.max - this.min) / 64f);
        }
        return getSum;
    }

    /**
     * 获取随机数
     *
     * @param getSum 获取次数
     * @return
     */
    public int nextInt(int getSum) {
        // 获取下标
        int selectIndex = (int) (this.seed % 100 * (long) (this.max - this.min) * (long) (fightRandomValueList.size()) + this.fightRandomGetRoundArgs() * getSum + (long) (fightRandomValueList.size() / 8 * (int) ((this.max - this.min) / 2)));
        for (int i = 0; i < 4; i++) {
            if (i % 4 == 0) {
                selectIndex = selectIndex << 13;
            } else if (i % 4 == 1) {
                selectIndex = selectIndex >> 17;
            } else if (i % 4 == 2) {
                selectIndex = selectIndex >> 3;
            } else {
                selectIndex = selectIndex << 10;
            }
        }
        if (selectIndex < 0) {
            selectIndex = -selectIndex;
        }
        selectIndex = selectIndex % fightRandomValueList.size();
        return fightRandomValueList.get(selectIndex);
    }

    // 打破周期循环
    private long fightRandomGetRoundArgs() {
        long reInt;
        if (fightRandomParaList.containsKey(this.roundInt)) {
            reInt = fightRandomParaList.get(this.roundInt);
        } else {
            int remInt = (int) (this.roundInt % 30);
            if (remInt == 1 || remInt == 3) {
                reInt = (this.roundInt - 3034) * 25;
            } else if (remInt == 0) {
                reInt = 3034 * 23 * 7;
            } else if (remInt == 2 || remInt == 23) {
                reInt = ((this.roundInt % 24) - 343) * 10;
            } else if (remInt == 4 || remInt == 29) {
                reInt = ((this.roundInt % 85) * 3) / 9;
            } else if (remInt == 5 || remInt == 20 || remInt == 25) {
                reInt = ((this.roundInt % 33) * 3) % 90;
            } else if (remInt == 6) {
                reInt = this.seed % 37;
            } else if (remInt == 8 || remInt == 11 || remInt == 15) {
                reInt = this.seed % 31 + this.roundInt % 37 * 3;
            } else if (remInt == 10 || remInt == 18) {
                reInt = this.seed / 245 - this.roundInt % 33 + 345;
            } else if (remInt == 12 || remInt == 16 || remInt == 22) {
                reInt = this.seed / 8 % 21;
            } else if (remInt == 17 || remInt == 24) {
                reInt = this.seed % 1000 * ((this.roundInt / 234) % 63);
            } else if (remInt == 26 || remInt == 27) {
                reInt = (this.seed / this.roundInt) % 23 * 358;
            } else {
                reInt = this.roundInt;
            }
        }
        return reInt;
    }

}
