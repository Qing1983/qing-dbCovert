package tool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class StringTool {

	/**
	 * 大写首字母
	 * @param text
	 * @return
	 */
	public static String  upperCaseFirst(String text ){
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}
	
	/**
	 * 小写首字母
	 * @param text
	 * @return
	 */
	public static String lowerCaseFirst(String text){
		return text.substring(0, 1).toLowerCase() + text.substring(1);
	}
	
	/**
	 * 把文件中的内容读到一个字符串中，编码使用VM的缺省编码。
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String readFileToString(String fileName) throws Exception {
		File file = new File(fileName);
		String content = FileUtils.readFileToString(file);
		return content;
	}

	public static void writeStringToFile(String fileName, String data) throws IOException {
		File file = new File(fileName);
		FileUtils.writeStringToFile(file, data);

	}

	public static boolean isEmpty(String text) {
		if (text == null || text.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String text) {
		return !isEmpty(text);
	}
}
