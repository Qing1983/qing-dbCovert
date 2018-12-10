package tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * 
 * @since 1.0.0
 */
public final class DateTool {

	private final static Logger log = LoggerFactory.getLogger(DateTool.class);

	public static class Format {
		// 缺省的时间格式
		public static final String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";
		public static final String DEFAULT_DATE = "yyyy-MM-dd";
		public static final String DEFAULT_TIME = "HH:mm:ss";

		// 没有连字符和空格的时间格式
		public static final String NOSPACE_DATETIME = "yyyyMMddHHmmss";
		public static final String NOSPACE_DATE = "yyyyMMdd";
		public static final String NOSPACE_TIME = "hhmmss";

		// 中国的时间格式
		public static final String CHINESE_DATETIME = "yyyy年MM月dd日 HH点mm分ss秒";
		public static final String CHINESE_DATE = "yyyy年MM月dd日";
		public static final String CHINESE_TIME = "HH点mm分ss秒";

	}

	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Format.DEFAULT_DATETIME);

	/**
	 * Description: 获取格式化后的时间字符串
	 * 
	 * @param longTime 时间戳
	 * @return
	 */
	public static Date getDate(String longTime) {
		Long time = 0L;
		try {
			time = Long.parseLong(longTime);
		} catch (Exception e) {
			log.error("covert failed.");
		}
		Date date = new Date(time);

		return date;
	}

	/**
	 * Description: 获取格式化后的时间字符串
	 * 
	 * @param longTime 时间戳
	 * @param formatText 格式化字符串
	 * @return
	 */
	public static String getString(long longTime, String formatText) {
		SimpleDateFormat ft = new SimpleDateFormat(formatText);
		return ft.format(longTime);
	}

	/**
	 * Description: 获取格式化后的时间字符串
	 * 
	 * @param date 日期
	 * @param formatText 格式化字符串
	 * @return
	 */
	public static String getString(Date date, String formatText) {
		SimpleDateFormat ft = new SimpleDateFormat(formatText);
		return ft.format(date);
	}

	/**
	 * 获取当前日期与时间
	 */
	public static String getCurrentDateTime() {
		return dateTimeFormat.format(new Date());
	}
}
