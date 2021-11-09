package content.businnes.sourcebuilder.enums;

import net.jarlehansen.proto2javame.business.sourcebuilder.enums.EnumsBuilder;
import net.jarlehansen.proto2javame.business.sourcebuilder.resource.ResourceFormatUtil;
import net.jarlehansen.proto2javame.domain.proto.EnumData;
import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class EnumsBuilderImpl implements EnumsBuilder {
    public EnumsBuilderImpl() {
    }

    public StringBuilder createEnums(ProtoFileInput protoInput) {
        Collection<EnumData> enums = protoInput.getEnums();
        StringBuilder builder = new StringBuilder();
        Iterator i$ = enums.iterator();

        while (i$.hasNext()) {
            EnumData enumData = (EnumData) i$.next();
            builder.append(this.createInnerMainClass(enumData.getName()));
            builder.append(this.createInnerFields(enumData.getEnumValues()));
            builder.append(this.createInnerClassMethod(enumData.getEnumValues()));
        }

        return builder;
    }

    private String createInnerMainClass(String enumName) {
        return ResourceFormatUtil.ENUM.getString("inner.class.init", enumName);
    }

    private String createInnerFields(Map<Integer, String> enumValues) {
        StringBuilder builder = new StringBuilder();
        Iterator i$ = enumValues.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry) i$.next();
            builder.append(ResourceFormatUtil.ENUM.getString("inner.class.fields", entry.getValue(), entry.getKey().toString()));
        }

        return builder.toString();
    }

    private String createInnerClassMethod(Map<Integer, String> enumValues) {
        StringBuilder builder = new StringBuilder();
        builder.append(ResourceFormatUtil.ENUM.getString("inner.class.method.start"));
        builder.append(ResourceFormatUtil.ENUM.getString("inner.class.method.switch.start"));
        Iterator i$ = enumValues.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry) i$.next();
            builder.append(ResourceFormatUtil.ENUM.getString("inner.class.method.switch.case", entry.getKey().toString(), entry.getValue()));
        }

        builder.append(ResourceFormatUtil.ENUM.getString("inner.class.method.switch.end"));
        builder.append(ResourceFormatUtil.ENUM.getString("inner.class.method.end"));
        builder.append(ResourceFormatUtil.ENUM.getString("inner.class.end"));
        return builder.toString();
    }
}
