package db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tool.ColumnUtil;

public class DataVo {

	private static final Logger log = LoggerFactory.getLogger(DataVo.class);

	private ResultSet rs;
	private String tableName;

	public DataVo(ResultSet rs, String tableName) {
		this.rs = rs;
		this.tableName = tableName;
	}

	public String getPgsqlColumnText() throws SQLException {
		String insertHead = "INSERT INTO " + tableName + "(";
		String insertTail = ") VALUES (";
		ResultSetMetaData rsmd = rs.getMetaData();
		final int start = /* tableData.length > 1 ? 2 : */1;
		for (int i = start; i <= rsmd.getColumnCount(); i++) {
			if (i > start) {
				insertHead += ", ";
				insertTail += ", ";
			}
			insertHead += ColumnUtil.camelToUnderline(rsmd.getColumnName(i));
			insertTail += "?";
		}
		return insertHead + insertTail + ")";
	}
}
