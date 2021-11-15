package content.businnes.sourcebuilder.staticmethods;

import content.businnes.sourcebuilder.resource.ResourceFormatUtil;

public final class StaticMethodsBuilderImpl implements StaticMethodsBuilder {
    private final ResourceFormatUtil resourceFormat;

    public StaticMethodsBuilderImpl() {
        this.resourceFormat = ResourceFormatUtil.STATIC_METHODS;
    }

    public StringBuilder createStaticMethods(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createParseFromByteArray(className));
        builder.append(this.createParseFromInputStream(className));
        builder.append(this.createParseDelimitedFromInputStream(className));
        builder.append(this.createClassEnd());
        return builder;
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
