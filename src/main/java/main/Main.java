package main;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.model.DBVo;
import global.Config;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

		Class.forName("org.postgresql.Driver");
		Class.forName("com.mysql.jdbc.Driver");

		if (!Config.init()) {
			log.error("Config init failed.");
			return;
		}

		
		// dstConn.close();

	}
}
