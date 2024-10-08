package com.game.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间处理工具类
 *
 * @author zhangzhen
 * @time 2020年5月14日
 */
public class TimeUtil {
    /**
     * 1秒的毫秒数
     */
    public static final long MILLIS = 1000L;
    /**
     * 2秒的毫秒数
     */
    public static final long TWO_MILLIS = 2 * 1000L;
    /**
     * 3秒的毫秒数
     */
    public static final long THREE_MILLIS = 3 * 1000L;
    /**
     * 5秒的毫秒数
     */
    public static final long FIVE_MILLIS = 5 * 1000L;
    /**
     * 10秒的毫秒数
     */
    public static final long TEN_MILLIS = 10 * 1000L;
    /**
     * 15秒的毫秒数
     */
    public static final long FIFTEEN_MILLIS = 15 * 1000L;
    /**
     * 三十秒的毫秒数
     */
    public static final long THIRTY_MILLIS = 30 * 1000L;
    /**
     * 三十秒的毫秒数
     */
    public static final long THIRTY_SEC = 30L;
    /**
     * 一分钟的毫秒数
     */
    public static final long MIN_MILLIS = 60 * 1000L;
    /**
     * 一分钟的秒数
     */
    public static final long MIN = 60L;
    /**
     * 两分钟的毫秒数
     */
    public static final long TWO_MIN_MILLIS = 2 * 60 * 1000L;
    /**
     * 三分钟的毫秒数
     */
    public static final long THREE_MIN_MILLIS = 3 * 60 * 1000L;
    /**
     * 五分钟的毫秒数
     */
    public static final long FIVE_MIN_MILLIS = 5 * 60 * 1000L;
    /**
     * 十分钟的毫秒数
     */
    public static final long TEN_MIN_MILLIS = 10 * 60 * 1000L;
    /**
     * 十分钟的秒数
     */
    public static final long TEN_MIN = 10 * 60L;
    /**
     * 三十分钟的毫秒数
     */
    public static final long THIRTY_MIN_MILLIS = 30 * 60 * 1000L;
    /**
     * 一小时的毫秒数
     */
    public static final long HOUR_MILLIS = 60 * 60 * 1000L;
    /**
     * 两小时的毫秒数
     */
    public static final long TWO_HOUR_MILLIS = 2 * 60 * 60 * 1000L;
    /**
     * 6小时的毫秒数
     */
    public static final long SIX_HOUR_MILLIS = 6 * 60 * 60 * 1000L;
    /**
     * 6小时的秒数
     */
    public static final long SIX_HOUR_SECOND = 6 * 60 * 60L;
    /**
     * 12个小时的毫秒数
     */
    public static final long TWELVE_MILLIS = 60 * 60 * 12 * 1000L;
    /**
     * 一天的毫秒数
     */
    public static final long DAY_MILLIS = 60 * 60 * 24 * 1000L;
    /**
     * 一天的秒数
     */
    public static final long DAY_SEC = 60 * 60 * 24;
    /**
     * 两天的毫秒数
     */
    public static final long TWO_DAY_MILLIS = 2 * 60 * 60 * 24 * 1000L;
    /**
     * 三天的毫秒数
     */
    public static final long THREE_DAY_MILLIS = 3 * 60 * 60 * 24 * 1000L;
    /**
     * 一周的毫秒数
     */
    public static final long WEEK_MILLIS = 3600L * 24L * 7L * 1000L;
    /**
     * 一周的秒数
     */
    public static final int WEEK_SEC = 3600 * 24 * 7;
    /**
     * 两周的毫秒数
     */
    public static final long DOUBLE_WEEK_MILLIS = 3600L * 24L * 14L * 1000L;
    /**
     * 两周的秒数
     */
    public static final int DOUBLE_WEEK_SEC = 3600 * 24 * 14;
    /**
     * 十五天的毫秒数
     */
    public static final long FIFTEEN_DAY_MILLIS = 15 * 60 * 60 * 24 * 1000L;
    /**
     * 1个月的毫秒数
     */
    public static final long MONTH_MILLIS = 3600L * 24L * 30L * 1000L;
    /**
     * 2个月的毫秒数
     */
    public static final long TWO_MONTH_MILLIS = 3600L * 24L * 60L * 1000L;
    /**
     * 3个月的毫秒数
     */
    public static final long QUARTER_MILLIS = 3600L * 24L * 90L * 1000L;
    /**
     * 6个月的毫秒数
     */
    public static final long TWO_QUARTER_MILLIS = 3600L * 24L * 180L * 1000L;
    /**
     * 1年的毫秒数
     */
    public static final long YEAR_MILLIS = 3600L * 24L * 365L * 1000L;
    /**
     * 2年的毫秒数
     */
    public static final long YEAR_TWO_MILLIS = 2L * 3600L * 24L * 365L * 1000L;

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

