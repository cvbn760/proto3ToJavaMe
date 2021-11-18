package content.businnes.sourcebuilder.publicmethods;


import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidScopes;
import content.domain.proto.ValidTypes;

import java.util.Iterator;
import java.util.List;

public final class PublicMethodsBuilderImpl implements PublicMethodsBuilder {
    private final ResourceFormatUtil resourceFormat;
    public PublicMethodsBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.PUBLIC_METHODS;
    }

    public StringBuilder createPublicMethods(String className, ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createGetMethods(protoInput));
        builder.append(this.createToString(protoInput));
        return builder;
    }

    private String createGetMethods(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        Iterator i$ = protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            // Если поле - это список
            if (field.isList()) {
                // Если список состоит из примитивных типов
                if (field.getType().isPrimitiveType()){
                    builder.append(this.resourceFormat.getString("public.getmethods.list.primitive", field.getType().getImplementationType(), field.getName(), field.getType().getJavaObjectType()));
                }
                // Если список состоит НЕ из примитивных типов
                else {
                    builder.append(this.resourceFormat.getString("public.getmethods", field.getListImpl().getImplementation(), JavaSourceCodeUtil.createCapitalLetterName(field.getName()), field.getName()));
                }
            }
            // Если поле - это НЕ список
            else {
                builder.append(this.resourceFormat.getString("public.getmethods", field.getType().getImplementationType(), JavaSourceCodeUtil.createCapitalLetterName(field.getName()), field.getName()));
            }
            if (field.getScope() == ValidScopes.OPTIONAL) {
                builder.append(this.resourceFormat.getString("public.hasmethods", JavaSourceCodeUtil.createCapitalLetterName(field.getName())));
            }
        }

        return builder.toString();
    }

    private String createToString(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("public.tostring.start"));
        Iterator i$ = protoInput.getFields().iterator();

        while (true) {
            while (i$.hasNext()) {
                FieldData field = (FieldData) i$.next();
                // Если НЕ лист/енум/
                if (!field.isList() && field.getType() == ValidTypes.ENUM_VALUE){
                    builder.append(this.resourceFormat.getString("public.tostring.fields.optional.enum", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), protoInput.getCurrentEnum().getName()));
                }
                // Если лист/примитив/не енум
                else if (field.isList() && field.getType().isPrimitiveType() && !(field.getType() == ValidTypes.ENUM_VALUE) && !(field.getType() == ValidTypes.ENUM)){
                    builder.append(this.resourceFormat.getString("public.tostring.nolist.primitive.noenum", field.getName(), field.getType().getJavaObjectType(), field.getType().getImplementationType()));
                }


                else if (field.getScope() != ValidScopes.REQUIRED && field.getScope() != ValidScopes.REPEATED) {
                    // хз что это
//                    if (field.getType().getName().equals(ValidTypes.ENUM.getName())){
//                        builder.append(this.resourceFormat.getString("public.tostring.fields.optional.enum", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), protoInput.getCurrentEnum().getName()));
//                    }
//                    else {
                        builder.append(this.resourceFormat.getString("public.tostring.fields.optional", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
//                    }
                } else {

                    builder.append(this.resourceFormat.getString("public.tostring.fields", field.getName(), String.valueOf(field.getSyntax())));
                }
            }

            builder.append(this.resourceFormat.getString("public.tostring.end"));
            return builder.toString();
        }
    }
}
