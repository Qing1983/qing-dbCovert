package theme;

import java.io.IOException;
import java.util.HashMap;

import org.beetl.core.Template;

import db.model.ModuleVo;
import db.model.TableVo;
import db.service.DBService;
import global.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.BeetlRenderTool;
import tool.JsonTool;
import tool.StringTool;

@Slf4j
@Data
public class SpringTrainTheme {
	// ========================================================================
	// 基础名字
	// ========================================================================
	TableVo tableVo; // 表对象
	String tableCN = ""; // 表的中文名字
	String moduleName; // 模块名
	String prefixPackage; // 包前缀

	// ========================================================================
	// 定义文件名称
	// ========================================================================
	String daoPackage = "db.${ctx.moduleName}.dao"; // dao 包名
	String daoClass = "${ctx.tableVo.camelTableName}Mapper"; // dao 类名
	String daoObj = "";

	String modelPackage = "db.${ctx.moduleName}.model"; // model 包名
	String modelClass = "${ctx.tableVo.camelTableName}Model"; // dao 类名
	String modelObj = "";

	String servicePackage = "db.${ctx.moduleName}.service"; // service 包名
	String serviceClass = "${ctx.tableVo.camelTableName}Service"; // service 类名
	String serviceObj = ""; // service对象名

	String implPackage = "db.${ctx.moduleName}.service.impl";// impl 包名
	String implClass = "${ctx.tableVo.camelTableName}ServiceImpl"; // impl 类名
	String implObj = "";

	String controllerPackage = "controller.${ctx.moduleName}"; // controller包名
	String controllerClass = "${ctx.tableVo.camelTableName}Controller"; // controller类名
	String controllerObj = ""; // controller对象名

	String mapperxmlPackage = "${ctx.moduleName}"; // mapper包名
	String mapperxmlClass = "${ctx.tableVo.camelTableName}Mapper"; // mapper类名
	String mapperxmlObj = "";

	// ========================================================================
	// 定义输出文件夹和模版文件夹
	// ========================================================================
	String outPath; // 输出文件夹
	String beetlPath; // 模版文件夹

	/**
	 * 初始化SpringTrain主题
	 * 
	 * @param tableCN
	 * @param tableVo
	 * @param moduleName
	 * @param prefixPackage
	 * @param outPath
	 * @param beetlPath
	 * @throws Exception
	 */
	public SpringTrainTheme(String tableCN, TableVo tableVo, String moduleName, String prefixPackage, String outPath, String beetlPath) throws Exception {
		this.tableCN = tableCN;
		this.tableVo = tableVo;
		this.moduleName = moduleName;
		this.prefixPackage = prefixPackage;
		this.outPath = outPath;
		this.beetlPath = beetlPath;

	}

	/**
	 * 渲染文件名称
	 * 
	 * @throws IOException
	 */
	public void renderName() throws IOException {
		daoPackage = prefixPackage + BeetlRenderTool.renderText("ctx", (Object) this, daoPackage);
		daoClass = StringTool.upperCaseFirst(BeetlRenderTool.renderText("ctx", (Object) this, daoClass));
		daoObj = StringTool.lowerCaseFirst(daoClass);

		modelPackage = prefixPackage + BeetlRenderTool.renderText("ctx", (Object) this, modelPackage);
		modelClass = StringTool.upperCaseFirst(BeetlRenderTool.renderText("ctx", (Object) this, modelClass));
		modelObj = StringTool.lowerCaseFirst(modelClass);

		servicePackage = prefixPackage + BeetlRenderTool.renderText("ctx", (Object) this, servicePackage);
		serviceClass = StringTool.upperCaseFirst(BeetlRenderTool.renderText("ctx", (Object) this, serviceClass));
		serviceObj = StringTool.lowerCaseFirst(serviceClass);

		implPackage = prefixPackage + BeetlRenderTool.renderText("ctx", (Object) this, implPackage);
		implClass = StringTool.upperCaseFirst(BeetlRenderTool.renderText("ctx", (Object) this, implClass));
		implObj = StringTool.lowerCaseFirst(implClass);

		controllerPackage = prefixPackage + BeetlRenderTool.renderText("ctx", (Object) this, controllerPackage);
		controllerClass = StringTool.upperCaseFirst(BeetlRenderTool.renderText("ctx", (Object) this, controllerClass));
		controllerObj = StringTool.lowerCaseFirst(controllerClass);

		mapperxmlPackage = "mapper." + BeetlRenderTool.renderText("ctx", (Object) this, mapperxmlPackage);
		mapperxmlClass = StringTool.upperCaseFirst(BeetlRenderTool.renderText("ctx", (Object) this, mapperxmlClass));
		mapperxmlObj = StringTool.lowerCaseFirst(mapperxmlClass);

		log.debug(JsonTool.toJSON(this));
	}

