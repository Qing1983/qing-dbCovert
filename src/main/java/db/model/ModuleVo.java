package db.model;

import lombok.Data;

@Data
public class ModuleVo {
	String modelName; //表属于的模块名称
	String tableCN; // 表的中文名字
	boolean isSkip=false; // 是否跳过该表或者视图
	
	/**
	 * 
	 * @param modelName 模块名称
	 * @param tableCN 表名
	 */
	public ModuleVo(String modelName, String tableCN )
	{
		this.modelName = modelName;
		this.tableCN = tableCN;
	}
	
	/**
	 * 
	 * @param modelName 模块名称
	 * @param tableCN 表名
	 * @param isSkip 是否跳过
	 */
	public ModuleVo(String modelName, String tableCN, boolean isSkip )
	{
		this.modelName = modelName;
		this.tableCN = tableCN;
		this.isSkip = isSkip;
	}
	
}
