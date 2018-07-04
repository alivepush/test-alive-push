package com.testalivepush;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <h3>»’∆⁄π§æﬂ¿‡</h3>
 * <p>÷˜“™ µœ÷¡À»’∆⁄µƒ≥£”√≤Ÿ◊˜
 */
@SuppressLint("SimpleDateFormat")
public final class DateUtil {

    /**
     * yyyy-MM-dd HH:mm:ss◊÷∑˚¥Æ
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd◊÷∑˚¥Æ
     */
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    /**
     * HH:mm:ss◊÷∑˚¥Æ
     */
    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss∏Ò Ω
     */
    public static final ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        }

    };

    /**
     * yyyy-MM-dd∏Ò Ω
     */
    public static final ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        }

    };

    /**
     * HH:mm:ss∏Ò Ω
     */
    public static final ThreadLocal<SimpleDateFormat> defaultTimeFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_FORMAT_TIME);
        }

    };

    private DateUtil() {
        throw new RuntimeException("£˛ 3£˛");
    }

    /**
     * Ω´long ±º‰◊™≥…yyyy-MM-dd HH:mm:ss◊÷∑˚¥Æ<br>
     *
     * @param timeInMillis  ±º‰long÷µ
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFromMillis(long timeInMillis) {
        return getDateTimeFormat(new Date(timeInMillis));
    }

    /**
     * Ω´long ±º‰◊™≥…yyyy-MM-dd◊÷∑˚¥Æ<br>
     *
     * @param timeInMillis
     * @return yyyy-MM-dd
     */
    public static String getDateFromMillis(long timeInMillis) {
        return getDateFormat(new Date(timeInMillis));
    }

    /**
     * Ω´date◊™≥…yyyy-MM-dd HH:mm:ss◊÷∑˚¥Æ
     * <br>
     *
     * @param date Date∂‘œÛ
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultDateTimeFormat.get());
    }

    /**
     * Ω´ƒÍ‘¬»’µƒint◊™≥…yyyy-MM-ddµƒ◊÷∑˚¥Æ
     *
     * @param year  ƒÍ
     * @param month ‘¬ 1-12
     * @param day   »’
     *              ◊¢£∫‘¬±Ì æCalendarµƒ‘¬£¨±» µº –°1
     *              ∂‘ ‰»ÎœÓŒ¥◊ˆ≈–∂œ
     */
    public static String getDateFormat(int year, int month, int day) {
        return getDateFormat(getDate(year, month, day));
    }

    /**
     * Ω´date◊™≥…yyyy-MM-dd◊÷∑˚¥Æ<br>
     *
     * @param date Date∂‘œÛ
     * @return yyyy-MM-dd
     */
    public static String getDateFormat(Date date) {
        return dateSimpleFormat(date, defaultDateFormat.get());
    }

    /**
     * ªÒµ√HH:mm:ssµƒ ±º‰
     *
     * @param date
     * @return
     */
    public static String getTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultTimeFormat.get());
    }

    /**
     * ∏Ò ΩªØ»’∆⁄œ‘ æ∏Ò Ω
     *
     * @param sdate  ‘≠ º»’∆⁄∏Ò Ω "yyyy-MM-dd"
     * @param format ∏Ò ΩªØ∫Û»’∆⁄∏Ò Ω
     * @return ∏Ò ΩªØ∫Ûµƒ»’∆⁄œ‘ æ
     */
    public static String dateFormat(String sdate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        return dateSimpleFormat(date, formatter);
    }

    /**
     * ∏Ò ΩªØ»’∆⁄œ‘ æ∏Ò Ω
     *
     * @param date   Date∂‘œÛ
     * @param format ∏Ò ΩªØ∫Û»’∆⁄∏Ò Ω
     * @return ∏Ò ΩªØ∫Ûµƒ»’∆⁄œ‘ æ
     */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return dateSimpleFormat(date, formatter);
    }

    /**
     * Ω´date◊™≥…◊÷∑˚¥Æ
     *
     * @param date   Date
     * @param format SimpleDateFormat
     *               <br>
     *               ◊¢£∫ SimpleDateFormatŒ™ø’ ±£¨≤…”√ƒ¨»œµƒyyyy-MM-dd HH:mm:ss∏Ò Ω
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateSimpleFormat(Date date, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat.get();
        return (date == null ? "" : format.format(date));
    }

    /**
     * Ω´"yyyy-MM-dd HH:mm:ss" ∏Ò Ωµƒ◊÷∑˚¥Æ◊™≥…Date
     *
     * @param strDate  ±º‰◊÷∑˚¥Æ
     * @return Date
     */
    public static Date getDateByDateTimeFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateTimeFormat.get());
    }

    /**
     * Ω´"yyyy-MM-dd" ∏Ò Ωµƒ◊÷∑˚¥Æ◊™≥…Date
     *
     * @param strDate
     * @return Date
     */
    public static Date getDateByDateFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateFormat.get());
    }

    /**
     * Ω´÷∏∂®∏Ò Ωµƒ ±º‰◊÷∑˚¥Æ◊™≥…Date∂‘œÛ
     *
     * @param strDate  ±º‰◊÷∑˚¥Æ
     * @param format  ∏Ò ΩªØ◊÷∑˚¥Æ
     * @return Date
     */
    public static Date getDateByFormat(String strDate, String format) {
        return getDateByFormat(strDate, new SimpleDateFormat(format));
    }

    /**
     * Ω´String◊÷∑˚¥Æ∞¥’’“ª∂®∏Ò Ω◊™≥…Date<br>
     * ◊¢£∫ SimpleDateFormatŒ™ø’ ±£¨≤…”√ƒ¨»œµƒyyyy-MM-dd HH:mm:ss∏Ò Ω
     *
     * @param strDate  ±º‰◊÷∑˚¥Æ
     * @param format  SimpleDateFormat∂‘œÛ
     * @throws ParseException »’∆⁄∏Ò Ω◊™ªª≥ˆ¥Ì
     */
    private static Date getDateByFormat(String strDate, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat.get();
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ω´ƒÍ‘¬»’µƒint◊™≥…date
     *
     * @param year  ƒÍ
     * @param month ‘¬ 1-12
     * @param day   »’
     *              ◊¢£∫‘¬±Ì æCalendarµƒ‘¬£¨±» µº –°1
     */
    public static Date getDate(int year, int month, int day) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month - 1, day);
        return mCalendar.getTime();
    }

    /**
     * «Û¡Ω∏ˆ»’∆⁄œ‡≤ÓÃÏ ˝
     *
     * @param strat ∆ º»’∆⁄£¨∏Ò Ωyyyy-MM-dd
     * @param end   ÷’÷π»’∆⁄£¨∏Ò Ωyyyy-MM-dd
     * @return ¡Ω∏ˆ»’∆⁄œ‡≤ÓÃÏ ˝
     */
    public static long getIntervalDays(String strat, String end) {
        return ((java.sql.Date.valueOf(end)).getTime() - (java.sql.Date
                .valueOf(strat)).getTime()) / (3600 * 24 * 1000);
    }

    /**
     * ªÒµ√µ±«∞ƒÍ∑›
     *
     * @return year(int)
     */
    public static int getCurrentYear() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * ªÒµ√µ±«∞‘¬∑›
     *
     * @return month(int) 1-12
     */
    public static int getCurrentMonth() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    /**
     * ªÒµ√µ±‘¬º∏∫≈
     *
     * @return day(int)
     */
    public static int getDayOfMonth() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * ªÒµ√ΩÒÃÏµƒ»’∆⁄(∏Ò Ω£∫yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getToday() {
        Calendar mCalendar = Calendar.getInstance();
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * ªÒµ√◊ÚÃÏµƒ»’∆⁄(∏Ò Ω£∫yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getYesterday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -1);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * ªÒµ√«∞ÃÏµƒ»’∆⁄(∏Ò Ω£∫yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeYesterday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -2);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * ªÒµ√º∏ÃÏ÷Æ«∞ªÚ’ﬂº∏ÃÏ÷Æ∫Ûµƒ»’∆⁄
     *
     * @param diff ≤Ó÷µ£∫’˝µƒÕ˘∫ÛÕ∆£¨∏∫µƒÕ˘«∞Õ∆
     * @return
     */
    public static String getOtherDay(int diff) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, diff);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * »°µ√∏¯∂®»’∆⁄º”…œ“ª∂®ÃÏ ˝∫Ûµƒ»’∆⁄∂‘œÛ.
     *
     * @param date   ∏¯∂®µƒ»’∆⁄∂‘œÛ
     * @param amount –Ë“™ÃÌº”µƒÃÏ ˝£¨»Áπ˚ «œÚ«∞µƒÃÏ ˝£¨ π”√∏∫ ˝æÕø…“‘.
     * @return Date º”…œ“ª∂®ÃÏ ˝“‘∫ÛµƒDate∂‘œÛ.
     */
    public static String getCalcDateFormat(String sDate, int amount) {
        Date date = getCalcDate(getDateByDateFormat(sDate), amount);
        return getDateFormat(date);
    }

    /**
     * »°µ√∏¯∂®»’∆⁄º”…œ“ª∂®ÃÏ ˝∫Ûµƒ»’∆⁄∂‘œÛ.
     *
     * @param date   ∏¯∂®µƒ»’∆⁄∂‘œÛ
     * @param amount –Ë“™ÃÌº”µƒÃÏ ˝£¨»Áπ˚ «œÚ«∞µƒÃÏ ˝£¨ π”√∏∫ ˝æÕø…“‘.
     * @return Date º”…œ“ª∂®ÃÏ ˝“‘∫ÛµƒDate∂‘œÛ.
     */
    public static Date getCalcDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * ªÒµ√“ª∏ˆº∆À„ Æ∑÷√Î÷Æ∫Ûµƒ»’∆⁄∂‘œÛ
     *
     * @param date
     * @param hOffset  ±∆´“∆¡ø£¨ø…Œ™∏∫
     * @param mOffset ∑÷∆´“∆¡ø£¨ø…Œ™∏∫
     * @param sOffset √Î∆´“∆¡ø£¨ø…Œ™∏∫
     * @return
     */
    public static Date getCalcTime(Date date, int hOffset, int mOffset, int sOffset) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hOffset);
        cal.add(Calendar.MINUTE, mOffset);
        cal.add(Calendar.SECOND, sOffset);
        return cal.getTime();
    }

    /**
     * ∏˘æ›÷∏∂®µƒƒÍ‘¬»’–° ±∑÷√Î£¨∑µªÿ“ª∏ˆjava.Util.Date∂‘œÛ°£
     *
     * @param year      ƒÍ
     * @param month     ‘¬ 0-11
     * @param date      »’
     * @param hourOfDay –° ± 0-23
     * @param minute    ∑÷ 0-59
     * @param second    √Î 0-59
     * @return “ª∏ˆDate∂‘œÛ
     */
    public static Date getDate(int year, int month, int date, int hourOfDay,
                               int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, hourOfDay, minute, second);
        return cal.getTime();
    }

    /**
     * ªÒµ√ƒÍ‘¬»’ ˝æ›
     *
     * @param sDate yyyy-MM-dd∏Ò Ω
     * @return arr[0]:ƒÍ£¨ arr[1]:‘¬ 0-11 , arr[2]»’
     */
    public static int[] getYearMonthAndDayFrom(String sDate) {
        return getYearMonthAndDayFromDate(getDateByDateFormat(sDate));
    }

    /**
     * ªÒµ√ƒÍ‘¬»’ ˝æ›
     *
     * @return arr[0]:ƒÍ£¨ arr[1]:‘¬ 0-11 , arr[2]»’
     */
    public static int[] getYearMonthAndDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int[] arr = new int[3];
        arr[0] = calendar.get(Calendar.YEAR);
        arr[1] = calendar.get(Calendar.MONTH);
        arr[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return arr;
    }

}
