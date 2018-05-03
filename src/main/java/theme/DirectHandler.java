package theme;

import db.model.ColumnVo;
import db.model.DBVo;
import db.model.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Slf4j
public class DirectHandler {

    public static final String [] defaultGenArray = {
            DirectMember.MODEL_ELEMENT_NAME, DirectMember.DAO_ELEMENT_NAME, DirectMember.SERVICE_ELEMENT_NAME,
            DirectMember.SERVICE_IMPL_ELEMENT_NAME, DirectMember.CONTROLLER_ELEMENT_NAME};

    private String [] genArray;

    private String tableName;

    private String className;

    private DirectConfig directConfig;

    static String CR = System.getProperty("line.separator");


    public void gen (DirectConfig directConfig, TableVo tableVo, String [] genArray) throws Exception {
        init(directConfig, genArray);
        genSingeTable(tableVo);
    }

    public void gen (DirectConfig directConfig, DBVo dbVo, String [] genArray) throws Exception {
        init(directConfig, genArray);
        genMoreTable(dbVo);
    }


    /**
     * 初始化参数
     * @param directConfig
     * @param genArray
     */
    private void init (DirectConfig directConfig, String [] genArray) {
        if (genArray == null) {
            this.genArray = defaultGenArray;
        } else {
            this.genArray = genArray;
        }
        this.directConfig = directConfig;
    }


    /**
     * 单张表生成
     * @param tableVo
     * @throws Exception
     */
    public void genSingeTable (TableVo tableVo) throws Exception {
        this.tableName = tableVo.getTableName();
        this.className = getClassName(tableVo.getTableName());
        // 第一步获取表相关数据
        List<ColumnVo> tableAttrs = getOriginalAttrs(tableVo);
        // 第二步初始化模板
        GroupTemplate modelGroupTemplate = getGroupTemplate();
        // 模板生成
        templateGen(modelGroupTemplate, className, tableAttrs);
    }

    /**
     * 多张表生成
     * @param dbVo
     * @throws Exception
     */
    public void genMoreTable (DBVo dbVo) throws Exception {
        for (TableVo tableVo : dbVo.getTableVoList()) {
            genSingeTable(tableVo);
        }
    }

    /**
     * 转换类名称
     * @param name
     * @return
     */
    private String getClassName (String name) {
        return name;
    }

    /**
     * 获取原始的表attrs
     * @param
     * @param
     * @param tableVo
     * @return
     */
    private List<ColumnVo> getOriginalAttrs (TableVo tableVo) {
        ArrayList<ColumnVo> attrs = new ArrayList<>();
        attrs.addAll(tableVo.getColumnVoList());
        return attrs;
    }

    /**
     * 表attrs排序
     * @param attrs
     * @param attrs
     */
    private void attrsSort (List<Map> attrs) {

    }

    /**
     * 获取GroupTemplate
     * @return
     * @throws Exception
     */
    private GroupTemplate getGroupTemplate () throws Exception {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(directConfig.getTemplateRootPath());
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        return gt;
    }

    private void templateBindingDate (Template template, String className, List<ColumnVo> tableAttrs) {
        for (String name : genArray) {
            DirectElement element = directConfig.getDirectElementMap().get(name);
            template.binding(name + DirectMember.PACKAGE_NAME, directConfig.getBasePackage() + "." + element.getPackageName());
            template.binding(name + DirectMember.CLASS_NAME, className + element.getSuffix());
            template.binding(name + DirectMember.IMPORT_NAME, getDeaultImport(element.getRely()));
            template.binding(name + DirectMember.COMMENT_NAME, element.getComment());
            template.binding(name + DirectMember.CLASS_EXTEND_NAME, element.getExtendClass());
        }
        template.binding(DirectMember.TABLE_ATTRS_NAME, tableAttrs);
        template.binding(DirectMember.TABLE_NAME, tableName);
    }

    private void templateBindingFunction (Template template) {
        log.info("===============开始绑定==================");
        template.gt.registerFunction("conversionObj", (paras, ctx) -> {
            String objName = paras[0].toString();
            return objName.substring(0, 1).toLowerCase() + objName.substring(1);
        });
    }

    private void templateGen (GroupTemplate groupTemplate, String className, List<ColumnVo> tableAttrs) throws Exception {
        for (String name : genArray) {
            DirectElement element = directConfig.getDirectElementMap().get(name);
            Template template = groupTemplate.getTemplate(element.getTemplateName());
            templateBindingDate(template, className, tableAttrs);
            templateBindingFunction(template);
            String templateCode = template.render();
            log.info(templateCode);
            saveSourceFile(directConfig.getRootPath(), directConfig.getBasePackage() + "." + element.getPackageName(),
                    className + element.getSuffix(), templateCode);
        }
    }

    private String getDeaultImport (List<String> rely) {
        StringBuilder srcHead = new StringBuilder();
        if (rely != null && rely.size() > 0) {
            for (String name : rely) {
                DirectElement element = directConfig.getDirectElementMap().get(name);
                srcHead.append("import " + directConfig.getBasePackage() + "." + element.getPackageName() + "." + className + element.getSuffix() + ";" + CR);
            }
        }
        return srcHead.toString();
    }

    private void saveSourceFile (String srcPath, String pkg, String className, String content) throws IOException {
        log.info("==========================输出文件到本地====================");
        String file = this.directConfig.getRootPath() == null ? srcPath + File.separator + pkg.replace('.', File.separatorChar)
                : this.directConfig.getRootPath();
        File f  = new File(file);
        f.mkdirs();
        File target = new File(file,className + ".java");
        if (target.exists()) {
            log.info("======================== + " + className + "文件已存在====================");
            return;
        }
        FileWriter writer = new FileWriter(target);
        writer.write(content);
        writer.close();
        log.info("路径:" + file + className + ".java");
    }

}
