package com.rjzd.baby.tools;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * create time: 2018/5/29  10:22
 * create author: Hition
 * descriptions: 常用工具类
 */
public class ZDUtils {

    /**
     * 时间格式化
     */
    public static String formattedTime(long second) {
        String hs, ms, ss, formatTime;

        long h, m, s;
        h = second / 3600;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }

        if(h > 0){
            formatTime = hs + ":" + ms + ":" + ss;
        }else{
            formatTime = ms + ":" + ss;
        }
        return formatTime;
    }

    /**
     * 获取当前年份
     * @return       年
     */
    public static int getCurrentYear() {
        Calendar date = Calendar.getInstance();
        return date.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份，因为月份是从0开始，所以要加1
     * @return     月
     */
    public static int getCurrentMonth() {
        Calendar date = Calendar.getInstance();
        return date.get(Calendar.MONTH)+1;
    }

    /***
     *  获取指定日后 后 dayAddNum 天的 日期
     *  @param day  日期，格式为String："2013-9-3";
     *  @param dayAddNum 增加天数 格式为int;
     *  @return
     */
    public static String getNextDateStr(String day, int dayAddNum) {
        Calendar calendar = Calendar.getInstance ();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime (df.parse (day));
            calendar.add(Calendar.DAY_OF_MONTH, dayAddNum);
            Date date = calendar.getTime ();
            return df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算最大分页数
     * @param total                 总数
     * @param pageSize              分页每页大小
     * @return 最大页码
     */
    public static int calculateMaxPage(int total,int pageSize){
        int q = total/pageSize;
        int r = total%pageSize;
        if(r == 0){
            return q;
        }else{
            return q+1;
        }
    }

    /**
     * 格式化单位,字节转换，byte转KB，MB，GB，TB
     *
     * @param size
     */
    public static String getFormatSize(long size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "TB";
    }

    /**
     * 将当前时间毫秒值转换为固定格式
     * @param format       "yyyy-MM-dd"   or  "yyyy-MM-dd HH:mm:ss"
     * @return 格式化后时间字符串
     */
    public static String formatCurrent(String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    private static Date getFormatDate(String format,String dt){
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(dt);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提取格式化后的日期字符串中年、月、日等信息
     *
     * @param format            格式yyyy-MM-dd
     * @param date              日期 2018-01-09
     * @param field             年月日等字段名   Calendar.YEAR   Calendar.MONTH   DAY_OF_MONTH
     * @return 年或月或日 信息
     *
     * 注：Calendar.MONTH 是从0开始计算，所以最终得到的返回需要 +1
     */
    public static int getDateField(String format,String date,String field){
        Date dt = getFormatDate(format,date);
        if(null!=dt){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            if(!TextUtils.isEmpty(field) ){
                if(field.equals("year")){
                    return calendar.get(Calendar.YEAR);
                }else if(field.equals("month")){
                    return calendar.get(Calendar.MONTH) + 1;
                }else if(field.equals("day")){
                    return calendar.get(Calendar.DAY_OF_MONTH);
                }
            }
        }
        return 0;
    }


}
