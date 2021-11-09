package content.domain.java;

public class JavaFileOutput {
    private final String packageName;
    private final String className;
    private StringBuilder mainClass = new StringBuilder();
    private StringBuilder enumClasses = new StringBuilder();
    private StringBuilder internalClass = new StringBuilder();
    private StringBuilder publicMethods = new StringBuilder();
    private StringBuilder privateAndProtectedMethods = new StringBuilder();
    private StringBuilder staticMethods = new StringBuilder();

    public JavaFileOutput(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setMainClass(StringBuilder mainClass) {
        this.mainClass = mainClass;
    }

    public void setEnumClasses(StringBuilder enumClasses) {
        this.enumClasses = enumClasses;
    }

    public void setInternalClass(StringBuilder internalClass) {
        this.internalClass = internalClass;
    }

    public void setPublicMethods(StringBuilder publicMethods) {
        this.publicMethods = publicMethods;
    }

    public void setPrivateAndProtectedMethods(StringBuilder privateAndProtectedMethods) {
        this.privateAndProtectedMethods = privateAndProtectedMethods;
    }

    public void setStaticMethods(StringBuilder staticMethods) {
        this.staticMethods = staticMethods;
    }

    public String getCompleteSource() {
        return this.mainClass.toString() + this.enumClasses.toString() + this.internalClass.toString() + this.publicMethods.toString() + this.privateAndProtectedMethods.toString() + this.staticMethods.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JavaFileOutput");
        sb.append("{packageName='").append(this.packageName).append('\'');
        sb.append(", className='").append(this.className).append('\'');
        sb.append(", mainClass=").append(this.mainClass);
        sb.append(", enumClasses=").append(this.enumClasses);
        sb.append(", internalClass=").append(this.internalClass);
        sb.append(", publicMethods=").append(this.publicMethods);
        sb.append(", privateAndProtectedMethods=").append(this.privateAndProtectedMethods);
        sb.append(", staticMethods=").append(this.staticMethods);
        sb.append('}');
        return sb.toString();
    }
}
