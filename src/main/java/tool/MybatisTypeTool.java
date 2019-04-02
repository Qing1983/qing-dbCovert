package tool;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class MybatisTypeTool {
	private static Map<Integer, String> typeMap = null;
	static {
		Map<Integer, String> _typeMap = new HashMap<Integer, String>();
		_typeMap.put(Types.ARRAY, "ARRAY");
		_typeMap.put(Types.BIT, "BIT");
		_typeMap.put(Types.TINYINT, "TINYINT");
		_typeMap.put(Types.SMALLINT, "SMALLINT");
		_typeMap.put(Types.INTEGER, "INTEGER");
		_typeMap.put(Types.BIGINT, "BIGINT");

		_typeMap.put(Types.FLOAT, "FLOAT");
		_typeMap.put(Types.REAL, "REAL");
		_typeMap.put(Types.DOUBLE, "DOUBLE");
		_typeMap.put(Types.NUMERIC, "NUMERIC");
		_typeMap.put(Types.DECIMAL, "DECIMAL");

		_typeMap.put(Types.CHAR, "CHAR");
		_typeMap.put(Types.VARCHAR, "VARCHAR");
		_typeMap.put(Types.LONGVARCHAR, "LONGVARCHAR");
		_typeMap.put(Types.DATE, "DATE");

		_typeMap.put(Types.TIME, "TIME");
		_typeMap.put(Types.TIMESTAMP, "TIMESTAMP");
		_typeMap.put(Types.BINARY, "BINARY");

		_typeMap.put(Types.VARBINARY, "VARBINARY");
		_typeMap.put(Types.LONGVARBINARY, "LONGVARBINARY");
		_typeMap.put(Types.NULL, "NULL");

		_typeMap.put(Types.OTHER, "OTHER");

		_typeMap.put(Types.BLOB, "BLOB");
		_typeMap.put(Types.CLOB, "CLOB");
		_typeMap.put(Types.BOOLEAN, "BOOLEAN");
		_typeMap.put(-10, "CURSOR");// Oracle
		_typeMap.put(Integer.MIN_VALUE + 1000, "UNDEFINED");
		_typeMap.put(Types.NVARCHAR, "NVARCHAR");// JDK6
		_typeMap.put(Types.NCHAR, "NCHAR");// JDK6
		_typeMap.put(Types.NCLOB, "NCLOB");// JDK6
		_typeMap.put(Types.STRUCT, "STRUCT");
		_typeMap.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
		_typeMap.put(Types.DISTINCT, "DISTINCT");
		_typeMap.put(Types.REF, "REF");
		_typeMap.put(Types.DATALINK, "DATALINK");
		_typeMap.put(Types.ROWID, "ROWID");
		_typeMap.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
		_typeMap.put(Types.SQLXML, "SQLXML");
		_typeMap.put(Types.LONGNVARCHAR, "LONGNVARCHAR");// JDK6
		_typeMap.put(-155, "DATETIMEOFFSET");

		typeMap = _typeMap;
	}

	public static String getCode(int code) {
		return typeMap.get(code);
	}

	

}
