package main;

import db.DBVo;
import global.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);




	public static void main(String[] args) throws Exception {

		Class.forName("org.postgresql.Driver");
		Class.forName("com.mysql.jdbc.Driver");

		if ( !Config.init())
		{
			log.error("Config init failed.");
			return;
		}

		Connection srcConn = DriverManager.getConnection(global.Config.mysqlUrl, global.Config.mysqlUser, global.Config.mysqlPassword);
		//Connection dstConn = DriverManager.getConnection(global.Config.pgsqlUrl, global.Config.pgsqlUser, global.Config.pgsqlPassword);

		DBVo dbVo = new DBVo();

		dbVo.load(srcConn);

		log.info(dbVo.getPgsqlSchema());

		//Statement dstCreateTable = dstConn.createStatement();
		
		/*ResultSet rs = srcConn.getMetaData().getColumns(null, null, "sms_query", "%");
		
		while(rs.next()){
			System.out.println(rs.getString("COLUMN_DEF"));
		}*/
		
		
		//迁移表结构
		//dstCreateTable.executeUpdate(dbVo.getPgsqlSchema());
		
		//迁移数据
		//dbVo.insertTableData(dstConn,srcConn);
		
		srcConn.close();
		//dstConn.close();

	}
}
