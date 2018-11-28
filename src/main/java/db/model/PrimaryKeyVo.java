package db.model;

import java.sql.ResultSet;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.ColumnTool;

@Data
@Slf4j
public class PrimaryKeyVo {

	/*
	 * 1.TABLE_CAT String => table catalog (may be null)
	 */
	public String tableCat;
	/*
	 * 2.TABLE_SCHEM String => table schema (may be null)
	 */
	public String tableSchem;
	/*
	 * 3.TABLE_NAME String => table name
	 */
	public String tableName;
	/*
	 * 4.COLUMN_NAME String => column name
	 */
	public String columnName;
	/*
	 * 5.KEY_SEQ short => sequence number within primary key
	 */
	public short keySeq;

	/*
	 * 6.PK_NAME String => primary key name (may be null)
	 */
	public String pkName;

	// dbmd.getPrimaryKeys(String catalog, String schema, String table)，

	/**
	 * 小写的下划线列名
	 */
	public String lowerCaseUnderLineColumnName;

	/**
	 * 大写的下划线列名
	 */
	public String upperCaseUnderLineColumnName;

	/**
	 * 驼峰列名
	 */
	public String camelColumnName;

	

}
