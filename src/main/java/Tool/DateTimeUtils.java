package Tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类
 */
public class DateTimeUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateTimeUtils() {
    }

    /**
     * 格式化日期为字符串
     *
     * @param date   日期对象
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化日期为字符串
     *
     * @param date   日期对象
     * @param format 日期格式
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 解析字符串为日期对象
     *
     * @param dateString 日期字符串
     * @return 解析后的日期对象
     * @throws ParseException 如果解析失败，则抛出ParseException异常
     */
    public static Date parseDate(String dateString) throws ParseException {
        return parseDate(dateString, DEFAULT_DATE_FORMAT);
    }

    /**
     * 解析字符串为日期对象
     *
     * @param dateString 日期字符串
     * @param format     日期格式
     * @return 解析后的日期对象
     * @throws ParseException 如果解析失败，则抛出ParseException异常
     */
    public static Date parseDate(String dateString, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }



    /**
     * 解析字符串为日期对象
     *
     * @param dateString 日期字符串
     * @param format     日期格式
     * @param timezone     时区
     * @return 解析后的日期对象
     * @throws ParseException 如果解析失败，则抛出ParseException异常
     */
    public static Date parseDate(String dateString, String format,Locale timezone) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, timezone);
        Date date = dateFormat.parse(dateString);
        return dateFormat.parse(dateString);
    }

    /**
     * 获取当前时间的时间戳（毫秒级别）
     *
     * @return 当前时间的时间戳
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 将时间戳转换为日期对象
     *
     * @param timestamp 时间戳
     * @return 转换后的日期对象
     */
    public static Date timestampToDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 将日期对象转换为时间戳
     *
     * @param date 日期对象
     * @return 转换后的时间戳
     */
    public static long dateToTimestamp(Date date) {
        return date.getTime();
    }

    /**
     * 将 Date 对象格式化为字符串
     *
     * @param date LocalDateTime 对象
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateText(Date date) {
        return formatDateText(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将 Date 对象格式化为字符串
     *
     * @param date LocalDateTime 对象
     * @param format   日期时间格式
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateText(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static void main(String[] args) throws ParseException {
        Date now = new Date();
        System.out.println("当前时间：" + DateTimeUtils.formatDate(now));

        String dateString = "2023-05-14 10:30:00";
        Date parsedDate = DateTimeUtils.parseDate(dateString);
        System.out.println("解析后的时间：" + parsedDate);

        long timestamp = DateTimeUtils.getCurrentTimestamp();
        System.out.println("当前时间戳：" + timestamp);

        Date dateFromTimestamp = DateTimeUtils.timestampToDate(timestamp);
        System.out.println("时间戳转换后的时间：" + dateFromTimestamp);

        long timestampFromDate = DateTimeUtils.dateToTimestamp(dateFromTimestamp);
        System.out.println("时间转换后的时间戳：" + timestampFromDate);

        String dateString_dd_MMM_yyyy = "03-Feb-2023 13:23:21";
        Date parsedDate_dd_MMM_yyyy = DateTimeUtils.parseDate(dateString_dd_MMM_yyyy, "dd-MMM-yyyy HH:mm:ss", Locale.US);
        String str_dd_MMM_yyyy = DateTimeUtils.formatDateText(parsedDate_dd_MMM_yyyy);
        System.out.println("解析03-Feb-2023后的时间为字符串：" + str_dd_MMM_yyyy);
    }


}
