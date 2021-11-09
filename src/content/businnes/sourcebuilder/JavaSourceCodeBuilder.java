package content.businnes.sourcebuilder;

import net.jarlehansen.proto2javame.domain.java.JavaFileOutput;
import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;

import java.util.List;

public interface JavaSourceCodeBuilder {
    List<JavaFileOutput> createJavaSourceCode(List<ProtoFileInput> var1);
}
