package content.businnes.generator;



import content.domain.java.JavaFileOutput;

import java.util.List;

public interface CodeGenerator {
    List<JavaFileOutput> generateJavaSourceCode(String... var1);
}
