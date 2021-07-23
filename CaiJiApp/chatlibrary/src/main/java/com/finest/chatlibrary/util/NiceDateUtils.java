package com.finest.chatlibrary.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 一个好用的时间工具类
 */

public class NiceDateUtils {
    /**
     * 格式：年－月－日 小时：分钟：秒
     */
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式：月.日 小时：分钟
     */
    public static final String DOT_FORMAT_ONE = "MM.dd HH:mm";
    /**
     * 格式：月.日 小时：分钟
     */
    public static final String DOT_FORMAT_TWO = "yyyy.MM.dd";

    public static final String PARSE_DATE_FORMAT_S = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 格式：年－月－日 小时：分钟
     */
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

    /**
     * 格式：年月日 小时分钟秒
     */
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

    /**
     * 格式：年－月－日
     */
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式：月－日
     */
    public static final String SHORT_DATE_FORMAT = "MM-dd";

    /**
     * 格式：小时：分钟：秒
     */
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    /**
     * 格式：年-月
     */
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";

    /**
     * 格式：小时：分钟
     */
    public static final String FORMAT_FOUR = "HH:mm";

    /**
     * 年的加减
     */
    public static final int SUB_YEAR = Calendar.YEAR;

    /**
     * 月加减
     */
    public static final int SUB_MONTH = Calendar.MONTH;

    /**
     * 天的加减
     */
    public static final int SUB_DAY = Calendar.DATE;

    /**
     * 小时的加减
     */
    public static final int SUB_HOUR = Calendar.HOUR;

    /**
     * 分钟的加减
     */
    public static final int SUB_MINUTE = Calendar.MINUTE;

    /**
     * 秒的加减
     */
    public static final int SUB_SECOND = Calendar.SECOND;

