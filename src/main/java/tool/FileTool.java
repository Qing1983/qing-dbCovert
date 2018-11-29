package tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileTool {

    public static String SEPARATOR = System.getProperty("file.separator");

    /**
	 * Description: 创建一个由path指定的目录,如果改目录存在，则不会创建
	 * @param path 文件目录
	 * @return 创建或者已经存在，返回true,否则返回false
	 */
	public static boolean createDir(String path) {
		try {
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				if (file.mkdirs()) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			log.error("createDir" + path + "fail", e);
			return false;
		}
	}
    
    /**
     * 判断文件路径中是否有文件
     * @param path
     * @return
     */
    public static Boolean hasFiles (String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        return file.list().length > 0;
    }

    /**
     * 获取路径内的所有文件
     * @param path
     * @return
     */
    public static List<File> getFileList (String path) {
        path = formatSeparator(path);
        File file = new File(path);
        List<File> files = new ArrayList<>();
        String [] fileLists = file.list();
        if (!hasFiles(path)) {
            return files;
        }
        for (String fileList : fileLists) {
            File readfile = new File(path + SEPARATOR + fileList);
            files.add(readfile);
        }
        return files;
    }

    /**
     * 获取不带后缀的文件名称
     * @param file
     * @return
     */
    public static String getFileNameWithoutSuffix (File file) {
        String file_name = file.getName();
        return file_name.substring(0, file_name.lastIndexOf("."));
    }

    /**
     * 获取打包后根路径
     * @return
     */
    public static String getAbsolutePath () {
        String path = FileTool.class.getProtectionDomain().getCodeSource()
                .getLocation().getFile();
        File file = new File(path);
        return file.getAbsolutePath();
    }

    /**
     * 格式化斜杠
     * @param path
     * @return
     */
    public static String formatSeparator (String path) {
        return path.replace("/", SEPARATOR);
    }

}
