package content.io.protoinput.factory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;
import net.jarlehansen.proto2javame.io.exception.ProtoFileValidationException;
import net.jarlehansen.proto2javame.io.protoinput.ProtoParser;
import net.jarlehansen.proto2javame.io.protoinput.factory.ProtoParserFactory;
import net.jarlehansen.proto2javame.io.protoinput.patterns.EnumParserConstants;
import net.jarlehansen.proto2javame.io.protoinput.patterns.FieldParserConstants;
import net.jarlehansen.proto2javame.io.protoinput.patterns.MessageParserConstants;
import net.jarlehansen.proto2javame.io.protoinput.patterns.OptionParserConstants;

public class ProtoParserFactoryImpl implements ProtoParserFactory {
    static final ProtoParser NULL_PARSER = new ProtoParser() {
        public void setParserLine(String line) {
        }

        public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        }
    };
    private final ProtoParser enumParser;
    private final ProtoParser enumValueParser;
    private final ProtoParser fieldParser;
    private final ProtoParser messageParser;
    private final ProtoParser optionParser;
    private boolean newProtoInput = false;
    private boolean hasJavaPackage = false;
    private boolean hasMessageStart = false;
    private boolean hasFields = false;
    private boolean hasMessageEnd = false;
    private boolean hasEnumStart = false;
    private boolean hasEnumValue = false;
    private boolean hasEnumEnd = false;

    @Inject
    public ProtoParserFactoryImpl(@Named("EnumParser") ProtoParser enumParser, @Named("EnumValueParser") ProtoParser enumValueParser, @Named("FieldParser") ProtoParser fieldParser, @Named("MessageParser") ProtoParser messageParser, @Named("OptionParser") ProtoParser optionParser) {
        this.enumParser = enumParser;
        this.enumValueParser = enumValueParser;
        this.fieldParser = fieldParser;
        this.messageParser = messageParser;
        this.optionParser = optionParser;
    }

    public ProtoParser getProtoParser(String line) {
        ProtoParser protoParser = NULL_PARSER;
        if (!this.matchesPackagePattern(line)) {
            if (this.matchesJavaPackagePattern(line)) {
                protoParser = this.optionParser;
            } else if (!this.matchesJavaOuterClassnamePattern(line)) {
                if (this.matchesMessageStartPattern(line)) {
                    protoParser = this.messageParser;
                } else if (this.matchesMessageFieldPattern(line)) {
                    protoParser = this.fieldParser;
                } else if (this.matchesEnumStartPattern(line)) {
                    protoParser = this.enumParser;
                } else if (this.matchesEnumValuePattern(line)) {
                    protoParser = this.enumValueParser;
                } else if (this.matchesEnumEndPattern(line)) {
                    this.resetEnumValues();
                } else if (this.matchesMessageEndPattern(line)) {
                    this.resetAll();
                } else if (line.trim().length() > 0) {
                    throw new ProtoFileValidationException("The .proto-file is invalid, content: " + line);
                }
            }
        }

        protoParser.setParserLine(line);
        return protoParser;
    }

    private void resetEnumValues() {
        this.hasEnumStart = false;
        this.hasEnumValue = false;
        this.hasEnumEnd = false;
    }

    private void resetAll() {
        this.resetEnumValues();
        this.hasJavaPackage = false;
        this.hasMessageStart = false;
        this.hasFields = false;
        this.newProtoInput = true;
    }

    public boolean getHasMessageEnd() {
        return this.hasMessageEnd;
    }

    public boolean isNewProto() {
        boolean retValue;
        if (this.newProtoInput) {
            retValue = true;
            this.newProtoInput = false;
        } else {
            retValue = false;
        }

        return retValue;
    }

    private boolean matchesPackagePattern(String line) {
        return OptionParserConstants.PATTERN_PACKAGE.matcher(line).matches();
    }

    private boolean matchesJavaPackagePattern(String line) {
        boolean pattern;
        if (this.hasJavaPackage) {
            pattern = false;
        } else {
            pattern = OptionParserConstants.PATTERN_JAVA_PACKAGE.matcher(line).matches();
            this.hasJavaPackage = pattern;
        }

        return pattern;
    }

    private boolean matchesJavaOuterClassnamePattern(String line) {
        return OptionParserConstants.PATTERN_JAVA_OUTER_CLASSNAME.matcher(line).matches();
    }

    private boolean matchesMessageStartPattern(String line) {
        boolean pattern;
        if (!this.hasMessageStart && !this.hasFields) {
            pattern = MessageParserConstants.PATTERN_MESSAGE_START.matcher(line).matches();
            this.hasMessageStart = pattern;
            if (this.hasMessageStart) {
                this.hasMessageEnd = false;
            }
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesMessageFieldPattern(String line) {
        boolean pattern;
        if (this.hasMessageStart && !this.hasMessageEnd) {
            pattern = FieldParserConstants.PATTERN_FIELD.matcher(line).matches();
            if (!this.hasFields) {
                this.hasFields = pattern;
            }
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesEnumStartPattern(String line) {
        boolean pattern;
        if (!this.hasEnumStart) {
            pattern = EnumParserConstants.PATTERN_ENUM_START.matcher(line).matches();
            this.hasEnumStart = pattern;
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesEnumValuePattern(String line) {
        boolean pattern;
        if (this.hasEnumStart && !this.hasEnumEnd) {
            pattern = EnumParserConstants.PATTERN_ENUM_VALUE.matcher(line).matches();
            if (!this.hasEnumValue) {
                this.hasEnumValue = pattern;
            }
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesEnumEndPattern(String line) {
        boolean pattern;
        if (this.hasEnumStart && this.hasEnumValue && !this.hasEnumEnd) {
            pattern = EnumParserConstants.PATTERN_ENUM_END.matcher(line).matches();
            this.hasEnumEnd = pattern;
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesMessageEndPattern(String line) {
        boolean pattern;
        if (this.hasMessageStart && this.hasFields && !this.hasMessageEnd) {
            pattern = MessageParserConstants.PATTERN_MESSAGE_END.matcher(line).matches();
            this.hasMessageEnd = pattern;
        } else {
            pattern = false;
        }

        return pattern;
    }
}
