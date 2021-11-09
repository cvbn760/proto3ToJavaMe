package content.businnes.generator;


import com.google.inject.Inject;
import content.businnes.generator.factory.MetaDataFactory;
import content.businnes.sourcebuilder.JavaSourceCodeBuilder;
import content.domain.java.JavaFileOutput;
import content.domain.metadata.InputMetaData;
import content.domain.proto.ProtoFileInput;
import content.io.protoinput.ProtoObjectBuilder;
import content.io.sourceoutput.SourceFileWriter;
import net.jarlehansen.proto2javame.business.generator.CodeGenerator;

import java.util.List;

public final class CodeGeneratorImpl implements CodeGenerator {
    private final ProtoObjectBuilder protoObjectBuilder;
    private final JavaSourceCodeBuilder javaSourceCodeBuilder;
    private final SourceFileWriter sourceFileWriter;

    @Inject
    public CodeGeneratorImpl(ProtoObjectBuilder protoObjectBuilder, JavaSourceCodeBuilder javaSourceCodeBuilder, SourceFileWriter sourceFileWriter) {
        this.protoObjectBuilder = protoObjectBuilder;
        this.javaSourceCodeBuilder = javaSourceCodeBuilder;
        this.sourceFileWriter = sourceFileWriter;
    }

    public List<JavaFileOutput> generateJavaSourceCode(String... inputValues) {
        InputMetaData metaData = MetaDataFactory.createInputMetaDataVo(inputValues);
        List<ProtoFileInput> protoInputList = this.protoObjectBuilder.validateAndSaveProtoData(metaData.getProtoLocation());
        List<JavaFileOutput> javaOutputList = this.javaSourceCodeBuilder.createJavaSourceCode(protoInputList);
        this.sourceFileWriter.writeJavaSourceFile(metaData, javaOutputList);
        return javaOutputList;
    }
}
