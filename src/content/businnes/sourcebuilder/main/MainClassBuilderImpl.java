package content.businnes.sourcebuilder.main;

import content.businnes.sourcebuilder.resource.JavaSourceCodeUtil;
import content.businnes.sourcebuilder.resource.ResourceFormatUtil;
import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.domain.proto.ValidScopes;

import java.util.Date;
import java.util.Iterator;

public final class MainClassBuilderImpl implements MainClassBuilder {
    private final ResourceFormatUtil resourceFormat;

    public MainClassBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.MAIN;
    }

    public StringBuilder createMainClass(String className, ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createPackage(protoInput)); // Добавляет пакет для класса
        builder.append(this.createImports());  // Добавляются необходимые импорты
        builder.append(this.createClassInitialization(className)); // Название класса с расширениями и реализациями
        builder.append(this.createFields(protoInput)); // Добавляет объявление полей
        builder.append(this.createNewBuilderMethod());  // Добавляет метод, который возвращает новый билдер
        builder.append(this.createConstructor(className, protoInput)); // Добавляет конструктор через билдер
        return builder;
    }

    private String createPackage(ProtoFileInput protoInput) {
        String packageName;
        if (protoInput.getPackageName() != null && !"".equals(protoInput.getPackageName())) {
            packageName = this.resourceFormat.getString("main.package", protoInput.getPackageName());
        } else {
            packageName = "";
        }

        return packageName;
    }

    private String createImports() {
        String mainPackage = ResourceFormatUtil.COMMON.getString("imports.package.structure.main");
        return this.resourceFormat.getString("main.import.statements", (new Date()).toString(), mainPackage, mainPackage + ResourceFormatUtil.COMMON.getString("imports.package.structure.input"), mainPackage + ResourceFormatUtil.COMMON.getString("imports.package.structure.output"), mainPackage + ResourceFormatUtil.COMMON.getString("imports.package.structure.taghandler"));
    }

    private String createClassInitialization(String className) {
        return this.resourceFormat.getString("main.init", className);
    }

    private String createFields(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();

        for (Iterator i$ = protoInput.getFields().iterator(); i$.hasNext(); builder.append(this.resourceFormat.getString("main.fields.end"))) {
            FieldData field = (FieldData) i$.next();
            if (field.isList()) {
                builder.append(this.resourceFormat.getString("main.fields", field.getListImpl().getImplementation(), field.getName()));
            } else {
                builder.append(this.resourceFormat.getString("main.fields", field.getType().getImplementationType(), field.getName()));
            }

            if (field.getScope() == ValidScopes.OPTIONAL) {
                builder.append(this.resourceFormat.getString("main.fields.bool", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName())));
            }
            builder.append(this.resourceFormat.getString("main.fields.number", JavaSourceCodeUtil.createFieldNumberName(field.getName()), Integer.toString(field.getId())));
        }

        return builder.toString();
    }

    private String createNewBuilderMethod() {
        return this.resourceFormat.getString("main.new.builder");
    }

    private String createConstructor(String className, ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.resourceFormat.getString("main.constructor", className));
//        builder.append(this.resourceFormat.getString("main.constructor.if.start"));
        boolean containsRequired = false;

        for (int i = 0; i < protoInput.getFields().size(); ++i) {
            FieldData field = protoInput.getFields().get(i);
            if (field.getScope() == ValidScopes.REQUIRED) {
                if (i != 0 && containsRequired) {
                    builder.append(this.resourceFormat.getString("main.constructor.if.and"));
                }

                containsRequired = true;
                builder.append(this.resourceFormat.getString("main.constructor.if.fields", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName())));
            }
        }

//        if (!containsRequired) {
//            builder.append("true");
//        }

//        builder.append(this.resourceFormat.getString("main.constructor.if.end"));
        builder.append(this.createConstructorFields(protoInput));
//        builder.append(this.resourceFormat.getString("main.constructor.else"));
        builder.append(this.createExceptionString(protoInput));
//        builder.append(this.resourceFormat.getString("main.constructor.else.end"));
        builder.append(this.resourceFormat.getString("main.method.end"));
        return builder.toString();
    }

    private String createConstructorFields(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();
        Iterator i$ = protoInput.getFields().iterator();

        while (i$.hasNext()) {
            FieldData field = (FieldData) i$.next();
            builder.append(this.resourceFormat.getString("main.constructor.fields", field.getName()));
            if (field.getScope() == ValidScopes.OPTIONAL) {
                builder.append(this.resourceFormat.getString("main.constructor.fields.bool", JavaSourceCodeUtil.createCapitalLetterMethod(field.getName())));
            }
        }

        return builder.toString();
    }

    private String createExceptionString(ProtoFileInput protoInput) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < protoInput.getFields().size(); ++i) {
            FieldData field = protoInput.getFields().get(i);
            if (field.getScope() == ValidScopes.REQUIRED) {
                builder.append(this.resourceFormat.getString("main.constructor.else.fields", field.getName(), JavaSourceCodeUtil.createCapitalLetterName(field.getName())));
            }
        }

        return builder.toString();
    }
}
