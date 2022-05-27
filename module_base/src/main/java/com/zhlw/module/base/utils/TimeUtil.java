package com.zhlw.module.base.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cn.hutool.core.date.DateUtil;


public class TimeUtil {

    private static final String TAG = "TimeUtil";

    private TimeUtil() { }


    public static final String TIME_FORMAT = "%02d:%02d:%02d";
    // 一秒毫秒数
    public static final long MS = 1000;
    // 一分钟毫秒数
    public static final long MIN_MS = 60000;
    //一小时的毫秒数
    public static final long HOUR_MS = MIN_MS * 60;
    //一天的毫秒数
    public static final long DAY_MS = HOUR_MS * 24;
    //一个月的毫秒数
    public static final long MONTH_MS = DAY_MS * 30;
    // 一小时秒数
    public static final int HSECOND = 3600;
    // 一分钟多少秒
    public static final int MIN_S = 60;

    //h am/pm 中的小时数（1-12）
    //H 一天中的小时数（0-23）
    //k 一天中的小时数（1-24）
    //K am/pm 中的小时数（0-11）

    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_2 = "yyyy-MM-dd";
    public static final String FORMAT_3 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_4 = "HH:mm:ss";
    public static final String FORMAT_5 = "HH:mm";
    public static final String FORMAT_6 = "mm:ss";
    public static final String FORMAT_7 = "MM.dd";
    public static final String FORMAT_8 = "MM";
    public static final String FORMAT_9 = "dd";
    public static final String FORMAT_10 = "yyyy-MM-dd kk:mm:ss";
    public static final String FORMAT_11 = "HH";
    public static final String FORMAT_12 = "mm";
    public static final String FORMAT_13 = "MM-dd HH:mm";

    public static long getCurrentTimeLong() {
        return System.currentTimeMillis();
    }

