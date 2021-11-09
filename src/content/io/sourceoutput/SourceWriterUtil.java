package content.io.sourceoutput;

import content.domain.java.JavaFileOutput;
import content.domain.metadata.InputMetaData;
import content.io.exception.ProtoFileException;

import java.io.File;

enum SourceWriterUtil {
    SourceWriterUtil() {
    }

    static String createDestinationFile(InputMetaData metaData, JavaFileOutput javaOutput) {
        String packageName = javaOutput.getPackageName().replace(".", "/");
        if (!createPackageDirectory(packageName, metaData.getDestinationDirectory())) {
            throw new ProtoFileException("Unable to create directory " + packageName);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(metaData.getDestinationDirectory()).append("/");
            builder.append(packageName);
            builder.append("/");
            builder.append(javaOutput.getClassName()).append(".java");
            return builder.toString();
        }
    }

    private static boolean createPackageDirectory(String packageDirectory, String destinationDirectory) {
        File packageDir = new File(destinationDirectory + "/" + packageDirectory);
        return packageDir.isDirectory() || packageDir.mkdirs();
    }
}
