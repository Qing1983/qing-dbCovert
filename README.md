# xk-dbCovert

## 星空-数据库转换工具

# mysql数据库到pgsql的数据库的转换。

# todo
[] 可以配置数据库列表  
[] mysql数据库生成mysql数据库语句, 建表生成注释   
[] mysql数据库生成数据字典   
[] mysql数据库到mysql数据库的迁移   

1.表转换
提供把mysql数据库的表转换为pgsql中的数据库表，并可以在控制台生成建表语句。

2.数据迁移
提供把mysql中的数据迁移到pgsql中,并生成insert语句供使用。

获取数据库指定表的所有列及相应的信息：

访问MySQL,在Connection接口中提供了DatabaseMetaData接口：

提供：getColumns()方法，该方法需要传进4个参数：

第一个是数据库名称，对于MySQL，则对应相应的数据库，对于Oracle来说，则是对应相应的数据库实例，可以不填，也可以直接使用Connection的实例对象中的getCatalog()方法返回的值填充；

第二个是模式，可以理解为数据库的登录名，而对于Oracle也可以理解成对该数据库操作的所有者的登录名。对于Oracle要特别注意，其登陆名必须是大写，不然的话是无法获取到相应的数据，而MySQL则不做强制要求。

第三个是表名称，用于传进想要查找的表

第四个是列类型，为空时，获取表对应的所有列，当不为空的时候获取该值的列的所有信息。

它返回一个ResultSet对象，有23列，详细的显示了表的类型：

TABLE_CAT String => 表类别（可为 null）

TABLE_SCHEM String => 表模式（可为 null）

TABLE_NAME String => 表名称

COLUMN_NAME String => 列名称

DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型

TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的

COLUMN_SIZE int => 列的大小。
BUFFER_LENGTH 未被使用。
DECIMAL_DIGITS int => 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。

NUM_PREC_RADIX int => 基数（通常为 10 或 2）
NULLABLE int => 是否允许使用 NULL。
columnNoNulls - 可能不允许使用 NULL 值
columnNullable - 明确允许使用 NULL 值
columnNullableUnknown - 不知道是否可使用 null
REMARKS String => 描述列的注释（可为 null）

COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）

SQL_DATA_TYPE int => 未使用
SQL_DATETIME_SUB int => 未使用
CHAR_OCTET_LENGTH int => 对于 char 类型，该长度是列中的最大字节数

ORDINAL_POSITION int => 表中的列的索引（从 1 开始）
IS_NULLABLE String => ISO 规则用于确定列是否包括 null。

YES --- 如果参数可以包括 NULL

NO --- 如果参数不可以包括 NULL

空字符串 --- 如果不知道参数是否可以包括 null

SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）

SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）

SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）

SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null）
IS_AUTOINCREMENT String => 指示此列是否自动增加
YES --- 如果该列自动增加

NO --- 如果该列不自动增加

空字符串 --- 如果不能确定该列是否是自动增加参数

可根据需要使用
示例:

DatabaseMetaData metaData = conn.getMetaData();

// ResultSet rs = metaData.getColumns (conn.getCatalog(), "SCOTT", "EMP", "SAL");

ResultSet rs = metaData.getColumns(conn.getCatalog(), "root", "book", "book_id");
while(rs.next()) {     
   System.out.println(rs.getString("COLUMN_NAME"));
}

  
