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

		Class.forName("org.postgresql.Driver");
		Class.forName("com.mysql.jdbc.Driver");

		if (!Config.init()) {
			log.error("Config init failed.");
			return;
		}
		Connection conn = DriverManager.getConnection(global.Config.mysqlUrl, global.Config.mysqlUser, global.Config.mysqlPassword);

		DBVo dbVo = new DBVo();
		dbVo.load(conn);
		conn.close();
		BeetlRenderUtil.render(dbVo);
	}
}