	/**
	 * 渲染文件
	 * 
	 * @param packageName
	 *            包名
	 * @param className
	 *            类名
	 * @param objName
	 *            对象名
	 * @param fileSuffix
	 *            生成文件的后缀名
	 * @param templateName
	 *            模版名称
	 * @throws Exception
	 */
	public void renderFile(String packageName, String className, String objName, String fileSuffix, String templateName) throws Exception {

		// 定义输出对应的文件
		String outFilePath = outPath + "/" + packageName.replace('.', '/') + "/" + className + fileSuffix;

		// 根据模版渲染文件
		Template template = BeetlRenderTool.getFileTemplate(beetlPath, templateName);
		template.binding("ctx", this);
		String text = template.render();
		log.debug("\n" + text);

		// 渲染的内容写入文件
		StringTool.writeStringToFile(outFilePath, text);
	}

	/**
	 * 渲染主题涉及的各个文件
	 * 
	 * @throws Exception
	 */
	public void render() throws Exception {
		renderName();
		renderFile(controllerPackage, controllerClass, controllerObj, ".java", "controller.btl"); // 控制器
		renderFile(daoPackage, daoClass, daoObj, ".java", "dao.btl"); // dao层文件
		renderFile(modelPackage, modelClass, modelObj, ".java", "model.btl"); // model层文件
		renderFile(servicePackage, serviceClass, serviceObj, ".java", "service.btl"); // service层文件
		renderFile(implPackage, implClass, implObj, ".java", "serviceImpl.btl"); // service
																					// impl文件
		renderFile(mapperxmlPackage, mapperxmlClass, mapperxmlObj, ".xml", "mapper.btl"); // mapper.xml文件
	}
	
	/**
	 * 生成spring 火车故障系统的代码
	 * 
	 * @param dbService
	 * @throws Exception
	 */
	public static void genSpringTrainTheme(DBService dbService) throws Exception {
		// String packagePrefix = "com.szzt.train.tfds.web.business.";
		String packagePrefix = "com.szzt.train.tfds.monitor.business.";
		String moduleName = "user";

		HashMap tableMap = new HashMap();
		tableMap.put("user", new ModuleVo("user", "用户"));
		tableMap.put("department", new ModuleVo("user", "部门"));
		tableMap.put("menu", new ModuleVo("user", "菜单"));
		tableMap.put("role", new ModuleVo("user", "角色"));
		tableMap.put("roleMenu", new ModuleVo("user", "角色菜单"));
		tableMap.put("group", new ModuleVo("user", "分组"));
		tableMap.put("groupUser", new ModuleVo("user", "分组用户"));
		tableMap.put("viewGroupUser", new ModuleVo("user", "分组用户视图列表"));
		tableMap.put("teamDuty", new ModuleVo("user", "值班分组"));
		
		tableMap.put("dict", new ModuleVo("system", "词典"));
		
		tableMap.put("tfOpTrain", new ModuleVo("train", "列车"));
		tableMap.put("tfOpVehicle", new ModuleVo("train", "机车"));
		
		tableMap.put("viewRoleMenu", new ModuleVo("user", "角色菜单"));
		
		
		tableMap.put("groupWork", new ModuleVo("work", "组工作记录"));
		tableMap.put("userWork", new ModuleVo("work", "用户工作记录"));
		tableMap.put("workPlan", new ModuleVo("work", "工作计划"));
		
		
		tableMap.put("tfCfgFaultClass", new ModuleVo("fault", "故障"));
		tableMap.put("tfDzFault", new ModuleVo("fault", "故障"));
		tableMap.put("tfOpFault", new ModuleVo("fault", "故障"));
		
		
		

		log.info("==========================================");
		log.info("生成 spring trian theme 开始");
		log.info("==========================================");

		for (TableVo curTableVo : dbService.getDbVo().getTableVoList()) {
			ModuleVo curModule = (ModuleVo) tableMap.get(curTableVo.getCamelTableName());
			if( curModule == null ){
				log.error("没有配置表或者视图"+curTableVo.getCamelTableName()+"对应的模块");
				continue;
			}
			if (curModule.isSkip()) {
				log.info("用户要求跳过"+curModule.getModelName()+"模块中的"+curModule.getTableCN()+"表");
			} else {
				SpringTrainTheme fielVo = new SpringTrainTheme(curModule.getTableCN(), curTableVo, curModule.getModelName(), packagePrefix, Config.outDir, Config.theme);
				fielVo.render();
			}
		}
		
		log.info("==========================================");
		log.info("生成 spring trian theme 结束");
		log.info("==========================================");
	}
}
