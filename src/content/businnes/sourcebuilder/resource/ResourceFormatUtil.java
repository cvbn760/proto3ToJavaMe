package content.businnes.sourcebuilder.resource;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceFormatUtil {
    COMMON("common/common"),
    BUILDER("builder"),
    MAIN("main"),
    ENUM("enum"),
    INSTANCE_METHODS("instance_methods"),
    PUBLIC_METHODS("publicmethods"),
    STATIC_METHODS("staticmethods");

    private static final String DEFAULT_RESOURCE = "javame";
    private static final String RESOURCE_LOCATION = "source_structure/";
    private static final String DEFAULT_LINE_SEPARATOR = "\n";
    private final ResourceBundle resource;
    String lineSeparator;

    ResourceFormatUtil(String baseName) {
        Locale locale = new Locale("javame");
        this.resource = ResourceBundle.getBundle("source_structure/" + baseName, locale);
        String tmpLineSeparator = System.getProperty("line.separator");
        if (tmpLineSeparator == null) {
            System.err.println("The system property line.separator is null, using \\n");
            this.lineSeparator = "\n";
        } else {
            this.lineSeparator = tmpLineSeparator;
        }

    }

    public String getString(String key) {
        return this.replaceLineSeparators(this.resource.getString(key));
    }

    public String getString(String key, String... input) {
        MessageFormat messageFormat = new MessageFormat(this.resource.getString(key));
        StringBuffer buffer = new StringBuffer();
        messageFormat.format(input, buffer, null);
        return this.replaceLineSeparators(buffer.toString());
    }

    private String replaceLineSeparators(String resourceString) {
        String tmpResourceString;
        if (!this.lineSeparator.equals("\n")) {
            tmpResourceString = resourceString.replace("\n", this.lineSeparator);
        } else {
            tmpResourceString = resourceString;
        }

        return tmpResourceString;
    }
}
