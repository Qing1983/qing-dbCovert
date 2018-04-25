package db.model;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DBVo {



    private List<TableVo> tableVoList = null;

    public DBVo()
    {
        tableVoList = new ArrayList<>();
    }

    public void load(Connection conn)
    {
        if ( tableVoList == null ) {
            tableVoList = new ArrayList<>();
        }
        else
        {
            tableVoList.clear();
        }

        // 获取所有的表
        fillTableVoList(conn);

    }
    
    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     *
     * @param table 表名
     * @return Map集合
     */
    public  void fillTableVoList(Connection conn) {


        DatabaseMetaData dbmd = null;

        try {

            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, null, new String[]{"TABLE","VIEW"});

            while (resultSet.next()) {
                TableVo tableVo = new TableVo();
                String tableName = resultSet.getString("TABLE_NAME");  // 表名称
                String tableType = resultSet.getString("TABLE_TYPE");  // 表类型    
                String tableRemarks = resultSet.getString("REMARKS");       // 表备注 
                log.debug(tableName);


                tableVo.setTableName(tableName);
                tableVo.setLowerCaseTableName(tableName.toLowerCase());
                tableVo.setTalbleRemarks(tableRemarks);
                ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, "%");
                while (rs.next()) {
                    ColumnVo columnVo = new ColumnVo(rs,tableVo);
                    tableVo.addColumn(columnVo);
                }

                rs = conn.getMetaData().getPrimaryKeys(null, null, tableName );
                while (rs.next()) {
                    PrimaryKeyVo primaryKeyVo = new PrimaryKeyVo(rs);
                    tableVo.addPrimaryKey(primaryKeyVo);
                }
                
              
                

                tableVoList.add(tableVo);

            }
        } catch (SQLException e) {
            log.error("sql 异常",e);

        } catch (Exception e) {
            log.error("程序 异常",e);

        } finally {

        }


    }

    
  
    
    

    /*

    public void transferColumn(int type, int position, ResultSet mysqlRs, String colName, PreparedStatement pgsqlPs) throws SQLException {
        switch (type) {
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.REAL:
            case Types.FLOAT: {
                double value = mysqlRs.getDouble(colName);

                if (mysqlRs.wasNull()) {
                    pgsqlPs.setNull(position, Types.DECIMAL);
                } else {
                    pgsqlPs.setDouble(position, value);
                }
                break;
            }
            case Types.BIT: {
                boolean value = mysqlRs.getBoolean(colName);
                if (mysqlRs.wasNull()) {
                    pgsqlPs.setNull(position, Types.BIT);
                } else {
                    pgsqlPs.setBoolean(position, value);
                }
                break;
            }
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.TINYINT: {
                int value = mysqlRs.getInt(colName);
                if (mysqlRs.wasNull()) {
                    pgsqlPs.setNull(position, Types.INTEGER);
                } else {
                    pgsqlPs.setInt(position, value);
                }
                break;
            }
            case Types.BIGINT: {
                long value = mysqlRs.getLong(colName);
                if (mysqlRs.wasNull()) {
                    pgsqlPs.setNull(position, Types.BIGINT);
                } else {
                    pgsqlPs.setLong(position, value);
                }
                break;
            }
            case Types.DATE:
            case Types.TIMESTAMP:
            case Types.TIME: {
                try {
                    Timestamp value = mysqlRs.getTimestamp(colName);
                    if (mysqlRs.wasNull()) {
                        pgsqlPs.setNull(position, Types.TIMESTAMP);
                    } else {
                        pgsqlPs.setTimestamp(position, value);
                    }
                } catch (SQLException e) {
                    String message = e.getMessage();
                    if (message.startsWith("Value '") && message.endsWith("' can not be represented as java.sql.Timestamp")) {
                        pgsqlPs.setTimestamp(position, new Timestamp(0L));
                    } else {
                        throw e;
                    }
                }
                break;
            }
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.CHAR: {
                String value = mysqlRs.getString(colName);
                if (value != null) {
                    try {
                        String removeChars = new String(new byte[]{(byte) 0x00}, "utf-8");
                        //Mysql sometimes has an initial 0x00 byte which is not an allowed utf-8 character. Strip it
                        value = value.replace(removeChars, "");
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(DataMigrator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (mysqlRs.wasNull()) {
                    pgsqlPs.setNull(position, Types.VARCHAR);
                } else {
                    pgsqlPs.setString(position, value);
                }
                break;
            }
            case Types.LONGVARBINARY:
            case Types.BLOB:
            case Types.VARBINARY:
            case Types.BINARY: {
                byte[] value = mysqlRs.getBytes(colName);
                if (mysqlRs.wasNull()) {
                    pgsqlPs.setNull(position, type);
                } else {
                    pgsqlPs.setBytes(position, value);
                }
                break;
            }
            default:
                throw new SQLException("Don't know how to handle type of " + type + " " + mysqlRs.getMetaData().getColumnTypeName(position));
        }
    }
    */


}
