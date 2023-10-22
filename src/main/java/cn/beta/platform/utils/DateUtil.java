package cn.beta.platform.utils;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 时间工具包
 * @version: 1.0
 */
public class DateUtil {

    public static final String defaultDateTimeFormatter = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final String TIME_FORMAT_LONG = "yyyyMMddHHmmss";

    public static final String DATA_FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 判断当前时间，是否在该时间范围内
     *
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 是否在
     */
    public static boolean isBetween(Date beginTime, Date endTime) {
        Assert.notNull(beginTime, "开始时间不能为空");
        Assert.notNull(endTime, "结束时间不能为空");
        Date now = new Date();
        return beginTime.getTime() <= now.getTime()
                && now.getTime() <= endTime.getTime();
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param field  日历字段.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAY等.
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(int field, int amount) {
        return addDate(null, field, amount);
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param date   设置时间
     * @param field  日历字段.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAY等.
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(Date date, int field, int amount) {
        if (amount == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取当天开始时间
     * @version: 1.0
     */
    public static String getDayBeginTime(String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }

        return (LocalDateTime.of(LocalDate.now(), LocalTime.MIN)).format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }
    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取当天开始时间
     * @version: 1.0
     */
    public static Date getDayBeginTime(Date source, String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return convertDate((LocalDateTime.of(LocalDate.now(), LocalTime.MIN)).format(DateTimeFormatter.ofPattern(dateTimeFormatter)),dateTimeFormatter);
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取当天结束时间
     * @version: 1.0
     */
    public static Date getDayEndTime(Date source, String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return convertDate( (LocalDateTime.of(LocalDate.now(), LocalTime.MAX)).format(DateTimeFormatter.ofPattern(dateTimeFormatter)),dateTimeFormatter);
    }
    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取当天结束时间
     * @version: 1.0
     */
    public static String getDayEndTime(String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return (LocalDateTime.of(LocalDate.now(), LocalTime.MAX)).format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }
    /**
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取当天结束时间
     * @version: 1.0
     */
    public static LocalDateTime getDayEndTime() {
        return (LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取本月开始时间
     * @version: 1.0
     */
    public static String getMonthStartTime(String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return (LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 1, 0, 0)).format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取本月结束时间
     * @version: 1.0
     */
    public static String getMonthEndTime(String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return (LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())).format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取当天时间
     * @version: 1.0
     */
    public static String getDay(String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 线程安全转换时间
     * @version: 1.0
     */
    public static String dateFormat(Date source, String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 线程安全转换时间
     * @version: 1.0
     */
    public static Date convertDate(String source, String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return Date.from(LocalDateTime.parse(source, DateTimeFormatter.ofPattern(dateTimeFormatter)).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param dateTimeFormatter
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取未来几天时间。默认往后一天
     * @version: 1.0
     */
    public static String getFutureTime(int pulsNo, String dateTimeFormatter) {
        if (dateTimeFormatter == null || dateTimeFormatter.equals("")) {
            dateTimeFormatter = DateUtil.defaultDateTimeFormatter;
        }
        return LocalDateTime.now().plusDays(pulsNo < 0 ? 1 : pulsNo).format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }
    /**
     * @author: by Mood
     * @date: 2011-01-14 11:11:11
     * @Description: 获取未来几天时间。默认往后一天
     * @version: 1.0
     */
    public static LocalDateTime getFutureTime(int pulsNo) {
        return LocalDateTime.now().plusDays(pulsNo <= 0 ? 1 : pulsNo);
    }

    public static boolean compareValidity(Date validity) {
        LocalDate compareDate = LocalDate.now().plusDays(-1);
        Date data = Date.from(compareDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return validity.getTime() > data.getTime();
    }

    /**
     * @param dateStr
     * @Description: 线程安全转换时间
     * @version: 1.0
     */
    public static Date parseLocalDate(String dateStr) {
        LocalDate nowLocalDate = LocalDate.parse(dateStr, DATE_FORMATTER);
        return Date.from(nowLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @Description: 获取当前时间
     * @version: 1.0
     */
    public static LocalDateTime getCurrentTime() {

        return LocalDateTime.now();
    }
    /**
     * @Description: 获取俩个时间之间的差距
     *
     * @version: 1.0
     */
    public static Long getTimeDistance(LocalDateTime startDate,LocalDateTime endDate) {

        return ChronoUnit.MINUTES.between(startDate, endDate);
    }
    /**
     * @param minutes
     * @Description: 获取当前时间后N分钟的时间
     * @version: 1.0
     */
    public static LocalDateTime obtainAfterMinutes(Long minutes ) {
        return LocalDateTime.now().plusMinutes(minutes);
    }
    /**
     * @param minutes
     * @Description: 获取某个时间后N分钟的时间
     * @version: 1.0
     */
    public static LocalDateTime obtainAfterMinutes(LocalDateTime dateTime,Long minutes ) {
        return dateTime.plusMinutes(minutes);
    }

    /**
     * @param dateStr
     * @Description: 线程安全转换时间
     * @version: 1.0
     */
    public static LocalDateTime parseLocalDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(defaultDateTimeFormatter));
    }
}
