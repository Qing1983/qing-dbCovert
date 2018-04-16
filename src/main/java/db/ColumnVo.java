package db;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tool.ColumnUtil;
import tool.JsonTool;

public class ColumnVo {
	private static final Logger log = LoggerFactory.getLogger(ColumnVo.class);

	private TableVo tableVo;

	public ColumnVo(ResultSet rs, TableVo tableVo) {
		this.tableVo = tableVo;
		try {
			this.tableCat = rs.getString("TABLE_CAT");
			this.tableSchem = rs.getString("TABLE_SCHEM");
			this.tableName = rs.getString("TABLE_NAME");
			// mysql 驼峰转下划线
			this.orginColumnName = rs.getString("COLUMN_NAME");
			this.columnName = ColumnUtil.camelToUnderline(this.orginColumnName);

			this.dataType = rs.getInt("DATA_TYPE");
			this.typeName = rs.getString("TYPE_NAME");
			this.columnSize = rs.getInt("COLUMN_SIZE");

			this.decimalDigits = rs.getInt("DECIMAL_DIGITS");
			this.numPrecRadix = rs.getInt("NUM_PREC_RADIX");

			this.nullAble = rs.getInt("NULLABLE");
			this.remarks = rs.getString("REMARKS");
			this.columnDef = rs.getString("COLUMN_DEF");
			this.charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
			this.ordinalPosition = rs.getInt("ORDINAL_POSITION");
			this.isNullable = rs.getString("IS_NULLABLE");
			if (this.isNullable == null) {
				this.isNullable = "";
			}
			this.isNullable = this.isNullable.toUpperCase();
			this.scourceDataType = rs.getShort("SOURCE_DATA_TYPE");
			this.isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
			if (this.isAutoIncrement == null) {
				this.isAutoIncrement = "";
			}
			this.isAutoIncrement = this.isAutoIncrement.toUpperCase();
		} catch (Exception e) {
			log.error("填充表" + tableName + "列失败", e);
		}

	}

	public TableVo getTableVo() {
		return tableVo;
	}

	public void setTableVo(TableVo tableVo) {
		this.tableVo = tableVo;
	}

	public String getOrginColumnName() {
		return orginColumnName;
	}

	public void setOrginColumnName(String orginColumnName) {
		this.orginColumnName = orginColumnName;
	}

	/*
	 * 1.TABLE_CAT String => table catalog (may be null)
	 */
	private String tableCat;

	/*
	 * 2.TABLE_SCHEM String => table schema (may be null)
	 */
	private String tableSchem;

	/*
	 * 3.TABLE_NAME String => table name (表名称)
	 */
	private String tableName;

	/*
	 * 4.COLUMN_NAME String => column name(列名)
	 */
	private String columnName;

	private String orginColumnName;
	/*
	 * 5.DATA_TYPE int => SQL type from Java.sql.Types(列的数据类型)
	 */
	private int dataType;

	/*
	 * 6.TYPE_NAME String => Data source dependent type name, for a UDT the type
	 * name is fully qualified
	 */
	private String typeName;

	/*
	 * 7.COLUMN_SIZE int => column size.
	 */
	private int columnSize;
	/*
	 * 8.BUFFER_LENGTH is not used.
	 */

	/*
	 * 9.DECIMAL_DIGITS int => the number of fractional digits. Null is returned for
	 * data types where DECIMAL_DIGITS is not applicable.
	 */
	private int decimalDigits;

	/*
	 * 10.NUM_PREC_RADIX int => Radix (typically either 10 or 2)
	 */
	private int numPrecRadix;

	/*
	 * 11.NULLABLE int => is NULL allowed.
	 */
	private int nullAble;

	/*
	 * 12.REMARKS String => comment describing column (may be null)
	 */
	private String remarks;

