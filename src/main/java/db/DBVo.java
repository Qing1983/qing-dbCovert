package db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DBVo {

    private static final Logger log = LoggerFactory.getLogger(DBVo.class);

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
     * 迁移数据
     * @param conn
     */
    public void insertTableData(Connection dstConn,Connection srcConn){
    	for(TableVo tableVo : tableVoList){
    		try {
    			long t0 = System.currentTimeMillis();
    			
    			//查询全表数据
    			String query = "SELECT * FROM " + tableVo.getTableName();
    			if (tableVo.getIdColumn() != null) {
    				/*
    				 * We assume the serial never overflows and starts again filling the
    				 * holes...
    				 */
    				query += " ORDER BY " + tableVo.getIdColumn() + " ASC";
    			}
                ResultSet rs = srcConn.createStatement().executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                
                //声明变量
                int count = 0;
                long lastIdValue = -1;
                final int start = /* tableData.length > 1 ? 2 : */1;
                
    			//获取该表的insert语句
                if(tableVo.getDataVo() == null){
                	continue;
                }
				String insert = tableVo.getDataVo().getPgsqlColumnText();
				
				log.info(insert);
				
				//处理插入值
				PreparedStatement pst = dstConn.prepareStatement(insert);
				while (rs.next()) {
					for (int i = start; i <= rsmd.getColumnCount(); i++) {
						int j = /* tableData.length > 1 ? i - 1 : */i;
						boolean wasNull = false;
						switch (rsmd.getColumnType(i)) {
							case Types.SMALLINT:
							case Types.TINYINT:
							case Types.INTEGER: {
								Integer _int = rs.getInt(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setInt(j, _int);
									/*
									 * If this is the id column, store its value as we'll need to
									 * update the sequence.
									 */
									if (rsmd.getColumnName(i).equals(tableVo.getIdColumn()) && _int > lastIdValue) {
										lastIdValue = _int;
									}
								}
								break;
							}
							case Types.BIT: {
								Boolean _ts = rs.getBoolean(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setBoolean(j, _ts);
								}
								
								break;
							}
							case Types.DATE: {
								Date _ts = rs.getDate(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setDate(j, _ts);
								}
								break;
							}
							case Types.TIMESTAMP: {
								Timestamp _ts = rs.getTimestamp(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setTimestamp(j, _ts);
								}
								break;
							}
							case Types.BOOLEAN: {
								boolean _b = rs.getBoolean(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setBoolean(j, _b);
								}
								break;
							}
							case Types.NUMERIC:
							case Types.DECIMAL: {
								BigDecimal _b = rs.getBigDecimal(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setBigDecimal(j, _b);
								}
								break;
							}
							case Types.CHAR: 
							case Types.VARCHAR:
							case Types.LONGVARCHAR:{
								String _str = rs.getString(i);
								wasNull = rs.wasNull();
								if (!wasNull) {
									pst.setString(j, _str);
								}
								break;
							}
							default: {
								log.info("Unhandled type: " + rsmd.getColumnTypeName(i) + " " + rsmd.getColumnType(i));
								break;
							}
						} // end-switch.
						if (wasNull) {
							pst.setNull(j, rsmd.getColumnType(i));
						}
					} // end-for: colonne.
					//pst.executeUpdate();
					try {
						pst.addBatch();
						count++;
						if (count % 500 == 0) {
							pst.executeBatch();
						}
					} catch (SQLException sqlex) {
						log.error("ERROR: " + sqlex.getMessage());
						SQLException e = sqlex;
						while (e.getNextException() != null) {
							log.error("CAUSE: " + e.getNextException().getMessage());
							e = e.getNextException();
						}
					}
				}
				/* Final execution */
				try {
					pst.executeBatch();
				} catch (SQLException sqlex) {
					log.error("ERROR: " + sqlex.getMessage());
					SQLException e = sqlex;
					while (e.getNextException() != null) {
						log.error("CAUSE: " + e.getNextException().getMessage(),e);
						e = e.getNextException();
					}
				}
				
				long t1 = System.currentTimeMillis();
				log.info("[" + tableVo.getTableName() + "] " + (t1 - t0) + " ms.");
				/* Update the sequence */
				PreparedStatement dstSequence = dstConn.prepareStatement("SELECT setval(?, ?)");
				if (tableVo.getIdColumn() != null && lastIdValue != -1) {
					System.out.println("[" + tableVo.getTableName() + "] setting sequence to " + lastIdValue);
					dstSequence.setString(1, tableVo.getTableName() + "_" + tableVo.getIdColumn() + "_seq");
					dstSequence.setLong(2, lastIdValue);
					dstSequence.execute();
				}
			} catch (SQLException e) {
	            log.error("sql 异常",e);
	        } catch (Exception e) {
	            log.error("程序 异常",e);
	        } 
    	}
    	
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
            ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});

            while (resultSet.next()) {
                TableVo tableVo = new TableVo();
                String tableName = resultSet.getString("TABLE_NAME");  // 表名称
                String tableType = resultSet.getString("TABLE_TYPE");  // 表类型    
                String tableRemarks = resultSet.getString("REMARKS");       // 表备注 
                log.debug(tableName);


                tableVo.setTableName(tableName);
                tableVo.setLowerCaseTableName(tableName.toLowerCase());
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
                
                //DataVo处理
    			String query = "SELECT * FROM " + tableName + " LIMIT 0,1";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                rs = preparedStatement.executeQuery();
                while(rs.next()){
                	DataVo dataVo = new DataVo(rs,tableName);
                	tableVo.setDataVo(dataVo);
                }
                
                log.debug(tableVo.getPgsqlSchema());
                tableVoList.add(tableVo);

            }
        } catch (SQLException e) {
            log.error("sql 异常",e);

        } catch (Exception e) {
            log.error("程序 异常",e);

        } finally {

        }


    }

    /**
     * 获取mysql schema语句
     * @return
     */
    public String getMysqlSchema() {
    	 String mysqlSchemaSql = "";

         for (TableVo tableVo : tableVoList)
         {
        	 mysqlSchemaSql+=tableVo.getMysqlSchema();
         }
         return mysqlSchemaSql;
    }
    
    /**
     * 获取pgsql schema语句
     * @return
     */
    public String getPgsqlSchema(){

        String pgsqlSchemaSql = "";

        for (TableVo tableVo : tableVoList)
        {
            pgsqlSchemaSql+=tableVo.getPgsqlSchema();
        }
        return pgsqlSchemaSql;
    }
    
    
    public String getPgsqlDataSchema(){
    	return null;
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
