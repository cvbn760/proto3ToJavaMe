package content.domain.proto;


import content.domain.metadata.DataType;

public class CustomType implements DataType {
    private final String implementationType;

    public CustomType(String implementationType) {
        this.implementationType = implementationType;
    }

    public String getImplementationType() {
        return this.implementationType;
    }

    public String getProtoType() {
        return this.getImplementationType();
    }

    public String getDataTypeConstant() {
        return this.getImplementationType();
    }

    public boolean isPrimitiveType() {
        return false;
    }

    public String getJavaObjectType() {
        return this.getImplementationType();
    }

    public String getName() {
        return this.getName();
    }
}