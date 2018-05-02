package db.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.model.ColumnVo;
import db.model.DBVo;
import db.model.PrimaryKeyVo;
import db.model.TableVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DBService {
	private String url;
	private String userName;
	private String password;
	private DBVo dbVo;

	public DBService() {
		this.dbVo = new DBVo();
	}

	public DBService(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.dbVo = new DBVo();
	}

	public boolean load() throws SQLException {
		Connection srcConn = DriverManager.getConnection(url, userName, password);

		loadTableList(srcConn);

		srcConn.close();
		return true;

	}

	public void loadTableList(Connection conn) {
		List<TableVo> tableVoList = dbVo.getTableVoList();
		if (tableVoList == null) {
			tableVoList = new ArrayList<>();
			dbVo.setTableVoList(tableVoList);
		} else {
			dbVo.getTableVoList().clear();
		}

		// 获取所有的表
		fillTableVoList(conn);

	}

	/**
	 * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
	 *
	 * @param table
	 *            表名
	 * @return Map集合
	 */
	public void fillTableVoList(Connection conn) {

		DatabaseMetaData dbmd = null;

		try {

			dbmd = conn.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, null, null, new String[] { "TABLE", "VIEW" });

			while (resultSet.next()) {
				TableVo tableVo = new TableVo();
				String tableName = resultSet.getString("TABLE_NAME"); // 表名称
				String tableType = resultSet.getString("TABLE_TYPE"); // 表类型
				String tableRemarks = resultSet.getString("REMARKS"); // 表备注
				log.debug("表名" + tableName + " 注释:" + tableRemarks);

				tableVo.setTableName(tableName);
				tableVo.setLowerCaseTableName(tableName.toLowerCase());
				tableVo.setTalbleRemarks(tableRemarks);
				ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, "%");
				while (rs.next()) {
					ColumnVo columnVo = new ColumnVo(rs, tableVo);
					tableVo.addColumn(columnVo);
				}

				rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);
				while (rs.next()) {
					PrimaryKeyVo primaryKeyVo = new PrimaryKeyVo(rs);
					tableVo.addPrimaryKey(primaryKeyVo);
				}

				dbVo.getTableVoList().add(tableVo);

			}
		} catch (SQLException e) {
			log.error("sql 异常", e);

		} catch (Exception e) {
			log.error("程序 异常", e);

		} finally {

		}
	}

}
