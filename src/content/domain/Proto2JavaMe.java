package content.domain;

import content.businnes.generator.CodeGenerator;
import content.businnes.generator.CodeGeneratorImpl;
import content.domain.java.JavaFileOutput;
import content.io.exception.Proto2JavaMeException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Онлайн конструктор регулярных выражений
 * http://regexpres.narod.ru/calculator.html
 */
public final class Proto2JavaMe {
    private Proto2JavaMe() {
    }

    public static void main(String[] args) {
        System.out.println("Starting protobuf-javame code generation, " + new Date());
        String[] strings = {"--java_out=D:/TZ2_Proto_3/rep/proto3ToJavaMe/src/my_messages",
                "D:/TZ2_Proto_3/rep/proto3ToJavaMe/resources/proto/user.proto"};


        long start = System.currentTimeMillis();
        CodeGenerator codeGen = new CodeGeneratorImpl();
        byte exitCode = 0;

        try {
            List<JavaFileOutput> javaOutputList = codeGen.generateJavaSourceCode(strings);
            System.out.println("SUCCESS! Created the java-object(s): ");
            Iterator i$ = javaOutputList.iterator();

            while (i$.hasNext()) {
                JavaFileOutput javaOutput = (JavaFileOutput) i$.next();
                System.out.println("\t-> " + javaOutput.getClassName());
            }

            System.out.println("In package: " + javaOutputList.get(0).getPackageName());
        } catch (Proto2JavaMeException var9) {
            System.err.println("Error: " + var9.getMessage());
            exitCode = 1;
        }

        long finished = System.currentTimeMillis();
        long timeUsed = finished - start;
        System.out.println("\nFINISHED, time used: " + timeUsed + "ms");
        System.exit(exitCode);
    }
}