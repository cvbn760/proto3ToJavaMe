package content.businnes.sourcebuilder.instancemethods;


import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.metadata.DataType;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public final class InstanceMethodsBuilderImpl implements InstanceMethodsBuilder {
    private final ResourceFormatUtil resourceFormat;
    private ProtoFileInput protoInput;
    private List<FieldData> fieldDatas = new ArrayList<>();

    public InstanceMethodsBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.INSTANCE_METHODS;
    }

    public StringBuilder createPrivateAndProtectedMethods(String className, ProtoFileInput protoInput) {
        this.protoInput = protoInput;
        StringBuilder builder = new StringBuilder();
        builder.append(this.createPopulateWithField(className));
        builder.append(this.createToByteArrayMethod(className));
        if (fieldDatas.size() != 0){
            builder.append(this.createGetBytePackMethod(fieldDatas));
        }
        return builder;
    }

    private String createGetBytePackMethod(List<FieldData> fieldDatas){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldDatas.size(); i ++){
            FieldData fieldData = (FieldData) fieldDatas.get(i);
            stringBuilder.append(this.resourceFormat.getString("private.get.byte.pack.method.start", fieldData.getName(), fieldData.getType().getJavaObjectType()));
        }
        return stringBuilder.toString();
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
                    builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent", field.getName(), String.valueOf(field.getSyntax()),  "RepeatedPacked", JavaSourceCodeUtil.createFieldNumberName(field.getName())));
                }
                else {
                    // Если лист состоит из объект класса (непримитивных типов)
                    builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent.list.object", field.getName(), String.valueOf(field.getSyntax()), JavaSourceCodeUtil.createFieldNumberName(field.getName())));
                    fieldDatas.add(field);
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
        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.start", className, String.valueOf(protoInput.getFields().get(0).getSyntax())));
        Iterator i$ = this.protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            System.out.println("field >>>>>>>>>>" + field);
            if (field.isList()) {
                if (this.isValidType(field.getType())) {
                    // Если поле
                    builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.list", JavaSourceCodeUtil.createFieldNumberName(field.getName()), String.valueOf(field.getSyntax()), field.getName()));
                } else {
                    // Если поле
                    builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.list.nested", JavaSourceCodeUtil.createFieldNumberName(field.getName()), String.valueOf(field.getSyntax()), field.getName(), field.getType().getJavaObjectType()));
                }
            } else if (this.isValidType(field.getType())) {
                // Если поле
                        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields", JavaSourceCodeUtil.createFieldNumberName(field.getName()), field.getType().getImplementationType(), field.getName(), String.valueOf(field.getSyntax()), field.getType().getName())) ;
            } else {
                // Если поле
//                builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.nested",JavaSourceCodeUtil.createFieldNumberName(field.getName())));
            }
        }

        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.end"));
        return builder.toString();
    }
}
