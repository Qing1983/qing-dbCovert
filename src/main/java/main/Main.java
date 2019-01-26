package main;

import methods.MethodsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import tool.ResultTool;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		try {
			String cmdName = args[0];
			String params = new String(new BASE64Decoder().decodeBuffer(args[1]));
			Object result = MethodsHandler.handler(cmdName, params);
			ResultTool.success(result);
		} catch (Exception e) {
			ResultTool.error(e.getMessage());
		}
	}

}
