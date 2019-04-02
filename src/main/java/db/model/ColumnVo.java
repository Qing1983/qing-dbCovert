package db.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ColumnVo {
	/*
	 * 1.TABLE_CAT String => table catalog (may be null)
	 */
	public String tableCat;

	/*
	 * 2.TABLE_SCHEM String => table schema (may be null)
	 */
	public String tableSchem;

	/*
	 * 3.TABLE_NAME String => table name (表名称)
	 */
	public String tableName;





	/*
	 * 5.DATA_TYPE int => SQL type from Java.sql.Types(列的数据类型)
	 */
	public int dataType;

	/*
	 * 6.TYPE_NAME String => Data source dependent type name, for a UDT the type
	 * name is fully qualified
	 */
	public String typeName;

	/*
	 * 7.COLUMN_SIZE int => column size.
	 */
	public int columnSize;
	/*
	 * 8.BUFFER_LENGTH is not used.
	 */

	/*
	 * 9.DECIMAL_DIGITS int => the number of fractional digits. Null is returned for
	 * data types where DECIMAL_DIGITS is not applicable.
	 */
	public int decimalDigits;

	/*
	 * 10.NUM_PREC_RADIX int => Radix (typically either 10 or 2)
	 */
	public int numPrecRadix;

	/*
	 * 11.NULLABLE int => is NULL allowed.
	 */
	public int nullAble;

	/*
	 * 12.REMARKS String => comment describing column (may be null)
	 */
	public String remarks;

	/*
	 * 13.COLUMN_DEF String => default value for the column, (may be null)
	 */
	public String columnDef;
	/*
	 * 14.SQL_DATA_TYPE int => unused
	 * 
	 * 15.SQL_DATETIME_SUB int => unused
	 */
	/*
	 * 16.CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the
	 * column
	 */
	public int charOctetLength;
	/*
	 * 17.ORDINAL_POSITION int => index of column in table (starting at 1)
	 */
	public int ordinalPosition;
	/*
	 * 18.IS_NULLABLE String => ISO rules are used to determine the nullability for
	 * a column.
	 */
	public String isNullable;
	/*
	 * 19.SCOPE_CATLOG String => catalog of table that is the scope of a reference
	 * attribute (null if DATA_TYPE isn't REF)
	 */
	// public String scopeCatlog;
	/*
	 * 20.SCOPE_SCHEMA String => schema of table that is the scope of a reference
	 * attribute (null if the DATA_TYPE isn't REF)
	 */
	// public String scopeSchema;
	/*
	 * 21.SCOPE_TABLE String => table name that this the scope of a reference
	 * attribure (null if the DATA_TYPE isn't REF)
	 */
	// public String scopeTable;
	/*
	 * 22.SOURCE_DATA_TYPE short => source type of a distinct type or user-generated
	 * Ref type, SQL type from java.sql.Types 23.IS_AUTOINCREMENT String =>
	 * Indicates whether this column is auto incremented
	 */
	public short scourceDataType;

	/*
	 * 23.IS_AUTOINCREMENT String => Indicates whether this column is auto
	 * incremented
	 */
	public String isAutoIncrement;

	/*
	 * 4.COLUMN_NAME String => column name(列名)
	 */
	public String columnName;

	
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
	
	public String javaType;
	
	public String oracleType;
	
	public String mysqlType;
	
	public String sqlServerType;
	
	public String pgsqlType;
	
	public String mybatisTypeName;
}
