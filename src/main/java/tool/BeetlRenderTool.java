package tool;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeetlRenderTool {

	/**
	 * 获取文件渲染模版
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Template getFileTemplate(String path, String fileName) throws Exception {
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(path);
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

		Template template = gt.getTemplate(fileName);
		return template;

	}

	/**
	 * 获取文本渲染模版
	 * @param templateText
	 * @return
	 * @throws IOException
	 */
	public static Template getTextTemplate(String templateText) throws IOException {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		Template template = gt.getTemplate(templateText);
		return template;
	}
	
	/**
	 * 单个对象渲染文字
	 * @param varName 需要传入的变量名称
	 * @param varObject 需要传入的对象名称
	 * @param templateText 模版文本
	 * @return
	 * @throws IOException
	 */
	public static String renderText( String varName, Object varObject, String templateText) throws IOException
	{
		Template template = getTextTemplate(templateText);
		template.binding(varName, varObject);
		return template.render();
	}

}