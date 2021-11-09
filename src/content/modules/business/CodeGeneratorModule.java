package content.modules.business;

import com.google.inject.AbstractModule;
import net.jarlehansen.proto2javame.business.generator.CodeGenerator;
import net.jarlehansen.proto2javame.business.generator.CodeGeneratorImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.JavaSourceCodeBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.JavaSourceCodeBuilderImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.builder.InternalClassBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.builder.InternalClassBuilderImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.enums.EnumsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.enums.EnumsBuilderImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.instancemethods.InstanceMethodsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.instancemethods.InstanceMethodsBuilderImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.main.MainClassBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.main.MainClassBuilderImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.publicmethods.PublicMethodsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.publicmethods.PublicMethodsBuilderImpl;
import net.jarlehansen.proto2javame.business.sourcebuilder.staticmethods.StaticMethodsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.staticmethods.StaticMethodsBuilderImpl;

public class CodeGeneratorModule extends AbstractModule {
    public CodeGeneratorModule() {
    }

    protected void configure() {
        this.bind(CodeGenerator.class).to(CodeGeneratorImpl.class);
        this.bind(JavaSourceCodeBuilder.class).to(JavaSourceCodeBuilderImpl.class);
        this.bind(InternalClassBuilder.class).to(InternalClassBuilderImpl.class);
        this.bind(MainClassBuilder.class).to(MainClassBuilderImpl.class);
        this.bind(InstanceMethodsBuilder.class).to(InstanceMethodsBuilderImpl.class);
        this.bind(PublicMethodsBuilder.class).to(PublicMethodsBuilderImpl.class);
        this.bind(StaticMethodsBuilder.class).to(StaticMethodsBuilderImpl.class);
        this.bind(EnumsBuilder.class).to(EnumsBuilderImpl.class);
    }
}