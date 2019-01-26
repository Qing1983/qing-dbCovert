package global;

public enum Cmd {

    GET_DB_INFO("DBMethods", "getDbInfo"),

    TEST_DB_CONNECT("DBMethods", "testDb");

    private String className;

    private String methodsName;

    Cmd(String className, String methodsName) {
        this.className = className;
        this.methodsName = methodsName;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodsName() {
        return methodsName;
    }

    public void setMethodsName(String methodsName) {
        this.methodsName = methodsName;
    }
}
