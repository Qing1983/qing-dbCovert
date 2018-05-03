package theme;

import lombok.Data;
import lombok.ToString;
import tool.GenKit;

import java.util.HashMap;
import java.util.Map;


@Data
@ToString
public class DirectConfig {

    // 模版所在路径
    private String templateRootPath;

    // 默认包
    private String basePackage;

    // 生成根目录
    private String rootPath = GenKit.getJavaSRCPath();

    // 需要生成的元素列表
    private Map<String, DirectElement> directElementMap = new HashMap<>();

    public DirectConfig () {
        initDirectElementMap();
    }

    public DirectConfig (String rootPath, String templateRootPath, String basePackage) {
        this.rootPath = rootPath;
        this.templateRootPath = templateRootPath;
        this.basePackage = basePackage;
        initDirectElementMap();
    }

    public DirectConfig (String templateRootPath, String basePackage) {
        this.templateRootPath = templateRootPath;
        this.basePackage = basePackage;
        initDirectElementMap();
    }

    public DirectConfig ( String templateRootPath, String basePackage, Map<String, DirectElement> directElementMap) {
        this.templateRootPath = templateRootPath;
        this.basePackage = basePackage;
        this.directElementMap = directElementMap;
    }

    public DirectConfig (String rootPath, String templateRootPath, String basePackage, Map<String, DirectElement> directElementMap) {
        this.rootPath = rootPath;
        this.templateRootPath = templateRootPath;
        this.basePackage = basePackage;
        this.directElementMap = directElementMap;
    }


    // 默认初始化
    private void initDirectElementMap () {

        DirectElement daoElement = new DirectElement("dao", "Dao", "dao.direct");
        DirectElement modelElement = new DirectElement("model", "Vo", "model.direct");
        DirectElement serviceElement = new DirectElement("service", "Service", "service.direct");
        DirectElement serviceImplElement = new DirectElement("serviceImpl", "ServiceImpl", "serviceImpl.direct");
        DirectElement controllerElement = new DirectElement("controller", "Controller", "controller.direct");

        directElementMap.put(DirectMember.DAO_ELEMENT_NAME, daoElement);
        directElementMap.put(DirectMember.MODEL_ELEMENT_NAME, modelElement);
        directElementMap.put(DirectMember.SERVICE_ELEMENT_NAME, serviceElement);
        directElementMap.put(DirectMember.SERVICE_IMPL_ELEMENT_NAME, serviceImplElement);
        directElementMap.put(DirectMember.CONTROLLER_ELEMENT_NAME, controllerElement);
    }

}