    static final String[] dayNames = {"星期日", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"};

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";

    public static final String PARSE_DATE_FORMAT_CH_HMS = "yyyy年MM月dd日HH时mm分";

    public NiceDateUtils() {
    }

    /**
     * 有参数汉字格式的时间
     *
     * @return
     */
    public static String getTimechs(Date date) {
        String time;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_CN);
        time = sdf.format(date);
        return time;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     */
    public static Date stringtoDate(String dateStr, String format,
                                    ParsePosition pos) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr, pos);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringtoDate(strTime, formatType); // String类型转成date类型
        long currentTime = 0;
        try {
            if (date == null) {
                return 0;
            } else {
                currentTime = date.getTime(); // date类型转成long类型
            }
        } catch (Exception e) {
            currentTime = 0;
        }
        return currentTime;
    }

    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }

    public static Date getCurrentTimeToDate(boolean isDateTime) {
        SimpleDateFormat sdf = null;

        if (isDateTime) {
            sdf = new SimpleDateFormat(FORMAT_ONE);
        } else {
            sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        }

        String CurrentTime = sdf.format(new Date());
        Date CurrentDate = null;
        try {
            CurrentDate = sdf.parse(CurrentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CurrentDate;
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * 日期是否一样
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean areSameDay(Date dateA, Date dateB) {

        if (dateA == null || dateB == null) {
            return false;
        }

        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isOut3Months(Date time) {
        if (time != null) {
            return (System.currentTimeMillis() - time.getTime()) > 3 * 30 * 24 * 60 * 60 * 1000L;
        }
        return false;
    }

    /**
     * @param dateStr
     * @param amount
     * @return
     */
    public static String dateSub(int dateKind, String dateStr, int amount) {
        Date date = stringtoDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * 两个日期相减
     *
     * @param firstTime
     * @param secTime
     * @return 相减得到的秒数
     */
    public static long timeSub(String firstTime, String secTime) {
        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (second - first) / 1000;
    }

    /**
     * 获得某月的天数
     *
     * @param year  int
     * @param month int
     * @return int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if ("1".equals(month) || "3".equals(month) || "5".equals(month)
                || "7".equals(month) || "8".equals(month) || "10".equals(month)
                || "12".equals(month)) {
            days = 31;
        } else if ("4".equals(month) || "6".equals(month) || "9".equals(month)
                || "11".equals(month)) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 获取某年某月的天数
     *
     * @param year  int
     * @param month int 月份[1-12]
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     *
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     *
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     *
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     *
     * @param date Date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     *
     * @param date Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * 比较两个日期的年差
     *
     * @param //before
     * @param after
     * @return
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     * @param //before
     * @param after
     * @return
     */
    public static int yearDiffCurr(String after) {
        Date beforeDay = new Date();
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(beforeDay) - getYear(afterDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     * @param before
     * @return
     * @author chenyz
     */
    public static long dayDiffCurr(String before) {
        Date currDate =NiceDateUtils.stringtoDate(currDay(), LONG_DATE_FORMAT);
        Date beforeDate = stringtoDate(before, LONG_DATE_FORMAT);
        return (currDate.getTime() - beforeDate.getTime()) / 86400000;

    }

    /**
     * 获取每月的第一周
     *
     * @param year
     * @param month
     * @return
     * @author chenyz
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     *
     * @param year
     * @param month
     * @return
     * @author chenyz
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得当前日期字符串，格式"yyyy_MM_dd_HH_mm_ss"
     *
     * @return
     */
    public static String getCurrent() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        StringBuffer sb = new StringBuffer();
        sb.append(year).append("_").append(addzero(month, 2))
                .append("_").append(addzero(day, 2)).append("_")
                .append(addzero(hour, 2)).append("_").append(
                addzero(minute, 2)).append("_").append(
                addzero(second, 2));
        return sb.toString();
    }

    /**
     * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNow() {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), FORMAT_ONE);
    }

    public static String getNowTime(String type) {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), type);
    }

    /**
     * 根据生日获取星座
     *
     * @param birth YYYY-mm-dd
     * @return
     */
    public static String getAstro(String birth) {
        if (!isDate(birth)) {
            birth = "2000" + birth;
        }
        if (!isDate(birth)) {
            return "";
        }
        int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
                birth.lastIndexOf("-")));
        int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     *
     * @param date YYYY-mm-dd
     * @return
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     *
     * @param date    日期 为null时表示当天
     * @param //month 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     *
     * @param date    日期 为null时表示当天
     * @param //month 相加(相减)的月数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     *
     * @param day
     * @param format
     * @return
     * @author chenyz
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
     *
     * @param date 日期 为null时表示当天
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }

    /**
     * 获取当前的日期(yyyy-MM-dd)
     */
    public static String currDay() {
        return NiceDateUtils.dateToString(new Date(),NiceDateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public static String befoDay() {
        return befoDay(NiceDateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     *
     * @param format
     * @return
     * @author chenyz
     */
    public static String befoDay(String format) {
        return NiceDateUtils.dateToString(NiceDateUtils.nextDay(new Date(), -1), format);
    }

    /**
     * 获取明天的日期
     */
    public static String afterDay() {
        return NiceDateUtils.dateToString(NiceDateUtils.nextDay(new Date(), 1),
                NiceDateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     *
     * @return
     */
    public static int getDayNum() {
        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     *
     * @param day
     * @return
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /**
     * 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd
     */
    public static String getYmdDateCN(String datestr) {
        if (datestr == null) {
            return "";
        }
        if (datestr.length() < 10) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7))
                .append(datestr.substring(8, 10));
        return buf.toString();
    }

    /**
     * 获取本月第一天
     *
     * @param format
     * @return
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取本月最后一天
     *
     * @param format
     * @return
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     *
     * @param sourceDate
     * @param formatLength
     * @return 重组后的数据
     */
    public static String addzero(int sourceDate, int formatLength) {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }

    /**
     * 终止时间大于起始时间一个小时则返回true；
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isInputTimeTooLong(Date start, Date end) {
        long length = end.getTime() - start.getTime();
        // long MaxLength = 30L * 24L * 60L * 60L * 1000L;// 一个月
        long MaxLength = 60L * 60L * 1000L;// 一个小时

        return length > MaxLength;
    }

    /**
     * 判断输入的起始时间是否合法
     *
     * @param start
     * @param end
     * @return return 1:起始时间大于终止时间，2：表示终止时间大于当前时间,0:表示正常
     */
    public static int isInputTimeValid(Date start, Date end) {
        Date currentDate = Calendar.getInstance().getTime();
        if (currentDate.getTime() < end.getTime()) {
            return 2;
        }

        if (start.getTime() >= end.getTime()) {
            return 1;
        }

        return 0;
    }

    public static String timeReduceTime(String time, long second) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TWO);
            Date dateStr = null;
            try {
                dateStr = sdf.parse(time);
                dateStr.setTime(dateStr.getTime() - second);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dateToString(dateStr, FORMAT_TWO);
        } else {
            return "";
        }
    }

    public static String timeAddTime(String time, long second) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TWO);
            Date dateStr = null;
            try {
                dateStr = sdf.parse(time);
                dateStr.setTime(dateStr.getTime() + second);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return dateToString(dateStr, FORMAT_TWO);
        } else {
            return "";
        }
    }

    /**
     * 时间大小比较
     */
    public static int compareTime(String t1, String t2, String format) {
        String time1 = t1;
        String time2 = t2;

        DateFormat sdf = new SimpleDateFormat(format);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(sdf.parse(time1));
            c2.setTime(sdf.parse(time2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int result = c1.compareTo(c2);

        return result;
    }

    /**
     * 年-月-日 时:分:秒
     *
     * @return
     */
    public static String getShiftTime(String str) {

        Date mDate = stringtoDate(str, "yyyyMMddHHmm");

        String time = "";

        if (mDate == null) {
            return time;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TWO);
        time = sdf.format(mDate);
        return time;
    }

    /**
     * 年-月-日 时:分:秒.毫秒
     *
     * @return
     */

    public static String removeMillisecond(String srcTime) {
        Date mDate = stringtoDate(srcTime, PARSE_DATE_FORMAT_S);
        String time = "";
        if (mDate == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ONE);
        time = sdf.format(mDate);
        return time;
    }

    /**
     * 判断时间是否有效
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean isValidTime(Date dateA, Date dateB) {
        if (dateA == null || dateB == null) {
            return false;
        }
        boolean isValid = false;
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Date newDateB = stringtoDate(dateToString(dateB, LONG_DATE_FORMAT), LONG_DATE_FORMAT);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(newDateB);

        int result = calDateA.compareTo(calDateB);
        if (result >= 0) {
            isValid = true;
        }
        return isValid;
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        if (day == 0) {
            return hour + "小时" + min + "分钟";
        } else {
            return day + "天" + hour + "小时";
        }
    }
}