    private static final Calendar calendar0 = Calendar.getInstance();

    private static final Calendar calendar1 = Calendar.getInstance();

    private static final Calendar calendar2 = Calendar.getInstance();

    private static final Calendar calendar3 = Calendar.getInstance();

    private static final Calendar calendar4 = Calendar.getInstance();

    private static final Calendar calendar5 = Calendar.getInstance();

    private static final Calendar calendar6 = Calendar.getInstance();

    private static final Calendar calendar7 = Calendar.getInstance();

    private static final Calendar calendar8 = Calendar.getInstance();

    private static final Calendar calendar9 = Calendar.getInstance();

    private static final Calendar calendar10 = Calendar.getInstance();

    private static final Calendar calendar11 = Calendar.getInstance();

    private static final Calendar calendar12 = Calendar.getInstance();

    private static final Calendar calendar13 = Calendar.getInstance();

    private static final Calendar calendar14 = Calendar.getInstance();

    private static final Calendar calendar15 = Calendar.getInstance();

    private static final Calendar calendar16 = Calendar.getInstance();

    private static final Calendar calendar17 = Calendar.getInstance();

    private static final Calendar calendar18 = Calendar.getInstance();

    private static final Calendar calendar19 = Calendar.getInstance();

    private static final Calendar calendar20 = Calendar.getInstance();

