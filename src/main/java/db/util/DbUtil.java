package db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import db.model.DBVo;

public class DbUtil {
	public static DBVo load(String url, String userName, String password) throws SQLException {
		Connection srcConn = DriverManager.getConnection(url, userName, password);

		DBVo dbVo = new DBVo();

		dbVo.load(srcConn);

		srcConn.close();

		return dbVo;
	}
}
