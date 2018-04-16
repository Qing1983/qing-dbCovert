package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tool.ColumnUtil;

import java.sql.ResultSet;

public class PrimaryKeyVo {

	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyVo.class);

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

	private String orginColumnName;
	
	public void setOrginColumnName(String orginColumnName) {
		this.orginColumnName = orginColumnName;
	}

	public PrimaryKeyVo(ResultSet rs) {
		try {
			this.tableCat = rs.getString("TABLE_CAT");
			this.tableSchem = rs.getString("TABLE_SCHEM");
			this.tableName = rs.getString("TABLE_NAME");
			this.orginColumnName = rs.getString("COLUMN_NAME");
			this.columnName = ColumnUtil.camelToUnderline(this.orginColumnName);
			this.keySeq = rs.getShort("KEY_SEQ");
			this.pkName = rs.getString("PK_NAME");
		} catch (Exception e) {
			log.error("填充表" + tableName + "列失败", e);
		}
	}

	public String getTableCat() {
		return tableCat;
	}

	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}

	public String getTableSchem() {
		return tableSchem;
	}

	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public short getKeySeq() {
		return keySeq;
	}

	public void setKeySeq(short keySeq) {
		this.keySeq = keySeq;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getOrginColumnName() {
		return orginColumnName;
	}

	
}
