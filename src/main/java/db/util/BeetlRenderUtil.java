package db.util;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeetlRenderUtil {

	public static Template getFileTemplate(String path, String fileName) throws Exception {
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(path);
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

		Template template = gt.getTemplate(fileName);
		return template;

	}

	public static Template getTextTemplate(String templateText) throws IOException {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		Template template = gt.getTemplate(templateText);
		return template;
	}
	
	public static String renderText( String varName, Object varObject, String templdateText) throws IOException
	{
		Template template = getTextTemplate(templdateText);
		template.binding(varName, varObject);
		return template.render();
	}

}