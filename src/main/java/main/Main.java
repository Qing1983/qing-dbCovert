package main;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.model.ModuleVo;
import db.model.TableVo;
import db.service.DBService;
import global.Config;
import theme.SpringTrainTheme;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

		// 加载数据库驱动
		Class.forName("org.postgresql.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 读取配置文件
		if (!Config.init()) {
			log.error("Config init failed.");
			return;
		}

		// 填充数据库表VO

		DBService dbService = new DBService(global.Config.mysqlUrl, global.Config.mysqlDB, global.Config.mysqlUrlParam, global.Config.mysqlUser, global.Config.mysqlPassword);
		dbService.load();

		// 渲染输出
		// BeetlRenderUtil.renderFile(dbService.getDbVo(),Config.theme,
		// "/mysql.md.btl");
		genSpringTrainTheme(dbService);

	}

	/**
	 * 生成spring 火车故障系统的代码
	 * 
	 * @param dbService
	 * @throws Exception
	 */
	public static void genSpringTrainTheme(DBService dbService) throws Exception {
		String packagePrefix = "com.szzt.train.tfds.web.business.";
		String moduleName = "user";

		HashMap tableMap = new HashMap();
		tableMap.put("user", new ModuleVo("user", "用户"));
		tableMap.put("department", new ModuleVo("user", "部门"));
		tableMap.put("dict", new ModuleVo("system", "词典"));
		tableMap.put("menu", new ModuleVo("user", "菜单"));
		tableMap.put("role", new ModuleVo("user", "角色"));
		tableMap.put("roleMenu", new ModuleVo("user", "角色菜单"));
		tableMap.put("group", new ModuleVo("user", "分组"));
		tableMap.put("groupUser", new ModuleVo("user", "分组", true));
		tableMap.put("viewGroupUser", new ModuleVo("user", "分组用户列表"));
		tableMap.put("tfOpTrain", new ModuleVo("train", "列车"));
		tableMap.put("tfOpVehicle", new ModuleVo("train", "机车"));
		tableMap.put("viewRoleMenu", new ModuleVo("user", "角色菜单"));
		tableMap.put("groupWork", new ModuleVo("work", "组工作记录"));
		tableMap.put("userWork", new ModuleVo("work", "用户工作记录"));
		tableMap.put("workPlan", new ModuleVo("work", "工作计划"));
		

		for (TableVo curTableVo : dbService.getDbVo().getTableVoList()) {
			ModuleVo curModule = (ModuleVo) tableMap.get(curTableVo.getCamelTableName());
			if( curModule == null ){
				log.error("没有配置表或者视图"+curTableVo.getCamelTableName()+"对应的模块");
			}
			if (curModule.isSkip()) {
				log.info("用户要求跳过"+curModule.getModelName()+"模块中的"+curModule.getTableCN()+"表");
			} else {
				SpringTrainTheme fielVo = new SpringTrainTheme(curModule.getTableCN(), curTableVo, curModule.getModelName(), packagePrefix, Config.outDir, Config.theme);
				fielVo.render();
			}
		}

	}
}
