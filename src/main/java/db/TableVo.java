package db;

import tool.JsonTool;

import java.util.ArrayList;
import java.util.List;


public class TableVo {

    List<ColumnVo> columnVoList;
    List<PrimaryKeyVo> primaryKeyList;
    DataVo dataVo;
    
    private String tableName;
    private String idColumn;

    public TableVo() {
        columnVoList = new ArrayList<>();
        primaryKeyList = new ArrayList<>();
    }

    public TableVo addColumn(ColumnVo columnVo) {
        columnVoList.add(columnVo);
        return this;
    }

    public TableVo addPrimaryKey( PrimaryKeyVo primaryKeyVo){
        primaryKeyList.add(primaryKeyVo);
        return this;
    }

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public DataVo getDataVo() {
		return dataVo;
	}

	public void setDataVo(DataVo dataVo) {
		this.dataVo = dataVo;
	}

	public List<ColumnVo> getColumnVoList() {
        return columnVoList;
    }

    public void setColumnVoList(List<ColumnVo> columnVoList) {
        this.columnVoList = columnVoList;
    }

    public List<PrimaryKeyVo> getPrimaryKeyList() {
        return primaryKeyList;
    }

    public void setPrimaryKeyList(List<PrimaryKeyVo> primaryKeyList) {
        this.primaryKeyList = primaryKeyList;
    }

    public String getMysqlSchema() {
    	String sql = "\nCREATE TABLE IF NOT EXISTS " + tableName + " ( \n";
        int first = 0;

        // 添加列
        for (ColumnVo curColumnVo : columnVoList) {
            if (first == 0) {
                first = 1;
            }
            else
            {
                sql += ", \n";
            }
            sql += "\t"+curColumnVo.getMysqlColumnText();
            sql += " "+curColumnVo.getMysqlColumnRemarkText();
        }

        // 添加主键
        if ( primaryKeyList.size() > 0 ) {
            sql += ",\n";
            sql += "\tPRIMARY KEY (";
        }
        first = 0;
        for ( PrimaryKeyVo primaryKeyVo: primaryKeyList )
        {
            if (first == 0) {
                first = 1;
            }
            else
            {
                sql += ", ";
            }
            sql += "`"+primaryKeyVo.getOrginColumnName()+"`";
        }
        sql += ")";

        sql += "\n);\r\n";

       
        return sql;
    }
    
    public String getPgsqlSchema() {
        String sql = "\nCREATE TABLE IF NOT EXISTS " + tableName + " ( \n";
        int first = 0;

        // 添加列
        for (ColumnVo curColumnVo : columnVoList) {
            if (first == 0) {
                first = 1;
            }
            else
            {
                sql += ", \n";
            }
            sql += "\t"+curColumnVo.getPgsqlColumnText();
        }

        // 添加主键
        if ( primaryKeyList.size() > 0 ) {
            sql += ",\n";
            sql += "\tPRIMARY KEY (";
        }
        first = 0;
        for ( PrimaryKeyVo primaryKeyVo: primaryKeyList )
        {
            if (first == 0) {
                first = 1;
            }
            else
            {
                sql += ", ";
            }
            sql += primaryKeyVo.getColumnName();
        }
        sql += ")";

        sql += "\n);\r\n";

        for (ColumnVo curColumnVo : columnVoList) {

            sql += curColumnVo.getPgsqlColumnRemarkText()+"\r\n";
        }
        
        return sql;
    }

    public String toJson(){
        return JsonTool.toJSON(this);
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