	/*
	 * 13.COLUMN_DEF String => default value for the column, (may be null)
	 */
	private String columnDef;
	/*
	 * 14.SQL_DATA_TYPE int => unused
	 * 
	 * 15.SQL_DATETIME_SUB int => unused
	 */
	/*
	 * 16.CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the
	 * column
	 */
	private int charOctetLength;
	/*
	 * 17.ORDINAL_POSITION int => index of column in table (starting at 1)
	 */
	private int ordinalPosition;
	/*
	 * 18.IS_NULLABLE String => ISO rules are used to determine the nullability for
	 * a column.
	 */
	private String isNullable;
	/*
	 * 19.SCOPE_CATLOG String => catalog of table that is the scope of a reference
	 * attribute (null if DATA_TYPE isn't REF)
	 */
	// private String scopeCatlog;
	/*
	 * 20.SCOPE_SCHEMA String => schema of table that is the scope of a reference
	 * attribute (null if the DATA_TYPE isn't REF)
	 */
	// private String scopeSchema;
	/*
	 * 21.SCOPE_TABLE String => table name that this the scope of a reference
	 * attribure (null if the DATA_TYPE isn't REF)
	 */
	// private String scopeTable;
	/*
	 * 22.SOURCE_DATA_TYPE short => source type of a distinct type or user-generated
	 * Ref type, SQL type from java.sql.Types 23.IS_AUTOINCREMENT String =>
	 * Indicates whether this column is auto incremented
	 */
	private short scourceDataType;

