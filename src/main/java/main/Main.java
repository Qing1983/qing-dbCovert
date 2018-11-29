package main;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.model.ModuleVo;
import db.model.TableVo;
import db.util.DBService;
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
		String packagePrefix = "com.szzt.train.tfds.web.business.";
		String moduleName = "user";
		
		HashMap tableMap = new HashMap();
		tableMap.put("user", new ModuleVo("user", "用户"));
		tableMap.put("department", new ModuleVo("user", "部门"));
		tableMap.put("dict", new ModuleVo("system", "词典"));
		tableMap.put("menu", new ModuleVo("user", "菜单"));
		tableMap.put("role", new ModuleVo("user", "角色"));
		tableMap.put("roleMenu", new ModuleVo("user", "角色菜单"));
		tableMap.put("tfOpTrain", new ModuleVo("train", "列车"));
		tableMap.put("tfOpVehicle", new ModuleVo("train", "机车"));
		tableMap.put("viewRoleMenu", new ModuleVo("user", "角色菜单"));
		
		for (TableVo curTableVo : dbService.getDbVo().getTableVoList()) {
			// SpringBootThemeVo SpringBootThemeVo = new
			// SpringBootThemeVo("user", curTableVo, "E:/test/");
			ModuleVo curModule = (ModuleVo) tableMap.get(curTableVo.getCamelTableName());
			SpringTrainTheme fielVo = new SpringTrainTheme(curModule.getTableCN(), curTableVo, curModule.getModelName(), packagePrefix, "E:/test", Config.theme);
			fielVo.render();
			
		}

	}
}
