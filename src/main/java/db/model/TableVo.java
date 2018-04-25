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
	private String idColumn;
	private String talbleRemarks;
	private String tableType;

	public TableVo() {
		columnVoList = new ArrayList<>();
		primaryKeyList = new ArrayList<>();
	}

	public TableVo addColumn(ColumnVo columnVo) {
		columnVoList.add(columnVo);
		return this;
	}

	public TableVo addPrimaryKey(PrimaryKeyVo primaryKeyVo) {
		primaryKeyList.add(primaryKeyVo);
		return this;
	}

	public String toJson() {
		return JsonTool.toJSON(this);
	}

}
