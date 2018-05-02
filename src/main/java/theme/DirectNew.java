package theme;

import db.model.DBVo;
import global.Config;
import tool.FileUtil;
import tool.GenKit;
import tool.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectNew {

    public static void gen (DBVo dbVo) throws Exception {
        // 第一步获取配置路径中所有的文件 包括文件名称等信息并生成DirectElement
        String themePath = Config.theme;
        Map<String, DirectElement> directElements = getElementsByPath(themePath);
        DirectConfig directConfig = new DirectConfig(GenKit.getJavaSRCPath(), GenKit.getJavaSRCPath() + themePath, directElements);
        DirectHandler directHandler = new DirectHandler();
        directHandler.gen(directConfig, dbVo, getGenArray(themePath));
    }

    /**
     * 获取所有需要生成的模板对象
     * @param path
     * @return
     */
    private static Map<String, DirectElement> getElementsByPath (String path) {
        List<File> files = getFilsByOriginPath(path);
        Map<String, DirectElement> directElements = new HashMap<>();
        // 第二步 构造Element对象
        for (File file : files) {
            String fileSuffixName = FileUtil.getFileNameWithoutSuffix(file);
            DirectElement directElement = new DirectElement("", StringUtil.upperFirstCase(fileSuffixName), file.getName());
            directElements.put(fileSuffixName, directElement);
        }
        return directElements;
    }

    /**
     * 获取需要生成的元素数组
     * @param path
     * @return
     */
    private static String [] getGenArray (String path) {
        List<String> genArray = new ArrayList<>();
        List<File> files = getFilsByOriginPath(path);
        for (File file : files) {
            String fileSuffixName = FileUtil.getFileNameWithoutSuffix(file);
            genArray.add(fileSuffixName);
        }
        return (String[]) genArray.toArray();
    }

    /**
     * 获取原始路径中所有文件
     * @return
     */
    private static List<File> getFilsByOriginPath (String path) {
        String filePath = GenKit.getJavaSRCPath() + path;
        // 第一步 获取路径中所有文件
        return FileUtil.getFileList(filePath);
    }

}
