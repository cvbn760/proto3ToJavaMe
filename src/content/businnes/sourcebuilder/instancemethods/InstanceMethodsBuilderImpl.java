package content.businnes.sourcebuilder.instancemethods;


import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.metadata.DataType;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidTypes;

import java.util.Iterator;

public final class InstanceMethodsBuilderImpl implements InstanceMethodsBuilder {
    private final ResourceFormatUtil resourceFormat;
    private ProtoFileInput protoInput;

    public InstanceMethodsBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.INSTANCE_METHODS;
    }

    public StringBuilder createPrivateAndProtectedMethods(String className, ProtoFileInput protoInput) {
        this.protoInput = protoInput;
        StringBuilder builder = new StringBuilder();
        builder.append(this.createPopulateWithField(className));
        builder.append(this.createToByteArrayMethod(className));
        return builder;
    }

    private boolean isValidType(DataType type) {
        boolean isValidType = false;
        ValidTypes[] arr$ = ValidTypes.values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            ValidTypes tempType = arr$[i$];
            if (tempType.getImplementationType().equals(type.getImplementationType())) {
                isValidType = true;
                break;
            }
        }

        return isValidType;
    }

    /**
     * Добавляет в каждый генерируемый класс метод toByteArray()
     * @param className - имя класса
     */
    private String createToByteArrayMethod(String className){
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("public.createtobytearraymethod.start"));
        builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trystart"));
        Iterator i$ = this.protoInput.getFields().iterator();
        while (i$.hasNext()){
            FieldData field = (FieldData) i$.next();
            if (field.isList()) {
                if (this.isValidType(field.getType())) {

                }
                else {

                }
            }
            else if (this.isValidType(field.getType())) {
                builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent", field.getName(), String.valueOf(field.getSyntax()), field.getType().getName(), JavaSourceCodeUtil.createFieldNumberName(field.getName())));
            }
            else {

            }
        }
        builder.append(this.resourceFormat.getString("public.createtobytearraymethod.tryend"));
        return builder.toString();
    }

    private String createPopulateWithField(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.start", className));
        Iterator i$ = this.protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            if (field.isList()) {
                if (this.isValidType(field.getType())) {
                    builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.list", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType())));
                } else {
                    builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.list.nested", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType())));
                }
            } else if (this.isValidType(field.getType())) {
                        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields", JavaSourceCodeUtil.createFieldNumberName(field.getName()), field.getType().getImplementationType(), field.getName(), String.valueOf(field.getSyntax()), field.getType().getName())) ;
            } else {
                builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.nested", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType())));
            }
        }

        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.end"));
        return builder.toString();
    }
}
