package content.io.sourceoutput;

import net.jarlehansen.proto2javame.domain.java.JavaFileOutput;
import net.jarlehansen.proto2javame.domain.metadata.InputMetaData;

import java.util.List;

public interface SourceFileWriter {
    void writeJavaSourceFile(InputMetaData var1, List<JavaFileOutput> var2);
}
