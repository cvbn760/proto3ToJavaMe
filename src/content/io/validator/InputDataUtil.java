package content.io.validator;


import content.io.exception.InvalidInputException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public enum InputDataUtil {
    InputDataUtil() {
    };

    public static String getDestinationDirectory(String... inputValues) {
        String destinationDirectory;
        if (inputValues.length == 1) {
            destinationDirectory = getCurrentDirectory();
        } else {
            if (inputValues[0].length() <= "--java_out=".length()) {
                throw new InvalidInputException("The destination directory was invalid, remember to include --java_out=");
            }

            destinationDirectory = inputValues[0].substring("--java_out=".length());
        }

        isValidDirectory(destinationDirectory);
        return destinationDirectory;
    }

    private static String getCurrentDirectory() {
        try {
            File currentDir = new File(".");
            return currentDir.getCanonicalPath();
        } catch (IOException var1) {
            throw new InvalidInputException(var1.getClass().getName() + " when trying to find the current directory", var1);
        }
    }

    public static String getProtoLocation(String... inputValues) {
        String protoLocation = inputValues[inputValues.length - 1];
        isValidFile(protoLocation);
        return protoLocation;
    }

    public static void isValidNumberOfParameters(String... inputValues) {
        if (inputValues.length != 2) {
            throw new InvalidInputException("The number of input parameters must be 2, it was: " + inputValues.length + ". Values: " + Arrays.toString(inputValues) + ". Valid syntax: --java_out=/path/to/dir path/to/protoFile.proto");
        }
    }

    private static void isValidDirectory(String path) {
        if (!(new File(path)).isDirectory()) {
            throw new InvalidInputException("The --java_out directory was not specified or is not a valid directory, value: " + path);
        }
    }

    private static void isValidFile(String path) {
        if (!(new File(path)).isFile()) {
            throw new InvalidInputException("The .proto file specified could not be located or is not a valid file, value: " + path);
        }
    }
}
