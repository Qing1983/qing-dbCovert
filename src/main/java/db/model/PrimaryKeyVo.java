package db.model;

import java.sql.ResultSet;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.ColumnUtil;

@Data
@Slf4j
public class PrimaryKeyVo {

	/*
	 * 1.TABLE_CAT String => table catalog (may be null)
	 */
	private String tableCat;
	/*
	 * 2.TABLE_SCHEM String => table schema (may be null)
	 */
	private String tableSchem;
	/*
	 * 3.TABLE_NAME String => table name
	 */
	private String tableName;
	/*
	 * 4.COLUMN_NAME String => column name
	 */
	private String columnName;
	/*
	 * 5.KEY_SEQ short => sequence number within primary key
	 */
	private short keySeq;

	/*
	 * 6.PK_NAME String => primary key name (may be null)
	 */
	private String pkName;

	// dbmd.getPrimaryKeys(String catalog, String schema, String table)，

	/**
	 * 小写的下划线列名
	 */
	private String lowerCaseUnderLineColumnName;

	/**
	 * 大写的下划线列名
	 */
	private String upperCaseUnderLineColumnName;

	/**
	 * 驼峰列名
	 */
	private String camelColumnName;

	public PrimaryKeyVo(ResultSet rs) {
		try {
			this.tableCat = rs.getString("TABLE_CAT");
			this.tableSchem = rs.getString("TABLE_SCHEM");
			this.tableName = rs.getString("TABLE_NAME");
			this.columnName = rs.getString("COLUMN_NAME");
			this.keySeq = rs.getShort("KEY_SEQ");
			this.pkName = rs.getString("PK_NAME");
			this.lowerCaseUnderLineColumnName = ColumnUtil.toUnderline(columnName);
			this.upperCaseUnderLineColumnName = this.lowerCaseUnderLineColumnName.toUpperCase();
		} catch (Exception e) {
			log.error("填充表" + tableName + "列失败", e);
		}
	}

}
