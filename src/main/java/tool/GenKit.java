package tool;

import java.io.File;


public class GenKit {

    private static String srcPathRelativeToSrc = "/main/java";

    private static String resourcePathRelativeToSrc = "/main/resources";

    public GenKit() {
    }

    public static String getJavaSRCPath() {
        return getPath(srcPathRelativeToSrc);
    }

    public static String getJavaResourcePath() {
        return getPath(resourcePathRelativeToSrc);
    }

    public static void setSrcPathRelativeToSrc(String srcPathRelativeToSrc) {
        srcPathRelativeToSrc = srcPathRelativeToSrc;
    }

    public static void setResourcePathRelativeToSrc(String resourcePathRelativeToSrc) {
        resourcePathRelativeToSrc = resourcePathRelativeToSrc;
    }

    private static String getPath(String relativeToSrc) {
        String userDir = System.getProperty("user.dir");
        if(userDir == null) {
            throw new NullPointerException("用户目录未找到");
        } else {
            File src = new File(userDir, "src");
            File resSrc = new File(src.toString(), relativeToSrc);
            String srcPath;
            if(resSrc.exists()) {
                srcPath = resSrc.toString();
            } else {
                srcPath = src.toString();
            }

            return srcPath;
        }
    }
}
