package tool;

public class StringUtil {

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperFirstCase (String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
