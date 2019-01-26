package global;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Config {

	public String mysqlUrl = "";
	public String mysqlDB = "";
	public String mysqlUrlParam = "";
	public String mysqlUser = "";
	public String mysqlPassword = "";

	/**
	 * @desc 初始化加载配置
	 * @return
	 */
	public Config(String mysqlUrl, String mysqlDB, String mysqlUrlParam, String mysqlUser, String mysqlPassword) {
		this.mysqlUrl = mysqlUrl == null ? "" : mysqlUrl;
		this.mysqlDB = mysqlDB == null ? "" : mysqlDB;
		this.mysqlUrlParam = mysqlUrlParam == null ? "" : mysqlUrlParam;
		this.mysqlUser = mysqlUser == null ? "" : mysqlUser;
		this.mysqlPassword = mysqlPassword == null ? "" : mysqlPassword;
	}

	public void print () {
		log.info("==========================================");
		log.info("                    CONFIG                ");
		log.info("==========================================");
		log.info("mysqlUrl: " + mysqlUrl);
		log.info("mysqlDB: " + mysqlDB);
		log.info("mysqlUrlParam: " + mysqlUrlParam);
		log.info("mysqlUser : " + mysqlUser);
		log.info("mysqlPassword : " + mysqlPassword);
	}

	public Config () {}


}
