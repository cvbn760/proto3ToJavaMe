package content.businnes.sourcebuilder.instancemethods;


import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.metadata.DataType;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidTypes;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


public final class InstanceMethodsBuilderImpl implements InstanceMethodsBuilder {
    private final ResourceFormatUtil resourceFormat;
    private ProtoFileInput protoInput;
    private List<FieldData> fieldDatas = new ArrayList<>();
    private Set needGetArraysMethod;

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
        if (isNeedArraysMethod()){
            builder.append(this.createGetArrayMethod(needGetArraysMethod));
        }
        if (isNeedGetTypePackedMethod()) {
            builder.append(this.createTypePacked(needGetArraysMethod));
        }
        return builder;
    }

    private boolean isNeedGetTypePackedMethod(){
        needGetArraysMethod = new HashSet();
        protoInput.getFields().stream()
                .filter(distinctByKey(fd -> fd.getType().getName()))
                .filter(field -> field.getType().isPrimitiveType() && field.isList())
                .forEach(fld -> needGetArraysMethod.add(fld));
        return needGetArraysMethod.size() != 0;
    }

    private String createTypePacked(Set<FieldData> fieldTypes){
        StringBuilder builder = new StringBuilder();
        for (FieldData field : fieldTypes){
            builder.append(this.resourceFormat.getString("private.get.type.packed.method", JavaSourceCodeUtil.createCapitalLetterName(field.getType().getProtoType()), getPacked(field), String.valueOf(field.getSyntax()), field.getType().getJavaObjectType(), field.getType().getImplementationType(), field.getType().getName()));
        }
        return builder.toString();
    }

    private String createGetArrayMethod(Set<DataType> fieldTypes){
        StringBuilder builder = new StringBuilder();
        for (DataType type : fieldTypes) {
            builder.append(this.resourceFormat.getString("private.need.get.arrays.method", type.getImplementationType(), type.getJavaObjectType()));
        }
        return builder.toString();
    }

    private boolean isNeedArraysMethod(){
        needGetArraysMethod = new HashSet();
        protoInput.getFields().stream()
                .filter(distinctByKey(fd -> fd.getType().getImplementationType()))
                .filter(field -> field.getType().isPrimitiveType() && field.isList())
                        .forEach(fld -> needGetArraysMethod.add(fld.getType()));
        return needGetArraysMethod.size() != 0;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
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
            // Если ENUM/NO LIST НЕ ИСПРАВЛЯТЬ
            if (!field.isList() && field.getType() == ValidTypes.ENUM_VALUE){
                builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent.enum.nolist", field.getName(), String.valueOf(field.getSyntax()), JavaSourceCodeUtil.createCapitalLetterName(field.getType().getProtoType()), JavaSourceCodeUtil.createFieldNumberName(field.getName())));
            }



            else if (field.isList()) {
                // Если лист/packed true/примитив
                if (field.getType().isPrimitiveType()){
                    builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent.repeated.packed.primitive",field.getName(), String.valueOf(field.getSyntax()), "Repeated",getPacked(field), JavaSourceCodeUtil.createFieldNumberName(field.getName()), JavaSourceCodeUtil.createCapitalLetterName(field.getType().getProtoType())));
                    // Нужно добавить методы для получения упакованных значений (getInt32Packed, getUInt32Packed, getSInt32Packed, getSInt64Packed.....)
                }
                else if (this.isValidType(field.getType())) {
                    builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent", field.getName(), String.valueOf(field.getSyntax()),  "RepeatedPacked", JavaSourceCodeUtil.createFieldNumberName(field.getName())));
                }
                else {
                    // Если лист состоит из объект класса (непримитивных типов)
                    builder.append(this.resourceFormat.getString("public.createtobytearraymethod.trycontent.list.object", field.getName(), String.valueOf(field.getSyntax()), JavaSourceCodeUtil.createFieldNumberName(field.getName())));
                    fieldDatas.add(field);
                }
            }
            else if (this.isValidType(field.getType())) {
//                if (field.getType().getImplementationType())
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
            // Если поле не лист/енум/ ЗДЕСЬ БОЛЬШЕ НИЧЕГО НЕ ИСПРАВЛЯТЬ
            if (!field.isList() && field.getType() == ValidTypes.ENUM_VALUE){
                builder.append(this.resourceFormat.getString("public.parsing.static.enum.nolist", JavaSourceCodeUtil.createFieldNumberName(field.getName()), String.valueOf(field.getSyntax()), JavaSourceCodeUtil.createCapitalLetterName(field.getType().getProtoType()), field.getName()));
            }
            // Если поле - лист примитивов ЗДЕСЬ БОЛЬШЕ НИЧЕГО НЕ ИСПРАВЛЯТЬ
            else if (field.isList() && field.getType().isPrimitiveType()){
                builder.append(this.resourceFormat.getString("packageprotected.static.list.primitive", JavaSourceCodeUtil.createFieldNumberName(field.getName()), field.getName(), String.valueOf(field.getSyntax()), getPacked(field), field.getType().getName()));
            }




            else if (field.isList()) {
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
    private String getPacked(FieldData fieldData){
        return fieldData.getPacked() ? "Packed" : "";
    }
}
