package content.businnes.sourcebuilder.builder;


import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;

import java.util.Iterator;

public final class InternalClassBuilderImpl implements InternalClassBuilder {
    private final ResourceFormatUtil resourceFormat;

    public InternalClassBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.BUILDER;
    }

    public StringBuilder createInternalClass(String className, ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createClassInitialization()); // Добавляет название класса строителя
        builder.append(this.createClassFields(protoInput)); // Добавляет поля
        builder.append(this.createConstructor());
        builder.append(this.createMethods(protoInput));
        builder.append(this.createBuildMethods(className)); // Добавляет метод build
        builder.append(this.createEnd()); // Добавляет закрывающую фигурную скобку в конце
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
                if (field.getType().isPrimitiveType()) {
                    // если лист состоит из примитивов
                    builder.append(this.resourceFormat.getString("internal.builder.methods.list", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getListImpl().getImplementation(), "vector", field.getType().getImplementationType()));
                }
                else {
                    // если лист состоит из объектов
                    builder.append(this.resourceFormat.getString("internal.builder.methods.list.object.add", field.getName(), field.getType().getJavaObjectType()));
                }
                if (field.getType().isPrimitiveType()) {
                    builder.append(this.resourceFormat.getString("internal.builder.methods.list.addelement.primitive", field.getName(), String.valueOf(field.getSyntax())));
                } else {
                    builder.append(this.resourceFormat.getString("internal.builder.methods.list.object.set"));
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
