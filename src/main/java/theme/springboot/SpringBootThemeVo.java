package theme.springboot;

import java.util.HashMap;

import theme.GenFileVo;

public class SpringBootThemeVo {

	// 定义包名字的map
	HashMap packageMap = null;

	HashMap fileMap = null;

	// 定义总包的前缀
	String PKGPrefix = "com.szzt.train.tfds.business.";

	SpringBootThemeVo() {
		packageMap = new HashMap();

		/**
		 * 定义所需要文件的名称
		 */
		packageMap.put("controllerPKG", PKGPrefix + "web." + " ${tableVo.tableName}.controller");
		packageMap.put("daoPKG", PKGPrefix + "db." + "${tableVo.tableName}.dao");
		packageMap.put("formPKG", PKGPrefix + "db." + "${tableVo.tableName}.form");
		packageMap.put("modelPKG", PKGPrefix + "db." + "${tableVo.tableName}.model");
		packageMap.put("servicePKG", PKGPrefix + "db." + "${tableVo.tableName}.service");
		packageMap.put("serviceImplPKG", PKGPrefix + "db." + "${tableVo.tableName}.serviceImpl");
		packageMap.put("voPKG", PKGPrefix + ".business.db.${tableVo.tableName}.serviceImpl");

		fileMap = new HashMap();
		
		// controller
		GenFileVo controllerGenFileVo = new GenFileVo("${controllerPKG}/${tableVo.tableName}Controller.java", "controller.btl");
		fileMap.put("controllerFile", controllerGenFileVo);
		
		// dao
		GenFileVo daoGenFileVo = new GenFileVo("${daoPKG}/${tableVo.tableName}Dao.java", "dao.btl");
		fileMap.put("daoFile", daoGenFileVo);
		
		// form
		GenFileVo formGenFileVo = new GenFileVo("${formPKG}/${tableVo.tableName}Form.java", "form.btl");
		fileMap.put("formFile", formGenFileVo);
		
		// model
		GenFileVo modelGenFileVo = new GenFileVo("${modelPKG}/${tableVo.tableName}Model.java", "model.btl");
		fileMap.put("modelFile", modelGenFileVo);
		
		// service
		GenFileVo serviceGenFileVo = new GenFileVo("${servicePKG}/${tableVo.tableName}Service.java", "service.btl");
		fileMap.put("serviceFile", serviceGenFileVo);
		
		// serviceImpl
		GenFileVo serviceImplGenFileVo = new GenFileVo("${servicePKG}/${tableVo.tableName}/impl/ServiceImpl.java", "serviceImpl.btl");
		fileMap.put("serviceImplFile", serviceImplGenFileVo);

	}

}
