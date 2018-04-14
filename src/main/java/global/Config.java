package global;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	private final static Logger log = LoggerFactory.getLogger(Config.class);




	public static String mysqlUrl = null ;
	public static String mysqlUser = null ;
	public static String mysqlPassword = null;

	public static String pgsqlUrl = null ;
	public static String pgsqlUser = null;
	public static String pgsqlPassword = null;

	public static Boolean useBatch =false;
	
	/**
	 * @desc 初始化加载配置
	 * @return
	 */
	public static boolean init() {

		Configuration config;
		try {
			config = new PropertiesConfiguration("conf/config_"+System.getProperty("env")+".properties");
			mysqlUrl = config.getString("mysqlUrl", "");
			mysqlUser = config.getString("mysqlUser", "");
			mysqlPassword = config.getString("mysqlPassword", "");

			pgsqlUrl = config.getString("pgsqlUrl", "");
			pgsqlUser = config.getString("pgsqlUser", "");
			pgsqlPassword = config.getString("pgsqlPassword", "");

			useBatch = config.getBoolean("usebatch");


			log.info("==========================================");
			log.info("                    CONFIG                ");
			log.info("==========================================");
			log.info("mysqlUrl: " + mysqlUrl);
			log.info("mysqlUser : " + mysqlUser);
			log.info("mysqlPassword : " + mysqlPassword);

			log.info("==========================================");
			log.info("pgsqlUrl : " + pgsqlUrl);
			log.info("pgsqlUser : " + pgsqlUser);
			log.info("pgsqlPassword : " + pgsqlPassword);
			log.info("==========================================");
			log.info("useBatch : " + useBatch);
			log.info("==========================================");



			return true;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
