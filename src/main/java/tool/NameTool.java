package tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameTool {

	public static final char UNDERLINE = '_';

	/**
	 * 判断是否是驼峰
	 * @param text
	 * @return
	 */
	public static boolean isCamel(String text) {
		if (text == null || text.contains("_")) {
			return false;
		}
		
		return true;
	}
	/**
	 * 转换成下划线命名
	 * @param text
	 * @return
	 */
	public static String toUnderline(String text) {

		if (text == null || "".equals(text.trim())) {
			return "";
		}
		if (!isCamel(text)) {
			return text;
		}

		int len = text.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	/**
	 * 转换成驼峰命名
	 * @param text
	 * @return
	 */
	public static String toCamel(String text) {
		if (text == null || "".equals(text.trim())) {
			return "";
		}
		int len = text.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(text.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}


}