    public static String getFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(time));
    }

    public static String getFormatTime(String time, String formatStr) {
        return getFormatTime(Long.parseLong(time), formatStr);
    }


    /**
     * 将时日期转换为时间戳
     */
    public static long formatStringToLong(String dateTime, String formatStr) {
        long mTimestamp = -1;
        if (dateTime != null && TextUtils.isEmpty(dateTime)) {
            return -1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.US);

        Date mDate;
        try {
            mDate = sdf.parse(dateTime);
            if (mDate != null) {
                mTimestamp = mDate.getTime();
            }
        } catch (Exception e) {
            Log.e(TAG,"string date to long date error is " + e);
        }

        return mTimestamp;
    }


    /**
     * 判断当前日期是星期几
     *
     * @param time 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */
    public static String getWeekStr(long time) {
        int weekInt = getWeekInt(time);
        String weekStr = "周";
        if (weekInt == Calendar.SUNDAY) {
            weekStr += "日";
        }
        if (weekInt == Calendar.MONDAY) {
            weekStr += "一";
        }
        if (weekInt == Calendar.TUESDAY) {
            weekStr += "二";
        }
        if (weekInt == Calendar.WEDNESDAY) {
            weekStr += "三";
        }
        if (weekInt == Calendar.THURSDAY) {
            weekStr += "四";
        }
        if (weekInt == Calendar.FRIDAY) {
            weekStr += "五";
        }
        if (weekInt == Calendar.SATURDAY) {
            weekStr += "六";
        }
        return weekStr;
    }


    public static String getWeekItem(int item) {
        String weekStr = "周";
        switch (item) {
            case 0:
                weekStr += "一";
                break;
            case 1:
                weekStr += "二";
                break;
            case 2:
                weekStr += "三";
                break;
            case 3:
                weekStr += "四";
                break;
            case 4:
                weekStr += "五";
                break;
            case 5:
                weekStr += "六";
                break;
            default:
                weekStr += "日";
                break;
        }

        return weekStr;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param time 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */

    public static int getWeekInt(long time) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param time 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */

    public static int getWeekIntNormal(long time) {
        int weekInt = getWeekInt(time);
        switch (weekInt) {
            case 1://周日
                return 7;
            case 2://周一
                return 1;
            case 3://周二
                return 2;
            case 4://周三
                return 3;
            case 5://周四
                return 4;
            case 6://周五
                return 5;
            default://周六
                return 6;
        }
    }

    /**
     * 将时间戳转换成 xx:xx
     */
    public static String stampToDate(long stamp) {
        String time = "";
        try {
            long hours = (stamp % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //小时
            long minutes = (stamp % (1000 * 60 * 60)) / (1000 * 60); //分钟
            long seconds = (stamp % (1000 * 60)) / 1000; //秒

            //时分秒，为0则无
            if (hours == 0) {
                if (minutes == 0) {
                    time = seconds + "秒";
                } else {
                    time = minutes + "分钟" + seconds + "秒";
                }
            } else if (minutes == 0) {
                if (seconds == 0) {
                    time = hours + "小时";
                } else {
                    time = hours + "小时" + seconds + "秒";
                }
            } else {
                if (seconds == 0) {
                    time = hours + "小时" + minutes + "分钟";
                } else {
                    time = hours + "小时" + minutes + "分钟" + seconds + "秒";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String stampToMinute(long stamp) {
        if (stamp <= 0) {
            return "0分钟";
        }
        String time = "";
        try {
            long hours = (stamp % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //小时
            long minutes = (stamp % (1000 * 60 * 60)) / (1000 * 60); //分钟

            //时分秒，为0则无
            if (hours == 0) {
                time = minutes + "分钟";
            } else if (minutes == 0) {
                time = hours + "小时";
            } else {
                time = hours + "小时" + minutes + "分钟";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static List<String> stampToHourMinute(long stamp) {
        List<String> result = new ArrayList<>();
        if (stamp <= 0) {
            result.add("00");
            result.add("00");
            return result;
        }
        long hours = (stamp % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (stamp % (1000 * 60 * 60)) / (1000 * 60);
        // 分钟需要进一
        long remainder = (stamp % (1000 * 60 * 60)) % (1000 * 60);
        if (remainder > 0) {
            minutes++;
            if (minutes == 60) {
                hours++;
                minutes = 0;
            }
        }
        if (hours < 10) {
            result.add("0" + hours);
        } else {
            result.add("" + hours);
        }
        if (minutes < 10) {
            result.add("0" + minutes);
        } else {
            result.add("" + minutes);
        }
        return result;
    }

    /**
     * 将时间段转化为今天的时间点
     */
    public static long getTheSameTimeToday(long time, long currentTime) {
        if (time <= 0) {
            return -1;
        }

        long hours = (time % 86400000) / 3600000;//(time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //小时
        long minutes = (time % 3600000) / 60000;//(time % (1000 * 60 * 60)) / (1000 * 60); //分钟

        String hourStr = String.valueOf(hours);
        String minuteStr = String.valueOf(minutes);
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        if (minuteStr.length() == 1) {
            minuteStr = "0" + minuteStr;
        }
        String currentDate = getFormatTime(currentTime, FORMAT_2);
        String timeStr = currentDate + " " + hourStr + ":" + minuteStr + ":00";

        return formatStringToLong(timeStr, FORMAT_1);
    }

    public static String millToHourMinute(long time) {
        StringBuffer timeString = new StringBuffer();
        long hour = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minute = (time % (1000 * 60 * 60)) / (1000 * 60);
        if (hour > 0) {
            timeString.append(hour);
            timeString.append("小时");
        }
        if (hour == 0 || minute > 0) {
            timeString.append(minute);
            timeString.append("分钟");
        }
        return timeString.toString();
    }

    public static String formatMillisecond(long millisecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_6, Locale.CHINA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        // time为转换格式后的字符串
        String time = dateFormat.format(new Date(millisecond));
        return time;
    }

    /**
     * 时间戳转 HH-DD-SS
     * @param timeinMiles 时间戳
     * @return
     */
    public static String formatTimeHDS(long timeinMiles){
        Date date = DateUtil.date(timeinMiles);
        String format = DateUtil.format(date, FORMAT_4);
        return format;
    }

    /**
     * 时间戳转 yyyy-MM-dd
     * @param timeinMiles 时间戳
     * @return
     */
    public static String formatTime(long timeinMiles){
        Date date = DateUtil.date(timeinMiles);
        String format = DateUtil.format(date, "yyyy-MM-dd");
        return format;
    }

    /**
     * 时间戳转 MM-dd HH:ss
     * @param timeinMiles 时间戳
     * @return
     */
    public static String formatTimeMDHS(long timeinMiles){
        Date date = DateUtil.date(timeinMiles);
        String format = DateUtil.format(date, FORMAT_13);
        return format;
    }

    /**
     * 时间戳转 yyyy-MM
     * @param timeinMiles 时间戳
     * @return
     */
    public static String formatTimeUntilMonth(long timeinMiles){
        Date date = DateUtil.date(timeinMiles);
        String format = DateUtil.format(date, "yyyy-MM");
        return format;
    }

    /**
     * 返回今日0点的时间戳
     * 入参 yyyy-mm-dd
     * @return  今日 yyyy-mm-dd 00:00:00时的时间戳
     */
    public static Long getTodayTimeStartTimeLong(String todayTime){
        String tempTime = todayTime.concat(" 00:00:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(tempTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!= null){
            long ts = date.getTime();
            return ts;
        } else {
            return -1L;
        }
    }

    /**
     * 返回今日23点59分的时间戳
     * 入参 yyyy-mm-dd
     * @return  今日 yyyy-mm-dd 23:59:59的时间戳
     */
    public static Long getTodayTimeEndTimeLong(String todayTime){
        String tempTime = todayTime.concat(" 23:59:59");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(tempTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!= null){
            long ts = date.getTime();
            return ts;
        } else {
            return -1L;
        }
    }


    /**
     * 毫秒化秒
     * @param timeInMiles 毫秒时间
     * @return 秒 Int 类型
     */
    public static Integer formatTimeToSecondInt(long timeInMiles){
        try{
            return Math.toIntExact(timeInMiles / 1000);
        } catch (Throwable throwable){
            throwable.printStackTrace();
            return 0;
        }
    }

    /**
     * 秒化分钟
     * @param timeInSceond 秒时间
     * @return  分钟 Int 类型
     */
    public static Integer formatTimeToMinInt(int timeInSceond){
        try{
            return Math.toIntExact(timeInSceond / 60);
        } catch (Throwable throwable){
            throwable.printStackTrace();
            return 0;
        }
    }

}
