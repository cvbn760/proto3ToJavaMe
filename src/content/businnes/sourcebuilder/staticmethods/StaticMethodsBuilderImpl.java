package content.businnes.sourcebuilder.staticmethods;

import net.jarlehansen.proto2javame.business.sourcebuilder.resource.ResourceFormatUtil;
import net.jarlehansen.proto2javame.business.sourcebuilder.staticmethods.StaticMethodsBuilder;

public final class StaticMethodsBuilderImpl implements StaticMethodsBuilder {
    private final ResourceFormatUtil resourceFormat;

    public StaticMethodsBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.STATIC_METHODS;
    }

    public StringBuilder createStaticMethods(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createSetUnknownTagHandler(className));
        builder.append(this.createParseFromByteArray(className));
        builder.append(this.createParseFromInputStream(className));
        builder.append(this.createParseDelimitedFromInputStream(className));
        builder.append(this.createClassEnd());
        return builder;
    }

    private String createSetUnknownTagHandler(String className) {
        return this.resourceFormat.getString("static.set.unknowntaghandler", className);
    }

    private String createParseFromByteArray(String className) {
        return this.resourceFormat.getString("static.parsefrom.bytearray", className);
    }

    private String createParseFromInputStream(String className) {
        return this.resourceFormat.getString("static.parsefrom.inputstream", className);
    }

    private String createParseDelimitedFromInputStream(String className) {
        return this.resourceFormat.getString("static.parsedelimitedfrom.inputstream", className);
    }

    private String createClassEnd() {
        return this.resourceFormat.getString("class.end");
    }
}