    /**
     * 格式化时间
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formateDate(long time, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * @param time
     * @return
     */
    public static String formatDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return format.format(new Date(time));
    }

    /**
     * 获取本地时区时间与UTC时区世间偏移量（相差秒数）
     *
     * @return
     */
    public static int getZoneOffset() {
        synchronized (calendar0) {
            int sec = calendar0.get(Calendar.ZONE_OFFSET);
            return sec / 1000;
        }
    }

    /**
     * 获取该时间的 在那一天
     *
     * @param currentTime
     * @return
     */
    public static int getDay(long currentTime) {
        synchronized (calendar1) {
            calendar1.setTimeInMillis(currentTime);
            return calendar1.get(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * 获取该时间在那一月
     *
     * @param currentTime
     * @return
     */
    public static int getMonth(long currentTime) {
        return getJavaMonth(currentTime) + 1;
    }

    /**
     * 获取该时间在那一月
     *
     * @param currentTime
     * @return
     */
    public static int getJavaMonth(long currentTime) {
        synchronized (calendar2) {
            calendar2.setTimeInMillis(currentTime);
            return calendar2.get(Calendar.MONTH);
        }
    }

    /**
     * 获取该时间的 在那一年
     *
     * @param currentTime
     * @return
     */
    public static int getYear(long currentTime) {
        synchronized (calendar20) {
            calendar20.setTimeInMillis(currentTime);
            return calendar20.get(Calendar.YEAR);
        }
    }

    /**
     * 获取指定时间的字符串年月日
     *
     * @param currentTime
     * @return
     */
    public static String getYyyyMmDd(long currentTime) {
        // 获取当前天数
        int year = getYear(currentTime);
        int month = getMonth(currentTime);
        int day = getDay(currentTime);
        return year + "_" + month + "_" + day;
    }

    /**
     * 获取当前星期几，不是默认常量，而是平时周一=1，周二=2，周日=7
     *
     * @param currentTime
     * @return
     */
    public static int getDayByWeek(long currentTime) {
        synchronized (calendar3) {
            calendar3.setTimeInMillis(currentTime);
            int dayOfWeek = calendar3.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                dayOfWeek = 7;
            } else {
                dayOfWeek--;
            }
            return dayOfWeek;
        }
    }

    /**
     * 获取当前的小时
     *
     * @param currentTime
     * @return
     */
    public static int getHours(long currentTime) {
        synchronized (calendar4) {
            calendar4.setTimeInMillis(currentTime);
            return calendar4.get(Calendar.HOUR_OF_DAY);
        }
    }

    /**
     * 获取这个时间是一年中的第几天
     *
     * @param currentTime
     * @return
     */
    public static int getDayNum(long currentTime) {
        synchronized (calendar5) {
            calendar5.setTimeInMillis(currentTime);
            int month = calendar5.get(Calendar.MONTH) + 1;
            int year = calendar5.get(Calendar.YEAR);
            int day = calendar5.get(Calendar.DAY_OF_MONTH);
            int sum = 0;
            for (int i = 1; i < month; i++) {
                switch (i) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        sum += 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        sum += 30;
                        break;
                    case 2:
                        if (((year % 4 == 0) && (year != 0)) || (year % 400 == 0)) sum += 29;
                        else sum += 28;
                }
            }
            return sum = sum + day;
        }
    }

    /**
     * 判断两个时间是否是同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        if (time1 - time2 > DAY_MILLIS || time2 - time1 > DAY_MILLIS) {
            return false;
        } else {
            synchronized (calendar6) {
                calendar6.setTimeInMillis(time1);
                int day1 = calendar6.get(Calendar.DAY_OF_YEAR);
                calendar6.setTimeInMillis(time2);
                int day2 = calendar6.get(Calendar.DAY_OF_YEAR);
                if (day1 != day2) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否为同一个周 判断date和当前日期是否在同一周内注:Calendar类提供了一个获取日期在所属年份中是第几周的方法，
     * 对于上一年末的某一天和新年初的某一天在同一周内也一样可以处理，
     * 例如2012-12-31和2013-01-01虽然在不同的年份中，但是使用此方法依然判断二者属于同一周内
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameWeek(long time1, long time2) {
        // 0.先把Date类型的对象转换Calendar类型的对象
        synchronized (calendar7) {
            // 设置一周的第一天为星期一
            if (calendar7.getFirstDayOfWeek() != Calendar.MONDAY) {
                // 给出一星期的第一天是星期一，不然算作星期天会造成每周日判断不是同一周
                calendar7.setFirstDayOfWeek(Calendar.MONDAY);
            }
            calendar7.setTimeInMillis(time1);
            int week1 = calendar7.get(Calendar.WEEK_OF_YEAR);
            calendar7.setTimeInMillis(time2);
            int week2 = calendar7.get(Calendar.WEEK_OF_YEAR);
            return week1 == week2;
        }
    }

    /**
     * 获得两个时间间隔自然周数(最大十周，因为目前逻辑用不上，以后有需要可以修改)
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getWeekNum(long time1, long time2) {
        long startTime = Math.min(time1, time2);
        long endTime = Math.max(time1, time2);
        long beginDayOfWeek = getBeginDayOfWeek(startTime, 0);
        // 最大获取10周
        for (int i = 0; i <= 10; i++) {
            if (isSameWeek(beginDayOfWeek + (i * TimeUtil.WEEK_MILLIS), endTime)) {
                return i;
            }
        }
        return 10;
    }

    /**
     * 判断是否为同一个月
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameMonth(long time1, long time2) {
        return getMonth(time1) == getMonth(time2);
    }

    /**
     * 返回2个时间相差的天数，相同返回0 过凌晨算第二天返回1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getDifferDays(long time1, long time2) {
        int days = 0;
        try {
            // 将转换的两个时间对象转换成Calendard对象
            synchronized (calendar8) {
                calendar8.setTimeInMillis(time1);
                calendar9.setTimeInMillis(time2);
                // 拿出两个年份
                int year1 = calendar8.get(Calendar.YEAR);
                int year2 = calendar9.get(Calendar.YEAR);
                // 天数
                Calendar can = null;
                // 如果can1 < can2
                // 减去小的时间在这一年已经过了的天数
                // 加上大的时间已过的天数
                if (calendar8.before(calendar9)) {
                    days -= calendar8.get(Calendar.DAY_OF_YEAR);
                    days += calendar9.get(Calendar.DAY_OF_YEAR);
                    can = calendar8;
                } else {
                    days -= calendar9.get(Calendar.DAY_OF_YEAR);
                    days += calendar8.get(Calendar.DAY_OF_YEAR);
                    can = calendar9;
                }
                for (int i = 0; i < Math.abs(year2 - year1); i++) {
                    // 获取小的时间当前年的总天数
                    days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
                    // 再计算下一年。
                    can.add(Calendar.YEAR, 1);
                }
                return days;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获得指定天数指定小时的毫秒数
     *
     * @param offsetTime
     * @return
     */
    public static long getTimeInMillisByHour(long offsetTime, int hour) {
        synchronized (calendar10) {
            calendar10.setTimeInMillis(offsetTime);
            calendar10.set(Calendar.HOUR_OF_DAY, 0);
            calendar10.set(Calendar.MINUTE, 0);
            calendar10.set(Calendar.SECOND, 0);
            calendar10.set(Calendar.MILLISECOND, 0);
            return calendar10.getTimeInMillis() + (hour * TimeUtil.HOUR_MILLIS);
        }
    }

    /**
     * 获得指定天数指定小时和分钟的毫秒数
     *
     * @param offsetTime
     * @return
     */
    public static long getTimeInMillisByHourAndMin(long offsetTime, int hour, int min) {
        synchronized (calendar11) {
            calendar11.setTimeInMillis(offsetTime);
            calendar11.set(Calendar.HOUR_OF_DAY, 0);
            calendar11.set(Calendar.MINUTE, 0);
            calendar11.set(Calendar.SECOND, 0);
            calendar11.set(Calendar.MILLISECOND, 0);
            return calendar11.getTimeInMillis() + (hour * TimeUtil.HOUR_MILLIS) + (min * TimeUtil.MIN_MILLIS);
        }
    }

    /**
     * 获得指定天数指定小时和分钟的毫秒数
     *
     * @param offsetTime
     * @return
     */
    public static long getTimeInMillisByDay(long offsetTime, int day, int hour, int min) {
        synchronized (calendar12) {
            calendar12.setTimeInMillis(offsetTime);
            calendar12.set(Calendar.DAY_OF_MONTH, day);
            calendar12.set(Calendar.HOUR_OF_DAY, hour);
            calendar12.set(Calendar.MINUTE, min);
            calendar12.set(Calendar.SECOND, 0);
            calendar12.set(Calendar.MILLISECOND, 0);
            return calendar12.getTimeInMillis();
        }
    }

    /**
     * 获得指定月份指定天数指定小时和分钟的毫秒数
     *
     * @param offsetTime
     * @return
     */
    public static long getTimeInMillisByMonth(long offsetTime, int month, int day, int hour, int min) {
        synchronized (calendar13) {
            calendar13.setTimeInMillis(offsetTime);
            calendar13.set(Calendar.MONTH, month);
            calendar13.set(Calendar.DAY_OF_MONTH, day);
            calendar13.set(Calendar.HOUR_OF_DAY, hour);
            calendar13.set(Calendar.MINUTE, min);
            calendar13.set(Calendar.SECOND, 0);
            calendar13.set(Calendar.MILLISECOND, 0);
            return calendar13.getTimeInMillis();
        }
    }

    /**
     * 获得指定 年月日时分 的毫秒数
     *
     * @param times
     * @return
     */
    public static long getTimeInMillisByTimes(String[] times) {
        synchronized (calendar14) {
            int year = Integer.parseInt(times[0]);
            int month = Integer.parseInt(times[1]) - 1;
            int day = Integer.parseInt(times[2]);
            int hours = Integer.parseInt(times[3]);
            int minute = Integer.parseInt(times[4]);
            calendar14.set(year, month, day, hours, minute, 0);
            calendar14.set(Calendar.MILLISECOND, 0);
            return calendar14.getTimeInMillis();
        }
    }

    /**
     * 根据字符串解析时间戳（年,月,日,时,分,秒）
     *
     * @param timeArray 时间数组
     * @return 时间戳
     */
    public static long getTimeInMillisByTime(List<Integer> timeArray) {
        synchronized (calendar17) {
            int year = timeArray.get(1);
            int month = timeArray.get(2) - 1;
            int day = timeArray.get(3);
            int hours = timeArray.get(4);
            int minute = timeArray.get(5);
            int second = timeArray.get(6);
            calendar17.set(year, month, day, hours, minute, second);
            calendar17.set(Calendar.MILLISECOND, 0);
            return calendar17.getTimeInMillis();
        }
    }

    /**
     * 指定时间还未到
     *
     * @param hour
     * @param min
     * @return
     */
    public static boolean isTodayTimeBeforeByTime(int hour, int min) {
        try {
            long currentTime = System.currentTimeMillis();
            long time = getTimeInMillisByHourAndMin(currentTime, hour, min);
            if (currentTime < time) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 指定时间已到
     *
     * @param hour
     * @param min
     * @return
     */
    public static boolean isTodayTimeLaterByTime(int hour, int min) {
        try {
            long currentTime = System.currentTimeMillis();
            long time = getTimeInMillisByHourAndMin(currentTime, hour, min);
            if (currentTime > time) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 指定时间还未到
     *
     * @param hour
     * @param min
     * @return
     */
    public static boolean isTodayTimeBeforeByTime(long currentTime, int hour, int min) {
        try {
            long time = getTimeInMillisByHourAndMin(currentTime, hour, min);
            if (currentTime < time) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 指定时间已到
     *
     * @param hour
     * @param min
     * @return
     */
    public static boolean isTodayTimeLaterByTime(long currentTime, int hour, int min) {
        try {
            long time = getTimeInMillisByHourAndMin(currentTime, hour, min);
            if (currentTime > time) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析时分秒时间为秒数
     *
     * @param formatTime
     * @return
     */
    public static long parseFormatTime(String formatTime) {
        return parseFormatTime(formatTime, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 解析时分秒时间为秒数
     *
     * @param formatTime
     * @return
     */
    public static long parseFormatTime(String formatTime, String pattern) {
        if (formatTime == null || formatTime.isEmpty()) return 0;
        long time = 0;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            time = (format.parse(formatTime).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取指定时间与当天凌晨秒数只差
     *
     * @return
     */
    public static long getSecondOfDay(long nowTime) {
        synchronized (calendar15) {
            calendar15.setTimeInMillis(nowTime);
            calendar15.set(Calendar.HOUR_OF_DAY, 0);
            calendar15.set(Calendar.MINUTE, 0);
            calendar15.set(Calendar.SECOND, 0);
            calendar15.set(Calendar.MILLISECOND, 0);
            long tempTime = calendar15.getTimeInMillis();
            return nowTime - tempTime;
        }
    }

    /**
     * 根据2个时间获取分钟之差
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getMinuteByTwoTime(long time1, long time2) {
        long time = Math.abs(time1 - time2);
        return (int) (time / TimeUtil.MIN_MILLIS);
    }

    /**
     * 根据时间获取当月最大天数
     *
     * @param time
     * @return
     */
    public static int getMaxDayOfMonth(long time) {
        synchronized (calendar16) {
            calendar16.setTimeInMillis(time);
            return calendar16.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * 根据时间配置数组，获取时间（用于活动时间，道具合成时间等等配置）
     *
     * @param timeArray
     * @param serverStartTime 服务器首次开服时间
     * @return
     */
    public static long getActivityTimeByServerOpenDayArray(List<Integer> timeArray, long serverStartTime) {
        long time = 0L;// 秒
        int day = timeArray.get(0);
        int hours = timeArray.get(1);
        int minute = timeArray.get(2);
        int second = timeArray.get(3);
        time += serverStartTime;
        time += day * TimeUtil.DAY_MILLIS;
        time += hours * TimeUtil.HOUR_MILLIS;
        time += minute * TimeUtil.MIN_MILLIS;
        time += second * TimeUtil.MILLIS;
        return time;
    }

    /**
     * 获取本周开始时间
     *
     * @param currentTime
     * @param hour        指定本周开始小时
     * @return
     */
    public static long getBeginDayOfWeek(long currentTime, int hour) {
        try {
            synchronized (calendar18) {
                calendar18.setTimeInMillis(currentTime);
                int dayofweek = calendar18.get(Calendar.DAY_OF_WEEK);
                if (dayofweek == 1) {
                    dayofweek += 7;
                }
                calendar18.add(Calendar.DAY_OF_MONTH, 2 - dayofweek);
                calendar18.set(calendar18.get(Calendar.YEAR), calendar18.get(Calendar.MONTH), calendar18.get(Calendar.DAY_OF_MONTH), hour, 0, 0);
                calendar18.set(Calendar.MILLISECOND, 0);
                return calendar18.getTimeInMillis();
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取本周结束时间
     *
     * @param currentTime
     * @param hour        指定本周开始小时
     * @return
     */
    public static long getEndDayOfWeek(long currentTime, int hour) {
        try {
            return getBeginDayOfWeek(currentTime, hour) + TimeUtil.WEEK_MILLIS;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取本月结束时间
     *
     * @param currentTime
     * @param hour
     * @return
     */
    public static long getEndDayOfMonth(long currentTime, int hour) {
        try {
            int nextMonth = getJavaMonth(currentTime);
            int maxDay = getMaxDayOfMonth(currentTime);
            return getTimeInMillisByMonth(currentTime, nextMonth, maxDay, 0, 0) + TimeUtil.DAY_MILLIS;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取指定时间的0点时间
     */
    public static long getZeroTime(long time) {
        try {
            calendar19.setTimeInMillis(time);
            calendar19.set(Calendar.HOUR_OF_DAY, 0);
            calendar19.set(Calendar.MINUTE, 0);
            calendar19.set(Calendar.SECOND, 0);
            calendar19.set(Calendar.MILLISECOND, 0);
            return calendar19.getTimeInMillis();
        } catch (Exception e) {
            return 0;
        }
    }

}
