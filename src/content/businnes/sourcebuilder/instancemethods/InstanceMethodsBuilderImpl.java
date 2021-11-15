package content.businnes.sourcebuilder.instancemethods;



import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.metadata.DataType;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidScopes;
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
        builder.append(this.createComputeSize()); // Добавляет метод посчета размера
        builder.append(this.createComputeSizeNestedMessages()); //
        builder.append(this.createWriteFields()); //
        builder.append(this.createParseFields(className)); //
        builder.append(this.createGetNextFieldNumber()); //
        builder.append(this.createPopulateWithField()); //
        return builder;
    }

    private String createComputeSize() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("public.compute.size.start"));
        Iterator i$ = this.protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            if (this.isValidType(field.getType())) {
                if (field.isList()) {
                    builder.append(this.resourceFormat.getString("public.compute.size.fields.list", ResourceFormatUtil.COMMON.getString("class.compute.size.util"), JavaSourceCodeUtil.createCapitalLetterMethod(field.getListImpl().getImplementation()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), ResourceFormatUtil.COMMON.getString("class.supported.data.types"), field.getType().getDataTypeConstant(), field.getName()));
                } else if (field.getScope() == ValidScopes.REQUIRED) {
                    builder.append(this.resourceFormat.getString("public.compute.size.fields", ResourceFormatUtil.COMMON.getString("class.compute.size.util"), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
                } else {
                    builder.append(this.resourceFormat.getString("public.compute.size.fields.optional", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), ResourceFormatUtil.COMMON.getString("class.compute.size.util"), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType()), JavaSourceCodeUtil.createFieldNumberName(field.getName()), field.getName()));
                }
            }
        }

        builder.append(this.resourceFormat.getString("public.compute.size.fields.nested"));
        builder.append(this.resourceFormat.getString("public.compute.size.end"));
        return builder.toString();
    }

    private String createComputeSizeNestedMessages() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("private.compute.message.size.start"));
        Iterator i$ = this.protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            if (!this.isValidType(field.getType())) {
                if (field.getScope() == ValidScopes.REQUIRED) {
                    builder.append(this.resourceFormat.getString("private.compute.message.size.custom.type", ResourceFormatUtil.COMMON.getString("class.compute.size.util"), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
                } else if (field.isList()) {
                    builder.append(this.resourceFormat.getString("private.compute.message.size.custom.type.list", ResourceFormatUtil.COMMON.getString("class.compute.size.util"), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), ResourceFormatUtil.COMMON.getString("class.supported.data.types"), field.getName()));
                } else {
                    builder.append(this.resourceFormat.getString("private.compute.message.size.custom.type.optional", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), ResourceFormatUtil.COMMON.getString("class.compute.size.util"), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
                }
            }
        }

        builder.append(this.resourceFormat.getString("private.compute.message.size.end"));
        return builder.toString();
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

    private String createWriteFields() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("public.writefields.start", ResourceFormatUtil.COMMON.getString("class.output.writer")));
        Iterator i$ = this.protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            if (field.isList()) {
                if (this.isValidType(field.getType())) {
                    builder.append(this.resourceFormat.getString("public.writefields.fields.list", JavaSourceCodeUtil.createCapitalLetterMethod(field.getListImpl().getImplementation()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), ResourceFormatUtil.COMMON.getString("class.supported.data.types"), field.getType().getDataTypeConstant(), field.getName()));
                } else {
                    builder.append(this.resourceFormat.getString("public.writefields.fields.list.nested", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), ResourceFormatUtil.COMMON.getString("class.supported.data.types"), field.getName()));
                }
            } else if (field.getScope() == ValidScopes.REQUIRED) {
                if (this.isValidType(field.getType())) {
                    builder.append(this.resourceFormat.getString("public.writefields.fields", JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
                } else {
                    builder.append(this.resourceFormat.getString("public.writefields.fields.nested", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
                }
            } else if (this.isValidType(field.getType())) {
                builder.append(this.resourceFormat.getString("public.writefields.fields.optional", JavaSourceCodeUtil.createFieldNumberName(field.getName()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType()), field.getName(), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName())));
            } else {
                builder.append(this.resourceFormat.getString("public.writefields.fields.optional.nested", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), field.getName()));
            }
        }

        builder.append(this.resourceFormat.getString("public.writefields.end"));
        return builder.toString();
    }

    private String createParseFields(String className) {
        return this.resourceFormat.getString("packageprotected.static.parsefields", className, ResourceFormatUtil.COMMON.getString("class.input.reader"));
    }

    private String createGetNextFieldNumber() {
        return this.resourceFormat.getString("packageprotected.static.getnextfieldnumber", ResourceFormatUtil.COMMON.getString("class.input.reader"));
    }

    private String createPopulateWithField() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.start", ResourceFormatUtil.COMMON.getString("class.input.reader")));
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
                builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields", JavaSourceCodeUtil.createFieldNumberName(field.getName()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()))) ;
            } else {
                builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.fields.nested", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName()), JavaSourceCodeUtil.createCapitalLetterMethod(field.getType().getImplementationType())));
            }
        }

        builder.append(this.resourceFormat.getString("packageprotected.static.populatewithfield.end"));
        return builder.toString();
    }
}
