package content.businnes.sourcebuilder;

import com.google.inject.Inject;
import net.jarlehansen.proto2javame.business.sourcebuilder.JavaSourceCodeBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.builder.InternalClassBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.enums.EnumsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.instancemethods.InstanceMethodsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.main.MainClassBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.publicmethods.PublicMethodsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.staticmethods.StaticMethodsBuilder;
import net.jarlehansen.proto2javame.domain.java.JavaFileOutput;
import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;

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

    @Inject
    public JavaSourceCodeBuilderImpl(InternalClassBuilder internalClassBuilder, MainClassBuilder mainClassBuilder, EnumsBuilder enumsBuilder, InstanceMethodsBuilder privateMethodsBuilder, PublicMethodsBuilder publicMethodsBuilder, StaticMethodsBuilder staticMethodsBuilder) {
        this.internalClassBuilder = internalClassBuilder;
        this.mainClassBuilder = mainClassBuilder;
        this.enumsBuilder = enumsBuilder;
        this.privateMethodsBuilder = privateMethodsBuilder;
        this.publicMethodsBuilder = publicMethodsBuilder;
        this.staticMethodsBuilder = staticMethodsBuilder;
    }

    public List<JavaFileOutput> createJavaSourceCode(List<ProtoFileInput> protoInputList) {
        List<JavaFileOutput> javaOutputList = new ArrayList();
        Iterator i$ = protoInputList.iterator();

        while (i$.hasNext()) {
            ProtoFileInput protoInput = (ProtoFileInput) i$.next();
            JavaFileOutput javaClass = new JavaFileOutput(protoInput.getPackageName(), protoInput.getProtoClassName());
            javaClass.setMainClass(this.mainClassBuilder.createMainClass(javaClass.getClassName(), protoInput));
            javaClass.setEnumClasses(this.enumsBuilder.createEnums(protoInput));
            javaClass.setInternalClass(this.internalClassBuilder.createInternalClass(javaClass.getClassName(), protoInput));
            javaClass.setPrivateAndProtectedMethods(this.privateMethodsBuilder.createPrivateAndProtectedMethods(javaClass.getClassName(), protoInput));
            javaClass.setPublicMethods(this.publicMethodsBuilder.createPublicMethods(javaClass.getClassName(), protoInput));
            javaClass.setStaticMethods(this.staticMethodsBuilder.createStaticMethods(javaClass.getClassName()));
            javaOutputList.add(javaClass);
        }

        return javaOutputList;
    }
}
