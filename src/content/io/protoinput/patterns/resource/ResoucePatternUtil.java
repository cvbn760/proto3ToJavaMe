package content.io.protoinput.patterns.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResoucePatternUtil {
    FIELD_PARSER("field"),
    MESSAGE_PARSER("message"),
    OPTION_PARSER("option"),
    ENUM_PARSER("enum");

    private static final String DEFAULT_RESOURCE = "proto";
    private static final String RESOURCE_LOCATION = "proto_patterns/";
    private final ResourceBundle resource;

    ResoucePatternUtil(String baseName) {
        Locale locale = new Locale("proto");
        this.resource = ResourceBundle.getBundle("proto_patterns/" + baseName, locale);
    }

    public String getString(String key) {
        return this.resource.getString(key);
    }
}
