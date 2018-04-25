package db.model;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.ColumnUtil;
import tool.JsonTool;

@Data
@Slf4j
public class ColumnVo {

	private TableVo tableVo;

	public ColumnVo(ResultSet rs, TableVo tableVo) {
		this.tableVo = tableVo;
		try {
			this.tableCat = rs.getString("TABLE_CAT");
			this.tableSchem = rs.getString("TABLE_SCHEM");
			this.tableName = rs.getString("TABLE_NAME");
			// mysql 驼峰转下划线
			this.orginColumnName = rs.getString("COLUMN_NAME");
			this.lowerCaseOrginColumnName = this.orginColumnName.toLowerCase();
			this.columnName = ColumnUtil.toUnderline(this.orginColumnName);

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
	
	private String lowerCaseOrginColumnName;


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

	
	

	public String toJson() {
		return JsonTool.toJSON(this);
	}

}
