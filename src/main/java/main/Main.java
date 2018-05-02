package main;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.model.DBVo;
import db.util.BeetlRenderUtil;
import global.Config;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

		// 加载数据库驱动
		Class.forName("org.postgresql.Driver");
		Class.forName("com.mysql.jdbc.Driver");

		// 读取配置文件
		if (!Config.init()) {
			log.error("Config init failed.");
			return;
		}
		
		// 填充数据库表VO
		Connection conn = DriverManager.getConnection(global.Config.mysqlUrl, global.Config.mysqlUser, global.Config.mysqlPassword);
		DBVo dbVo = new DBVo();
		dbVo.load(conn);
		conn.close();
		
		// 渲染输出
		BeetlRenderUtil.render(dbVo);
	}
}
