package global;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {

	public static String mysqlUrl = null;
	public static String mysqlDB = null;
	public static String mysqlUrlParam = null;
	public static String mysqlUser = null;
	public static String mysqlPassword = null;

	// oralce 配置文件
	public static String oracleUrl = null;
	public static String oracleUsername = null;
	public static String oraclePassword = null;
	public static int oracleInitialSize = 5;
	public static int oracleMinIdle = 5;
	public static int oracleMaxActive = 50;

	public static String theme = null;
	public static String outDir = null;

	/**
	 * @desc 初始化加载配置
	 * @return
	 */
	public static boolean init() {

		Configuration config;
		try {
			String env = System.getProperty("env");
			if (env == null) {
				log.info("没有配置环境，使用本地配置local");
				env = "local";
			}
			config = new PropertiesConfiguration("conf/config_" + env + ".properties");
			mysqlUrl = config.getString("mysqlUrl", "");
			mysqlDB = config.getString("mysqlDB", "");
			mysqlUrlParam = config.getString("mysqlUrlParam", "");
			mysqlUser = config.getString("mysqlUser", "");
			mysqlPassword = config.getString("mysqlPassword", "");
			
			
			oracleUrl = config.getString("oracle-url", "jdbc:oracle:thin:@127.0.0.1:1521/keep");
			oracleUsername = config.getString("oracle-username", "EXCHANGE");
			oraclePassword = config.getString("oracle-password", "keep" );
			oracleInitialSize = config.getInt("oracle-initial-size", 5 );
			oracleMinIdle = config.getInt("oracle-min-idle", 5 );
			oracleMaxActive = config.getInt("oracle-max-active", 50 );
			

			theme = config.getString("theme", "");
			outDir = config.getString("outDir", "");

			log.info("==========================================");
			log.info("                    CONFIG                ");
			log.info("==========================================");
			log.info("mysqlUrl: " + mysqlUrl);
			log.info("mysqlDB: " + mysqlDB);
			log.info("mysqlUrlParam: " + mysqlUrlParam);
			log.info("mysqlUser : " + mysqlUser);
			log.info("mysqlPassword : " + mysqlPassword);
			log.info("theme : " + theme);
			log.info("outDir : " + outDir);

			return true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
