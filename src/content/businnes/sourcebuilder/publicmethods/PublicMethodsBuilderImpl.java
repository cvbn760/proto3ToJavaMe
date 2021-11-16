package content.businnes.sourcebuilder.publicmethods;


import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidScopes;
import content.domain.proto.ValidTypes;

import java.util.Iterator;

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
            if (field.isList()) {
                builder.append(this.resourceFormat.getString("public.getmethods", field.getListImpl().getImplementation(), JavaSourceCodeUtil.createCapitalLetterName(field.getName()), field.getName()));
            } else {
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
                if (field.getScope() != ValidScopes.REQUIRED && field.getScope() != ValidScopes.REPEATED) {
                    if (field.getType().getName().equals(ValidTypes.ENUM.getName())){
                        builder.append(this.resourceFormat.getString("public.tostring.fields.optional.enum", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), protoInput.getCurrentEnum().getName()));
                    }
                    else {
                        builder.append(this.resourceFormat.getString("public.tostring.fields.optional", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
                    }
                } else {
                    builder.append(this.resourceFormat.getString("public.tostring.fields", field.getName(), String.valueOf(field.getSyntax())));
                }
            }

            builder.append(this.resourceFormat.getString("public.tostring.end"));
            return builder.toString();
        }
    }
}
