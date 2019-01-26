package methods;

import com.alibaba.fastjson.JSON;
import db.service.DBService;
import global.Config;

import java.sql.SQLException;

public class DBMethods {

    public static DBService getDbInfo (String params) throws ClassNotFoundException, SQLException {
        // 初始化参数
        DBService dbService = getDbService(params);
        dbService.load();
        return dbService;
    }

    public static void testDb (String params) throws SQLException, ClassNotFoundException {
        DBService dbService = getDbService(params);
        dbService.connect();
    }

    public static DBService getDbService (String params) throws ClassNotFoundException, SQLException  {
        Config config = JSON.parseObject(params, Config.class);
        // 加载数据库驱动
        Class.forName("org.postgresql.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");
        DBService dbService = new DBService(config.mysqlUrl, config.mysqlDB, config.mysqlUrlParam, config.mysqlUser, config.mysqlPassword);
        return dbService;
    }

}
