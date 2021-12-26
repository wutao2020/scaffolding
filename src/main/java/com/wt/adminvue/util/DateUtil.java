package com.wt.adminvue.util;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static void main(String[] args) throws ParseException {
    //    System.out.println(CalculateTime("Wed Aug 26 16:14:45 CST 2020"));
       // CalculateTime("2020-08-01 19:00:09");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 指定时间格式
        for(int i=6;i>=0;i--){
            System.out.println(sdf.format(DateUtil.addYearOrMonthOrDay(null,0,0,0-i)));
        }
    }

    /**
     * 过去的某一时间,计算距离当前的时间
     * */

    public static String CalculateTime(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        Date setTime = null; // 指定时间
        try {
            setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
        } catch (ParseException e) {

            e.printStackTrace();
        }

        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;

        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {

            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数

            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";

            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";

            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";

            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";

            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";

            } else if (dateTemp1 > 0) {
                msg = "刚刚";

            }
        }
        return msg;

    }

    /**
     * 过去的某一时间,计算距离当前的时间（和上面一种显示不同）
     * */

    public static String CalculateTime1(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        Date setTime = null; // 指定时间
        try {
            setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;

        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {

            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数

            if (dateTemp6 > 0) {
                msg =sdf1.format(setTime);

            } else if (dateTemp5 > 0) {
                msg = sdf1.format(setTime);

            } else if (dateTemp4 > 0) {
                if(dateTemp4 == 1){
                    msg = "昨天";
                }else{
                    msg = sdf1.format(setTime);
                }
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";

            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";

            } else if (dateTemp1 > 0) {
                msg = "刚刚";

            }
        }
        return msg;

    }


    /**
     * 过去的某一时间,计算距离当前的时间
     * */

    public static String CalculateTime(Date setTime) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;

        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {

            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数

            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";

            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";

            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";

            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";

            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";

            } else if (dateTemp1 > 0) {
                msg = "刚刚";

            }
        }
        return msg;

    }


    @SneakyThrows
    public static String zoneToLocalTimeStr(String dateString) {

      //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString = dateString.replace("Z", " UTC");
        Date date = sdf.parse(dateString);
        //System.out.println(df.format(date));
        return df.format(date);
    }

    public  static Date  addYearOrMonthOrDay(Date startTime , int yearNum, int monthNum, int dayNum){
        Date endTime = startTime;
        if (startTime == null){
             endTime =new Date();//获取当前日期
        }
        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);   //设置当前时间
        cal.add(Calendar.YEAR, yearNum);  //在当前时间基础上加年
        cal.add(Calendar.MONTH, monthNum);  //在当前时间基础上加月
        cal.add(Calendar.DATE, dayNum);  //在当前时间基础上加天
        //将时间格式化成yyyy-MM-dd HH:mm:ss的格式
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(format.format(cal.getTime()));
        return cal.getTime();
    }


}