	/*
	 * 23.IS_AUTOINCREMENT String => Indicates whether this column is auto
	 * incremented
	 */
	private String isAutoIncrement;

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

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public int getNumPrecRadix() {
		return numPrecRadix;
	}

	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}

	public int getNullAble() {
		return nullAble;
	}

	public void setNullAble(int nullAble) {
		this.nullAble = nullAble;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public short getScourceDataType() {
		return scourceDataType;
	}

	public void setScourceDataType(short scourceDataType) {
		this.scourceDataType = scourceDataType;
	}

	public String getIsAutoIncrement() {
		return isAutoIncrement;
	}

	public void setIsAutoIncrement(String isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public String toJson() {
		return JsonTool.toJSON(this);
	}

	public String getPgsqlColumnRemarkText() {
		return "COMMENT ON COLUMN " + this.tableName + "." + this.columnName + " IS \'" + this.remarks + "\';";
	}

	public String getPgsqlColumnText() {
		typeName = typeName.toUpperCase();

		String pgsqlTypeName = typeName;
		String pgsqlColumText = this.columnName + " ";
		if ("YES".compareTo(this.isAutoIncrement) == 0) {
			tableVo.setIdColumn(this.columnName);
		}
		switch (typeName) {

		case "TINYINT":
			if ("YES".compareTo(this.isAutoIncrement) == 0) {
				pgsqlTypeName = "SMALLSERIAL";
				pgsqlColumText += pgsqlTypeName;

			} else {
				pgsqlTypeName = "SMALLINT";
				pgsqlColumText += pgsqlTypeName;
			}
			break;

		case "INT":
		case "INTEGER":
		case "INT UNSIGNED":

			if ("YES".compareTo(this.isAutoIncrement) == 0) {
				pgsqlTypeName = "SERIAL";
				pgsqlColumText += pgsqlTypeName;
			} else {
				pgsqlTypeName = "INTEGER";
				pgsqlColumText += pgsqlTypeName;
			}
			break;

		case "BIGINT":
			if ("YES".compareTo(this.isAutoIncrement) == 0) {
				pgsqlTypeName = "BIGSERIAL";
				pgsqlColumText += pgsqlTypeName;

			} else {
				pgsqlTypeName = "BIGINT";
				pgsqlColumText += pgsqlTypeName;
			}
			break;
		case "BIT":
			// MYSQL的BIT要对应PGSQL的boolean!
			pgsqlTypeName = "BOOLEAN";
			pgsqlColumText += pgsqlTypeName;
			break;

		case "VARCHAR":
		case "VARCHAR2":
		case "CHAR":
			pgsqlTypeName = "VARCHAR";
			pgsqlColumText += pgsqlTypeName + "(" + this.charOctetLength + ")";
			break;

		case "NUMBER":
		case "DECIMAL":
			pgsqlTypeName = "DECIMAL";
			pgsqlColumText += pgsqlTypeName;
			break;

		case "DATETIME":
		case "TIMESTAMP":
			pgsqlTypeName = "TIMESTAMP";
			pgsqlColumText += pgsqlTypeName;
			break;

		case "DATE":
			pgsqlTypeName = "DATE";
			pgsqlColumText += pgsqlTypeName;
			break;

		default:
			pgsqlColumText += pgsqlTypeName;
			break;
		}

		if ("NO".compareTo(this.isNullable) == 0) {
			pgsqlColumText += " NOT NULL";
		}
		if (this.columnDef != null) {
			if (typeName.equals("BIT") && columnDef.equals("1")) {
				pgsqlColumText += " DEFAULT TRUE";
			} else if (typeName.equals("BIT") && columnDef.equals("0")) {
				pgsqlColumText += " DEFAULT FALSE";
			} else if (pgsqlTypeName.equals("VARCHAR")) {
				pgsqlColumText += " DEFAULT '" + this.columnDef + "'";
			} else {
				pgsqlColumText += " DEFAULT " + this.columnDef;
			}
		}
		return pgsqlColumText;
	}

	public String getMysqlColumnText() {
		typeName = typeName.toUpperCase();

		String mysqlTypeName = typeName;
		String mysqlColumText = "`"+this.orginColumnName + "` ";
		if ("YES".compareTo(this.isAutoIncrement) == 0) {
			tableVo.setIdColumn(this.columnName);
		}
		switch (typeName) {

		case "TINYINT":
			if ("YES".compareTo(this.isAutoIncrement) == 0) {
				mysqlTypeName = "SMALLSERIAL";
				mysqlColumText += mysqlTypeName;

			} else {
				mysqlTypeName = "SMALLINT";
				mysqlColumText += mysqlTypeName+ "(" + this.decimalDigits + ")";
			}
			break;

		case "INT":
		case "INTEGER":
		case "INT UNSIGNED":
		case "BIGINT":
			if ("YES".compareTo(this.isAutoIncrement) == 0) {
				mysqlTypeName = "INTEGER";
				mysqlColumText += mysqlTypeName + "(" + this.decimalDigits + ")";
				mysqlTypeName = "AUTO_INCREMENT";
			} else {
				mysqlTypeName = "INTEGER";
				mysqlColumText += mysqlTypeName + "(" + this.decimalDigits + ")";
			}
			break;

		case "BIT":
			// MYSQL的BIT要对应PGSQL的boolean!
			mysqlColumText += mysqlTypeName;
			break;

		case "VARCHAR":
		case "VARCHAR2":
		case "CHAR":
			mysqlTypeName = "VARCHAR";
			mysqlColumText += mysqlTypeName + "(" + this.charOctetLength + ")";
			break;

		case "NUMBER":
		case "DECIMAL":
			mysqlTypeName = "DECIMAL";
			mysqlColumText += mysqlTypeName;
			break;

		case "DATETIME":
		case "TIMESTAMP":
			mysqlTypeName = "TIMESTAMP";
			mysqlColumText += mysqlTypeName;
			break;

		case "DATE":
			mysqlTypeName = "DATE";
			mysqlColumText += mysqlTypeName;
			break;

		default:
			mysqlColumText += mysqlTypeName;
			break;
		}


		if ("NO".compareTo(this.isNullable) == 0) {
			mysqlColumText += " NOT NULL";
		}
		if (this.columnDef != null) {
			if (typeName.equals("BIT") && columnDef.equals("1")) {
				mysqlColumText += " DEFAULT TRUE";
			} else if (typeName.equals("BIT") && columnDef.equals("0")) {
				mysqlColumText += " DEFAULT FALSE";
			} else if (mysqlTypeName.equals("VARCHAR")) {
				mysqlColumText += " DEFAULT '" + this.columnDef + "'";
			} else {
				mysqlColumText += " DEFAULT " + this.columnDef;
			}
		}
		return mysqlColumText;
	}

	public String getMysqlColumnRemarkText() {
		return "COMMENT `" + this.remarks +"`";
	}
}
