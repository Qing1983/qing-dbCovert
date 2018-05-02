package db.util;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import db.model.DBVo;
import db.model.TableVo;
import global.Config;

public class BeetlRenderUtil {
	public static void render(DBVo dbVo) throws Exception {

		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(Config.theme);
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		for ( TableVo tableVo : dbVo.getTableVoList())
		{
			
			Template template = gt.getTemplate("/mysql_execl.btl");
			template.binding("tableVo",tableVo);
			String str = template.render();
			System.out.println(str);
		}
	}
}