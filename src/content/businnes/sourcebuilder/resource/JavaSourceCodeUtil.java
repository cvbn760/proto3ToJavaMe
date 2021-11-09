package content.businnes.sourcebuilder.resource;

import content.domain.proto.ListImplementation;
import content.domain.proto.ValidTypes;

public enum JavaSourceCodeUtil {
    JavaSourceCodeUtil() {
    };

    public static String createClassName(String protoClassName) {
        return modifyNameCapitalLetter(protoClassName);
    }

    private static String modifyNameCapitalLetter(String name) {
        String startChar = name.substring(0, 1);
        String end = name.substring(1);
        return startChar.toUpperCase() + end;
    }

    public static String createCapitalLetterName(String name) {
        String startChar = name.substring(0, 1);
        String end = name.substring(1);
        return startChar.toUpperCase() + end;
    }

    public static String createCapitalLetterMethod(String name) {
        String methodName;
        if (name.compareToIgnoreCase(ValidTypes.BYTES.getImplementationType()) == 0) {
            methodName = "ByteString";
        } else if (name.compareToIgnoreCase(ListImplementation.VECTOR.getImplementation()) == 0) {
            methodName = "List";
        } else {
            methodName = createCapitalLetterName(name);
        }

        return methodName;
    }
}
