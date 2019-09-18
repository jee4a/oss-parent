package com.jee4a.oss.framework.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类.
 * 
 */
public class DateFormatUtils {

	public final static DateFormat	DATE_TIME_FORMAT	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static DateFormat	DATE_FORMAT			= new SimpleDateFormat("yyyy-MM-dd");
	
	public final static DateFormat	SIMPLE_DATE_FORMAT			= new SimpleDateFormat("y-M-d");

	public final static DateFormat	DATE_FORMAT_2		= new SimpleDateFormat("MM-dd");

	public static Date parseString(DateFormat dateFormat, String dateStr) throws ParseException {
		return dateFormat.parse(dateStr);
	}

	public static String parseDate(DateFormat dateFormat, Date date) {
		return dateFormat.format(date);
	}

	public static String parseDateToDatetimeString(Date date) {
		return DATE_TIME_FORMAT.format(date);
	}

	public static String converToStringDate(Date date, String formatString) {
		if (StringUtils.isEmpty(formatString)) {
			SimpleDateFormat sdf_datetime_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf_datetime_s.format(date);
		}
		else {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			return format.format(date);
		}
	}

	/**
	 * @see 将字符串转化为JAVA时间类型(精确到秒)。
	 * 
	 * @return Date date。JAVA时间类型。
	 * @param String
	 *            。字符串。
	 */
	public static Date converToDateTime(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see 将字符串转化为JAVA时间类型(精确到分)。
	 * 
	 * @return Date date。JAVA时间类型。
	 * @param String
	 *            。字符串。
	 */
	public static String converToStringMiniuteDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see 得到传入日期n天后的日期,如果传入日期为null，则表示当前日期n天后的日期
	 * 
	 * @param Date
	 *            dt
	 * @param days
	 *            可以为任何整数，负数表示前days天，正数表示后days天
	 * @return Date
	 */
	public static Date getAddDayTime(Date dt, int days) {
		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);
		return cal.getTime();
	}

	/**
	 * @see 将日期转化为字符串(精确到秒)。
	 * 
	 * @return Date date。JAVA时间类型。
	 * @param String
	 *            。字符串。
	 */
	public static String converToStringTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date converToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	public static String converToStringDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String converToStringDate2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		try {
			return format.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getToday() {
		return DateFormatUtils.converToStringDate(new Date());
	}

	/**
	 * 将时间移动days天
	 * 
	 * @param date
	 *            当前日期
	 * @param days
	 *            移动的天数
	 * @return 移动后的时间
	 */
	public static Date addDay(Date date, Integer days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}
	
	
	/**
	 * 当前时间增加秒
	 * @param date
	 * @param sec
	 * @return
	 */
	public static Date addSec(Date date, Integer sec) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, sec);
		return c.getTime();
	}
	
	

	/**
	 * 将时间移动months天
	 * 
	 * @param date
	 *            当前日期
	 * @param months
	 *            移动的月数
	 * @return 移动后的时间
	 */
	public static Date addMonth(Date date, Integer months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	/**
	 * Description ： 比较两个时间的大小
	 * 
	 * @author "hejp" 2015年3月11日 下午7:59:28
	 * @param data1
	 * @param data2
	 * @return
	 *
	 */
	public static int compare_date(Date data1, Date data2) {
		try {
			if (data1.getTime() > data2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return 1;
			}
			else if (data1.getTime() < data2.getTime()) {
				// System.out.println("dt1在dt2后");
				return -1;
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Date dayBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date dayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public static String timeAgo(Date date) {
	    if (date == null) {
	        return "恒古";
	    }
	    long nowSec = System.currentTimeMillis() / 1000;
	    long sec = nowSec - date.getTime() / 1000;
	    
	    String postfix = "前";
	    if (sec < 0) {
	        sec = 0 - sec;
	        postfix = "后";
	    }
	    
	    if (sec >= 0 && sec < 60) {
	        return "刚刚";
	    }
	    long minute = sec / 60;
	    if (minute < 60 ) {
	        return "" + minute + "分钟" + postfix;
	    }
	    long hour = minute / 60;
	    if (hour < 24) {
	        return "" + hour + "小时" + postfix;
	    }
	    long day = hour / 24;
	    if (day < 30) {
	        return "" + day + "天" + postfix;
	    }
	    long month = day / 30;
	    if (month < 12) {
	        return "" + month + "月" + postfix;
	    }
	    long year = month / 12;
	    return "" + year + "年" + postfix;
	}
	
	/**
	* 取得两个日期之间的日数
	* 
	* @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
	*/
	public static long daysBeetween(java.sql.Timestamp t1, java.sql.Timestamp t2) {
	return (t2.getTime() - t1.getTime()) / (24 * 60 * 60 * 1000);
	}
}
