package content.domain.metadata;

public interface DataType {
    String getImplementationType();

    String getProtoType();

    String getDataTypeConstant();

    boolean isPrimitiveType();

    String getJavaObjectType();
}
