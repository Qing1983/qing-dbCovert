package direct.template;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DirectMember {


    /* 表列相关信息 */

    public static String COLUMN_REMARK = "remark";

    public static String COLUMN_NAME = "name";

    public static String COLUMN_TYPE = "type";

    public static String COLUMN_DESC = "desc";

    /* 内置对象相关 */

    public static String MODEL_ELEMENT_NAME = "model";

    public static String DAO_ELEMENT_NAME = "dao";

    public static String SERVICE_ELEMENT_NAME = "service";

    public static String SERVICE_IMPL_ELEMENT_NAME = "serviceImpl";

    public static String CONTROLLER_ELEMENT_NAME = "controller";


    /* 模板相关属性 */

    public static String TABLE_ATTRS_NAME = "attrs";

    public static String PACKAGE_NAME ="Package";

    public static String CLASS_NAME = "ClassName";

    public static String IMPORT_NAME = "Import";

    public static String COMMENT_NAME = "Comment";

    public static String TABLE_NAME = "tableName";

    public static String CLASS_EXTEND_NAME = "ExtendClass";

}
