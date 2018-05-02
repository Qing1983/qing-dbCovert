package tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static String SPRIT = System.getProperty("file.separator");

    /**
     * 判断文件路径中是否有文件
     * @param path
     * @return
     */
    public static Boolean isHasFiles (String path) {
        File file = new File(path);
        if (!judeFileExists(file)) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        return file.list().length > 0;
    }

    /**
     * 判断文件路径或文件是否存在
     * @param file
     * @return
     */
    public static Boolean judeFileExists (File file) {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取路径内的所有文件
     * @param path
     * @return
     */
    public static List<File> getPathFiles (String path) {
        File file = new File(path);
        List<File> files = new ArrayList<>();
        String [] fileLists = file.list();
        if (!isHasFiles(path)) {
            return files;
        }
        for (String fileList : fileLists) {
            File readfile = new File(path + SPRIT + fileList);
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

}
