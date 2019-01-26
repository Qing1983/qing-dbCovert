package methods;

import global.Cmd;
import java.lang.reflect.Method;

public class MethodsHandler {

    public static Object handler (String cmdName, String params) throws Exception {
        Cmd cmd = Cmd.valueOf(cmdName);
        String className = cmd.getClassName();
        String methodsName = cmd.getMethodsName();
        Class methodsClass = Class.forName("methods." + className);
        Method method = methodsClass.getMethod(methodsName, String.class);
        return method.invoke(methodsClass, params);
    }

}
