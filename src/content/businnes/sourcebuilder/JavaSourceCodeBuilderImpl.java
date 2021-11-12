package content.businnes.sourcebuilder;

import content.businnes.sourcebuilder.builder.InternalClassBuilder;
import content.businnes.sourcebuilder.builder.InternalClassBuilderImpl;
import content.businnes.sourcebuilder.enums.EnumsBuilder;
import content.businnes.sourcebuilder.enums.EnumsBuilderImpl;
import content.businnes.sourcebuilder.instancemethods.InstanceMethodsBuilder;
import content.businnes.sourcebuilder.instancemethods.InstanceMethodsBuilderImpl;
import content.businnes.sourcebuilder.main.MainClassBuilder;
import content.businnes.sourcebuilder.main.MainClassBuilderImpl;
import content.businnes.sourcebuilder.publicmethods.PublicMethodsBuilder;
import content.businnes.sourcebuilder.publicmethods.PublicMethodsBuilderImpl;
import content.businnes.sourcebuilder.staticmethods.StaticMethodsBuilder;
import content.businnes.sourcebuilder.staticmethods.StaticMethodsBuilderImpl;
import content.domain.java.JavaFileOutput;
import content.domain.proto.ProtoFileInput;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JavaSourceCodeBuilderImpl implements JavaSourceCodeBuilder {
    private final InternalClassBuilder internalClassBuilder;
    private final MainClassBuilder mainClassBuilder;
    private final EnumsBuilder enumsBuilder;
    private final InstanceMethodsBuilder privateMethodsBuilder;
    private final PublicMethodsBuilder publicMethodsBuilder;
    private final StaticMethodsBuilder staticMethodsBuilder;


    public JavaSourceCodeBuilderImpl() {
        this.internalClassBuilder = new InternalClassBuilderImpl();
        this.mainClassBuilder = new MainClassBuilderImpl();
        this.enumsBuilder = new EnumsBuilderImpl();
        this.privateMethodsBuilder = new InstanceMethodsBuilderImpl();
        this.publicMethodsBuilder = new PublicMethodsBuilderImpl();
        this.staticMethodsBuilder = new StaticMethodsBuilderImpl();
    }

    public List<JavaFileOutput> createJavaSourceCode(List<ProtoFileInput> protoInputList) {
        List<JavaFileOutput> javaOutputList = new ArrayList();
        Iterator i$ = protoInputList.iterator();

        while (i$.hasNext()) {
            ProtoFileInput protoInput = (ProtoFileInput) i$.next();
            JavaFileOutput javaClass = new JavaFileOutput(protoInput.getPackageName(), protoInput.getProtoClassName());
            javaClass.setMainClass(this.mainClassBuilder.createMainClass(javaClass.getClassName(), protoInput));  // MainClassBuilderImpl
            javaClass.setEnumClasses(this.enumsBuilder.createEnums(protoInput));
           javaClass.setInternalClass(this.internalClassBuilder.createInternalClass(javaClass.getClassName(), protoInput)); // Добавляет билдер для класса
            javaClass.setPrivateAndProtectedMethods(this.privateMethodsBuilder.createPrivateAndProtectedMethods(javaClass.getClassName(), protoInput));
            javaClass.setPublicMethods(this.publicMethodsBuilder.createPublicMethods(javaClass.getClassName(), protoInput));
            javaClass.setStaticMethods(this.staticMethodsBuilder.createStaticMethods(javaClass.getClassName()));
            javaOutputList.add(javaClass);
        }

        return javaOutputList;
    }
}
