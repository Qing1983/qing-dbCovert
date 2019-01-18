package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		SpringTrainTheme.genSpringTrainTheme(dbService);
		//SpringAiBotTheme.genSpringAibotTheme(dbService);
	}
}
