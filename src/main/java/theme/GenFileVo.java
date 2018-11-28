package theme;

import lombok.Data;

@Data
public class GenFileVo {
	
	String outFileName;
	String beetlFileName;
	
	public GenFileVo(String outFileName, String beetlFileName) {
		this.outFileName = outFileName;
		this.beetlFileName = beetlFileName;
	}


}
