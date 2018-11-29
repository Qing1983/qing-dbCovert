package db.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tool.JsonTool;

@Data
@Slf4j
public class TableVo {

	List<ColumnVo> columnVoList;
	List<PrimaryKeyVo> primaryKeyList;

	private String tableName;
	private String lowerCaseTableName;
	private String talbleRemarks;
	private String tableType;

	/**
	 * 驼峰列名
	 */
	public String camelTableName;
	
	public TableVo() {
		columnVoList = new ArrayList<>();
		primaryKeyList = new ArrayList<>();
	}

	public TableVo addColumn(ColumnVo columnVo) {
		if (columnVo != null) {
			columnVoList.add(columnVo);
		} else {
			log.debug("addColumn失败, columnVo是空的");
		}
		return this;
	}

	public TableVo addPrimaryKey(PrimaryKeyVo primaryKeyVo) {
		if (primaryKeyVo != null) {
			primaryKeyList.add(primaryKeyVo);
		} else {
			log.debug("addPrimaryKey失败, primaryKeyVo是空的");
		}

		return this;
	}

}
