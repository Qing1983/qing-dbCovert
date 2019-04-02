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
public class SpringHeathTheme {
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
	public SpringHeathTheme(String tableCN, TableVo tableVo, String moduleName, String prefixPackage, String outPath,
			String beetlPath) throws Exception {
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
	public void renderFile(String packageName, String className, String objName, String fileSuffix, String templateName)
			throws Exception {

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
	 * 生成spring 医疗系统的代码
	 * 
	 * @param dbService
	 * @throws Exception
	 */
	public static void genTheme(DBService dbService) throws Exception {
		String packagePrefix = "com.greatwall.health.business.";

		HashMap tableMap = new HashMap();

		// 基本患者信息
		tableMap.put("TBHZJBXX", new ModuleVo("basic", "基本患者信息"));

		// 门诊就诊信息
		tableMap.put("TBMZGH", new ModuleVo("mz", "门诊挂号表"));
		tableMap.put("TBMZJZJL", new ModuleVo("mz", "门诊就诊记录表"));
		tableMap.put("TBMZCFZXX", new ModuleVo("mz", "门诊处方主信息表"));
		tableMap.put("TBMZCFMX", new ModuleVo("mz", "门诊处方明细表"));
		tableMap.put("TBMZCFZXJL", new ModuleVo("mz", "门诊处方医嘱执行记录"));
		tableMap.put("TBMZSFHZ", new ModuleVo("mz", "门诊收费汇总表"));
		tableMap.put("TBMZSFMX", new ModuleVo("mz", "门诊收费明细表"));

		// 住院就诊信息
		tableMap.put("TBZYJZJL", new ModuleVo("zyjz", "住院就诊记录表"));
		tableMap.put("TBZKJL", new ModuleVo("zyjz", "转科记录"));
		tableMap.put("TBZYYZMX", new ModuleVo("zyjz", "住院医嘱明细表"));
		tableMap.put("TBZYYZZXJL", new ModuleVo("zyjz", "住院医嘱执行记录"));
		tableMap.put("TBZYSFMX", new ModuleVo("zyjz", "住院收费明细表"));
		tableMap.put("TBZYSFJS", new ModuleVo("zyjz", "住院收费结算表"));
		tableMap.put("TBHLSCDJL", new ModuleVo("zyjz", "护理三测单记录"));
		tableMap.put("TBEMR_YBHLJL", new ModuleVo("zyjz", "一般护理记录"));
		tableMap.put("TBDZBLSYXX", new ModuleVo("zyjz", "电子病历索引表信息"));
		tableMap.put("TBEMRCYXJ", new ModuleVo("zyjz", "出院小结"));

		// 实验室检验报告
		tableMap.put("TBJYZBG", new ModuleVo("jybg", "实验室检验报告主表"));
		tableMap.put("TBJYXMFZJL", new ModuleVo("jybg", "实验室检验项目分组记录"));
		tableMap.put("TBJYZBJG", new ModuleVo("jybg", "检验项目指标结果表"));
		tableMap.put("TBJYXJJG", new ModuleVo("jybg", "细菌结果"));
		tableMap.put("TBJYYMJG", new ModuleVo("jybg", "药敏结果"));

		// 医学影像检查报告
		tableMap.put("TBJCBG", new ModuleVo("jcbg", "医学影像检查报告表—常见检查报告格式"));
		tableMap.put("TBJCTYBG", new ModuleVo("jcbg", "医学影像检查报告表—通用检查报告格式"));

		// 住院病案首页报告
		tableMap.put("TBBASYZTB", new ModuleVo("basy", "住院病案首页主体表"));
		tableMap.put("TBBASYZDMX", new ModuleVo("basy", "病案首页-诊断明细"));
		tableMap.put("TBBASYSSMX", new ModuleVo("basy", "病案首页-手术操作明细"));

		// 手术/诊断明细表
		tableMap.put("TBSSMX", new ModuleVo("mx", "手术明细表"));
		tableMap.put("TBZDMX", new ModuleVo("mx", "诊断明细表"));

		// 体检业务
		tableMap.put("TBYLTJBGSY", new ModuleVo("body", "体检业务相关数据"));
		tableMap.put("TBYLTJFZBG", new ModuleVo("body", "体检分组报告"));
		tableMap.put("TBYLTJMX", new ModuleVo("body", "体检项目明细表"));

		// 运营监管相关数据
		tableMap.put("TBCWJSXX", new ModuleVo("cw", "财务结算信息"));
		tableMap.put("TBCWSFMX", new ModuleVo("cw", "财务结算-收费明细"));
		tableMap.put("TBCWSFFS", new ModuleVo("cw", "财务结算-收费方式"));

		// 药品信息表
		tableMap.put("TBYPRKXX", new ModuleVo("cw", "药品出入库信息"));
		tableMap.put("TBYPKCXX", new ModuleVo("cw", "药品库存信息"));

		// 本地基础字典数据
		tableMap.put("TBKSXX", new ModuleVo("zd", "医院的科室字典表"));
		tableMap.put("TBYHRYJBXX", new ModuleVo("zd", "医护人员基本信息表"));
		tableMap.put("TBYPZD", new ModuleVo("zd", "药品字典"));
		tableMap.put("TBZLXMZD", new ModuleVo("zd", "诊疗项目字典"));
		tableMap.put("TBJBZD", new ModuleVo("zd", "疾病字典"));

		// 本地字典与标准映射数据
		tableMap.put("TBKSYS", new ModuleVo("ys", "科室字典映射"));
		tableMap.put("TBYPYS", new ModuleVo("ys", "药品字典映射"));
		tableMap.put("TBJBYS", new ModuleVo("ys", "疾病字典映射"));
		tableMap.put("TBZLXMYS", new ModuleVo("ys", "诊疗项目映射"));
		tableMap.put("TBFYLBYS", new ModuleVo("ys", "收费明细费用类别映射"));

		log.info("==========================================");
		log.info("生成 spring heath theme 开始");
		log.info("==========================================");

		for (TableVo curTableVo : dbService.getDbVo().getTableVoList()) {
			ModuleVo curModule = (ModuleVo) tableMap.get(curTableVo.getCamelTableName());
			if (curModule == null) {
				log.error("没有配置表或者视图" + curTableVo.getCamelTableName() + "对应的模块");
			} else if (curModule.isSkip()) {
				log.info("用户要求跳过" + curModule.getModelName() + "模块中的" + curModule.getTableCN() + "表");
			} else {
				SpringHeathTheme fielVo = new SpringHeathTheme(curModule.getTableCN(), curTableVo,
						curModule.getModelName(), packagePrefix, Config.outDir, Config.theme);
				fielVo.render();
			}
		}

		log.info("==========================================");
		log.info("生成 spring heath theme 结束");
		log.info("==========================================");
	}
}
