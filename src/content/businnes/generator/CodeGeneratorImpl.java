package content.businnes.generator;

import content.businnes.generator.factory.MetaDataFactory;
import content.businnes.sourcebuilder.JavaSourceCodeBuilder;
import content.businnes.sourcebuilder.JavaSourceCodeBuilderImpl;
import content.domain.java.JavaFileOutput;
import content.domain.metadata.InputMetaData;
import content.domain.proto.ProtoFileInput;
import content.io.protoinput.ProtoObjectBuilder;
import content.io.protoinput.ProtoObjectBuilderImpl;
import content.io.sourceoutput.SourceFileWriter;
import content.io.sourceoutput.SourceFileWriterImpl;

import java.util.List;

public final class CodeGeneratorImpl implements CodeGenerator {
    private final ProtoObjectBuilder protoObjectBuilder;
    private final JavaSourceCodeBuilder javaSourceCodeBuilder;
    private final SourceFileWriter sourceFileWriter;

//    @Inject
    public CodeGeneratorImpl() {
        this.protoObjectBuilder = new ProtoObjectBuilderImpl();
        this.javaSourceCodeBuilder = new JavaSourceCodeBuilderImpl();
        this.sourceFileWriter = new SourceFileWriterImpl();
    }

    public List<JavaFileOutput> generateJavaSourceCode(String... inputValues) {
        InputMetaData metaData = MetaDataFactory.createInputMetaDataVo(inputValues);
        List<ProtoFileInput> protoInputList = this.protoObjectBuilder.validateAndSaveProtoData(metaData.getProtoLocation());
        List<JavaFileOutput> javaOutputList = this.javaSourceCodeBuilder.createJavaSourceCode(protoInputList);
        this.sourceFileWriter.writeJavaSourceFile(metaData, javaOutputList);
        return javaOutputList;
    }
}
