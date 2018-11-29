package theme;

import java.io.IOException;

import org.beetl.core.Template;

import db.model.TableVo;
import db.util.BeetlRenderUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.FileTool;
import tool.JsonTool;
import tool.StringTool;

@Slf4j
@Data
public class SpringTrainTheme {
	// ========================================================================
	// 基础名字
	// ========================================================================
	TableVo tableVo; // 表对象
	String tableCN=""; // 表的中文名字
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
	String implClass = "${ctx.tableVo.camelTableName}serviceImpl"; // impl 类名
	String implObj = "";

	String controllerPackage = "controller.${ctx.moduleName}"; // controller包名
	String controllerClass = "${ctx.tableVo.camelTableName}Controller"; // controller类名
	String controllerObj = ""; // controller对象名
	
	String mapperxmlPackage = "${ctx.moduleName}"; // mapper包名
	String mapperxmlClass = "${ctx.tableVo.camelTableName}Mapper"; // mapper类名
	String mapperxmlObj="";
	

	// ========================================================================
	// 定义输出文件夹和模版文件夹
	// ========================================================================
	String outPath; // 输出文件夹
	String beetlPath; // 模版文件夹

	public SpringTrainTheme(String tableCN, TableVo tableVo, String moduleName, String prefixPackage, String outPath, String beetlPath) throws Exception {
		this.tableCN = tableCN;
		this.tableVo = tableVo;
		this.moduleName = moduleName;
		this.prefixPackage = prefixPackage;
		this.outPath = outPath;
		this.beetlPath = beetlPath;

	}

	/**
	 * 渲染名称
	 * @throws IOException
	 */
	public void renderName() throws IOException {
		daoPackage = prefixPackage + BeetlRenderUtil.renderText("ctx", (Object) this, daoPackage);
		daoClass = StringTool.upperCaseFirst(BeetlRenderUtil.renderText("ctx", (Object) this, daoClass));
		daoObj = StringTool.lowerCaseFirst(daoClass);
		
		modelPackage = prefixPackage + BeetlRenderUtil.renderText("ctx", (Object) this, modelPackage);
		modelClass = StringTool.upperCaseFirst(BeetlRenderUtil.renderText("ctx", (Object) this, modelClass));
		modelObj = StringTool.lowerCaseFirst(modelClass);

		

		servicePackage = prefixPackage + BeetlRenderUtil.renderText("ctx", (Object) this, servicePackage);
		serviceClass = StringTool.upperCaseFirst(BeetlRenderUtil.renderText("ctx", (Object) this, serviceClass));
		serviceObj = StringTool.lowerCaseFirst(serviceClass);

		implPackage = prefixPackage + BeetlRenderUtil.renderText("ctx", (Object) this, implPackage);
		implClass = StringTool.upperCaseFirst(BeetlRenderUtil.renderText("ctx", (Object) this, implClass));
		implObj = StringTool.lowerCaseFirst(implClass);

		controllerPackage = prefixPackage + BeetlRenderUtil.renderText("ctx", (Object) this, controllerPackage);
		controllerClass = StringTool.upperCaseFirst(BeetlRenderUtil.renderText("ctx", (Object) this, controllerClass));
		controllerObj = StringTool.lowerCaseFirst(controllerClass);
		
		mapperxmlPackage = "mapper." + BeetlRenderUtil.renderText("ctx", (Object) this, mapperxmlPackage);
		mapperxmlClass = StringTool.upperCaseFirst(BeetlRenderUtil.renderText("ctx", (Object) this, mapperxmlClass));
		mapperxmlObj=StringTool.lowerCaseFirst(mapperxmlClass);

		log.info(JsonTool.toJSON(this));
	}

	/**
	 * 渲染文件
	 * @param packageName 包名
	 * @param className 类名
	 * @param objName 对象名
	 * @param fileSuffix 生成文件的后缀名
	 * @param templateName 模版名称
	 * @throws Exception
	 */
	public void renderFile(String packageName, String className, String objName, String fileSuffix,  String templateName) throws Exception {
		
		// 定义输出对应的文件
		String outFilePath = outPath + "/" + packageName.replace('.', '/') + "/" + className + fileSuffix;

		// 根据模版渲染文件
		Template template = BeetlRenderUtil.getFileTemplate(beetlPath,   templateName );
		template.binding("ctx", this);
		String text = template.render();
		log.info("\n"+ text);
		
		// 渲染的内容写入文件
		StringTool.writeStringToFile(outFilePath, text);
	}

	/**
	 * 渲染主题涉及的各个文件
	 * @throws Exception
	 */
	public void render() throws Exception {
		renderName();
		renderFile(controllerPackage, controllerClass, controllerObj, ".java", "controller.btl");
		renderFile(daoPackage, daoClass, daoObj, ".java", "dao.btl");
		renderFile(modelPackage, modelClass, modelObj, ".java", "model.btl");
		renderFile(servicePackage, serviceClass, serviceObj, ".java", "service.btl");
		renderFile(implPackage, implClass, implObj, ".java", "serviceImpl.btl");
		renderFile(mapperxmlPackage, mapperxmlClass, mapperxmlObj, ".xml", "mapper.btl");	
	}
}
