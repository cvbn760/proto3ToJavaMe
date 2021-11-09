package content.businnes.sourcebuilder.builder;

import net.jarlehansen.proto2javame.business.sourcebuilder.builder.InternalClassBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.resource.JavaSourceCodeUtil;
import net.jarlehansen.proto2javame.business.sourcebuilder.resource.ResourceFormatUtil;
import net.jarlehansen.proto2javame.domain.proto.FieldData;
import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;

import java.util.Iterator;

public final class InternalClassBuilderImpl implements InternalClassBuilder {
    private final ResourceFormatUtil resourceFormat;

    public InternalClassBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.BUILDER;
    }

    public StringBuilder createInternalClass(String className, ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createClassInitialization());
        builder.append(this.createClassFields(protoInput));
        builder.append(this.createConstructor());
        builder.append(this.createMethods(protoInput));
        builder.append(this.createBuildMethods(className));
        builder.append(this.createEnd());
        return builder;
    }

    private String createClassInitialization() {
        return this.resourceFormat.getString("internal.builder.init");
    }

    private String createClassFields(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();

        FieldData field;
        for (Iterator i$ = protoInput.getFields().iterator(); i$.hasNext(); builder.append(this.resourceFormat.getString("internal.builder.fields.bool", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName())))) {
            field = (FieldData) i$.next();
            if (field.isList()) {
                builder.append(this.resourceFormat.getString("internal.builder.fields.list", field.getListImpl().getImplementation(), field.getName()));
            } else {
                builder.append(this.resourceFormat.getString("internal.builder.fields", field.getType().getImplementationType(), field.getName()));
            }
        }

        return builder.toString();
    }

    private String createConstructor() {
        return this.resourceFormat.getString("internal.builder.constructor");
    }

    private String createMethods(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        Iterator i$ = protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            if (field.isList()) {
                builder.append(this.resourceFormat.getString("internal.builder.methods.list", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getListImpl().getImplementation(), field.getName(), field.getType().getImplementationType()));
                if (field.getType().isPrimitiveType()) {
                    builder.append(this.resourceFormat.getString("internal.builder.methods.list.addelement.primitive", field.getName(), field.getType().getJavaObjectType()));
                } else {
                    builder.append(this.resourceFormat.getString("internal.builder.methods.list.addelement.object", field.getName()));
                }
            } else {
                builder.append(this.resourceFormat.getString("internal.builder.methods", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getType().getImplementationType(), field.getName()));
            }
        }

        return builder.toString();
    }

    private String createBuildMethods(String className) {
        return this.resourceFormat.getString("internal.builder.build.method", className);
    }

    private String createEnd() {
        return this.resourceFormat.getString("internal.builder.end");
    }
}
