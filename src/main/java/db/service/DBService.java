package db.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.beetl.sql.core.JavaType;

import db.model.ColumnVo;
import db.model.DBVo;
import db.model.PrimaryKeyVo;
import db.model.TableVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.NameTool;

@Slf4j
@Data
public class DBService {

	private String url;
	private String db;
	private String urlParam;
	private String userName;
	private String password;
	private DBVo dbVo;

	public DBService() {
		this.dbVo = new DBVo();
	}

	public DBService(String url, String db, String urlParam, String userName, String password) {
		this.url = url;
		this.db = db;
		this.urlParam = urlParam;
		this.userName = userName;
		this.password = password;
		this.dbVo = new DBVo();
	}

	public boolean load() throws SQLException {
		Connection conn = DriverManager.getConnection(url + db + urlParam, userName, password);
		loadDatabase(conn);
		conn.close();
		return true;
	}

	public boolean connect () throws SQLException {
		Connection conn = DriverManager.getConnection(url + db + urlParam, userName, password);
		conn.close();
		return true;
	}

	/**
	 * 加载数据库实例schema到model中
	 * 
	 * @param conn
	 */
	public void loadDatabase(Connection conn) {
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
			ResultSet resultSet = dbmd.getTables(this.db, null, null, new String[] { "TABLE", "VIEW" });

			while (resultSet.next()) {
				TableVo tableVo = new TableVo();
				String tableCat = resultSet.getString("TABLE_CAT"); // 表目录
				String tableSchema = resultSet.getString("TABLE_SCHEM");
				String tableName = resultSet.getString("TABLE_NAME"); // 表名称
				String tableType = resultSet.getString("TABLE_TYPE"); // 表类型
				String tableRemarks = resultSet.getString("REMARKS"); // 表备注
				String camelTableName = NameTool.toCamel(tableName);

				log.debug(tableCat + "," + tableSchema + "," + tableType + ",表名" + tableName + " 注释:" + tableRemarks);

				tableVo.setTableName(tableName);
				tableVo.setLowerCaseTableName(tableName.toLowerCase());
				tableVo.setCamelTableName(camelTableName);
				tableVo.setTalbleRemarks(tableRemarks);

				ResultSet rs = conn.getMetaData().getColumns(tableCat, null, tableName, "%");
				while (rs.next()) {
					ColumnVo columnVo = getColumnVo(rs);
					tableVo.addColumn(columnVo);
				}

				rs = conn.getMetaData().getPrimaryKeys(tableCat, null, tableName);
				while (rs.next()) {
					PrimaryKeyVo primaryKeyVo = this.getPrimaryKeyVo(rs);
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

	/**
	 * 根据结果集填充
	 * 
	 * @param rs
	 * @param columnVo
	 */
	public ColumnVo getColumnVo(ResultSet rs) {
		ColumnVo columnVo = new ColumnVo();
		try {
			columnVo.tableCat = rs.getString("TABLE_CAT");
			columnVo.tableSchem = rs.getString("TABLE_SCHEM");
			columnVo.tableName = rs.getString("TABLE_NAME");
			// mysql 驼峰转下划线
			columnVo.columnName = rs.getString("COLUMN_NAME");
			columnVo.lowerCaseUnderLineColumnName = NameTool.toUnderline(columnVo.columnName).toLowerCase();
			columnVo.upperCaseUnderLineColumnName = columnVo.lowerCaseUnderLineColumnName.toUpperCase();
			columnVo.camelColumnName = NameTool.toCamel(columnVo.lowerCaseUnderLineColumnName);

			columnVo.dataType = rs.getInt("DATA_TYPE");
			columnVo.typeName = rs.getString("TYPE_NAME");
			columnVo.columnSize = rs.getInt("COLUMN_SIZE");

			columnVo.decimalDigits = rs.getInt("DECIMAL_DIGITS");
			columnVo.numPrecRadix = rs.getInt("NUM_PREC_RADIX");

			columnVo.nullAble = rs.getInt("NULLABLE");
			columnVo.remarks = rs.getString("REMARKS");
			columnVo.columnDef = rs.getString("COLUMN_DEF");
			columnVo.charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
			columnVo.ordinalPosition = rs.getInt("ORDINAL_POSITION");
			columnVo.isNullable = rs.getString("IS_NULLABLE");
			if (columnVo.isNullable == null) {
				columnVo.isNullable = "";
			}
			columnVo.isNullable = columnVo.isNullable.toUpperCase();
			columnVo.scourceDataType = rs.getShort("SOURCE_DATA_TYPE");
			columnVo.isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
			if (columnVo.isAutoIncrement == null) {
				columnVo.isAutoIncrement = "";
			}
			columnVo.isAutoIncrement = columnVo.isAutoIncrement.toUpperCase();

			// 处理数据类型
			columnVo.javaType = JavaType.getType(columnVo.dataType, columnVo.columnSize, columnVo.decimalDigits);
			if (columnVo.javaType.equals("Double")) {
				columnVo.javaType = "BigDecimal";
			}
			if (columnVo.javaType.equals("Timestamp")) {
				columnVo.javaType = "Date";
			}

		} catch (Exception e) {
			log.error("从表" + columnVo.tableName + "获取列失败", e);
			columnVo = null;
		}
		return columnVo;

	}

	public PrimaryKeyVo getPrimaryKeyVo(ResultSet rs) {
		PrimaryKeyVo primaryKeyVo = new PrimaryKeyVo();
		try {
			primaryKeyVo.tableCat = rs.getString("TABLE_CAT");
			primaryKeyVo.tableSchem = rs.getString("TABLE_SCHEM");
			primaryKeyVo.tableName = rs.getString("TABLE_NAME");
			primaryKeyVo.columnName = rs.getString("COLUMN_NAME");
			primaryKeyVo.lowerCaseUnderLineColumnName = NameTool.toUnderline(primaryKeyVo.columnName).toLowerCase();
			primaryKeyVo.upperCaseUnderLineColumnName = primaryKeyVo.lowerCaseUnderLineColumnName.toUpperCase();
			primaryKeyVo.camelColumnName = NameTool.toCamel(primaryKeyVo.columnName);
			primaryKeyVo.keySeq = rs.getShort("KEY_SEQ");
			primaryKeyVo.pkName = rs.getString("PK_NAME");

		} catch (Exception e) {
			log.error("从" + primaryKeyVo.tableName + "获取主键失败", e);
		}
		return primaryKeyVo;
	}
}
