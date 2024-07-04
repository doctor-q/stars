package cc.doctor.stars.biz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenzhi03
 */
public class DateUtils {
    public static final long DAY_MILLS = 24 * 60 * 60 * 1000;
    public static final long HOUR_MILLS = 60 * 60 * 1000;
    public static final long MINUTE_MILLS = 60 * 1000;

    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<SimpleDateFormat> dfThreadLocal = ThreadLocal.withInitial(SimpleDateFormat::new);

    public static Date truncateTo(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar truncate = truncateTo(calendar, field);
        return truncate.getTime();
    }

    public static Calendar truncateWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar c = truncateTo(calendar, Calendar.HOUR_OF_DAY);
        c.set(Calendar.DAY_OF_WEEK, 1);
        return c;
    }

    public static Calendar truncateYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar c = truncateTo(calendar, Calendar.HOUR_OF_DAY);
        c.set(Calendar.DAY_OF_YEAR, 1);
        return c;
    }

    /**
     * 规整化时间
     *
     * @param calendar 时间，形如2021-04-21 16:12:30
     * @param field    规整时间字段
     *                 秒（2021-04-21 16:12:00）、
     *                 分（2021-04-21 16:00:00）、
     *                 时（2021-04-21 00:00:00）、
     *                 日（2021-04-01 00:00:00）、
     *                 月（2021-01-01 00:00:00）
     */
    public static Calendar truncateTo(Calendar calendar, int field) {
        // 13
        if (field <= Calendar.SECOND) {
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        // 12
        if (field <= Calendar.MINUTE) {
            calendar.set(Calendar.MINUTE, 0);
        }
        // 11
        if (field <= Calendar.HOUR_OF_DAY) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        // 5
        if (field <= Calendar.DATE) {
            calendar.set(Calendar.DATE, 1);
        }
        // 2
        if (field <= Calendar.MONTH) {
            calendar.set(Calendar.MONTH, 1);
        }
        return calendar;
    }

    public static String toyyyyMMddHHmmss(Long timetsamp) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(PATTERN_YYYY_MM_DD_HH_MM_SS);
        return df.format(new Date(timetsamp));
    }

    public static Date fromyyyyMMddHHmmss(String dateStr) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(PATTERN_YYYY_MM_DD_HH_MM_SS);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date addDate(Date date, int days) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hours) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    public static String toyyyyMMdd(Long timestamp) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(PATTERN_YYYY_MM_DD);
        return df.format(new Date(timestamp));
    }

    public static String toyyyyMMdd(Date date) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(PATTERN_YYYY_MM_DD);
        return df.format(date);
    }

    public static String toyMd(Date date) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern("yyyyMMdd");
        return df.format(date);
    }

    public static String toHHmmss(Date date) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern("HH:mm:ss");
        return df.format(date);
    }

    public static String toyyyyMMddHHmmss(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(PATTERN_YYYY_MM_DD_HH_MM_SS);
        return df.format(date);
    }

    public static String toyMdHms(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern("yyyyMMddHHmmss");
        return df.format(date);
    }

    public static String toyyyyMM(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern("yyyy-MM");
        return df.format(date);
    }

    private static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Integer days(Date start, Date end) {
        if (start == null || end == null) {
            return null;
        }
        Calendar from = truncateTo(toCalendar(start), Calendar.HOUR_OF_DAY);
        Calendar to = truncateTo(toCalendar(end), Calendar.HOUR_OF_DAY);
        int i = 0;
        while (from.before(to)) {
            from.add(Calendar.DATE, 1);
            i++;
        }
        return i;
    }

    public static Double hours(Date end, Date start) {
        if (start == null || end == null) {
            return null;
        }
        return Math.abs((double) (end.getTime() - start.getTime())) / HOUR_MILLS;
    }

    public static Date parse(String date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(pattern);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat df = dfThreadLocal.get();
        df.applyPattern(pattern);
        return df.format(date);
    }

    public static List<Date> everyDays(Date start, Date end) {
        if (start == null || end == null) {
            return Collections.emptyList();
        }
        if (start.after(end)) {
            return Collections.emptyList();
        }
        Calendar from = truncateTo(toCalendar(start), Calendar.HOUR_OF_DAY);
        Calendar to = truncateTo(toCalendar(end), Calendar.HOUR_OF_DAY);
        List<Date> everyDays = new ArrayList<>();
        while (!from.after(to)) {
            everyDays.add(new Date(from.getTime().getTime()));
            from.add(Calendar.DATE, 1);
        }
        return everyDays;
    }

    public static Integer currentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Date tryParse(String date, List<String> patterns) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = dfThreadLocal.get();
        for (String pattern : patterns) {
            df.setTimeZone(TimeZone.getDefault());
            df.applyPattern(pattern);
            try {
                return df.parse(date);
            } catch (ParseException e) {
                // ignore
            }
        }
        return null;
    }

    public static Date day(Integer year, Integer month, Integer day) {
        if (year == null) {
            return null;
        }
        if (month == null) {
            month = 1;
        }
        if (day == null) {
            day = 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Calendar truncate = truncateTo(calendar, Calendar.HOUR_OF_DAY);
        return truncate.getTime();
    }

    public static Date yesterdayZero() {
        return truncateTo(addDate(new Date(), -1), Calendar.HOUR_OF_DAY);
    }

    public static Date todayZero() {
        return truncateTo(new Date(), Calendar.HOUR_OF_DAY);
    }
}
