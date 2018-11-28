package db.util;

import java.io.IOException;
import java.util.HashMap;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import db.model.DBVo;
import global.Config;
import lombok.extern.slf4j.Slf4j;
import tool.StringTool;

@Slf4j
public class BeetlRenderUtil {

	public static String renderFile(DBVo dbVo, String path, String templateFile) throws Exception {
		String text = "";
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(path);//Config.theme);
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

		Template template = gt.getTemplate(templateFile);//("/mysql.md.btl");
		template.binding("dbVo", dbVo);
		text = template.render();
		log.info(text);
		return text;
	}

	public static void renderToFile(DBVo dbVo, String outPath, String outFileName, String templatePath, String templateFile) throws Exception {
		String text = renderFile(dbVo, templatePath, templateFile);
		StringTool.writeStringToFile(outPath+"/"+outFileName, text);
	}
	/**
	 * 渲染文本
	 * @param template
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static String renderText(String template, HashMap map) throws IOException{
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		Template t = gt.getTemplate(template);
		t.binding(map);
		String text = t.render();
		log.info(text);
		return text;
	}

}