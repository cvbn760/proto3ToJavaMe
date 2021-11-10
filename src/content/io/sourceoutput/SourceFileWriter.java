package content.io.sourceoutput;


import content.domain.java.JavaFileOutput;
import content.domain.metadata.InputMetaData;

import java.util.List;

public interface SourceFileWriter {
    void writeJavaSourceFile(InputMetaData var1, List<JavaFileOutput> var2);
}
