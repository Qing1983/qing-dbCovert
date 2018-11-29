package db.model;

import lombok.Data;

@Data
public class ModuleVo {
	String modelName; //表属于的模块名称
	String tableCN; // 表的中文名字
	
	public ModuleVo(String modelName, String tableCN )
	{
		this.modelName = modelName;
		this.tableCN = tableCN;
	}
}
