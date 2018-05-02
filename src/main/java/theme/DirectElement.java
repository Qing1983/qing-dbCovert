package theme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DirectElement {

    // 包名
    private String packageName;

    // 后缀
    private String suffix;

    // 模板名称
    private String templateName;

    // 资源
    private String source;

    // 类注释
    private String comment;

    // 类继承
    private String extendClass;

    // other
    private Map<String, String> enlarge;

    // 关系
    private ArrayList<String> rely;

    public DirectElement(String packageName, String suffix, String templateName) {
        this.packageName = packageName;
        this.suffix = suffix;
        this.templateName = templateName;
        rely = new ArrayList<>();
    }

    public void setRely (String value) {
        rely.add(value);
    }

}
