package content.businnes.sourcebuilder;


import content.domain.java.JavaFileOutput;
import content.domain.proto.ProtoFileInput;

import java.util.List;

public interface JavaSourceCodeBuilder {
    List<JavaFileOutput> createJavaSourceCode(List<ProtoFileInput> var1);
}
