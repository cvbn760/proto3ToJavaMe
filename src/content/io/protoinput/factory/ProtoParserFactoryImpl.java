package content.io.protoinput.factory;


import content.domain.proto.ProtoFileInput;
import content.io.exception.ProtoFileValidationException;
import content.io.protoinput.ProtoParser;
import content.io.protoinput.enums.EnumParser;
import content.io.protoinput.enums.EnumValueParser;
import content.io.protoinput.fields.FieldParser;
import content.io.protoinput.message.MessageParser;
import content.io.protoinput.options.OptionParser;

import java.util.regex.Pattern;

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

    public ProtoParserFactoryImpl() {
        this.enumParser = new EnumParser();
        this.enumValueParser = new EnumValueParser();
        this.fieldParser = new FieldParser();
        this.messageParser = new MessageParser();
        this.optionParser = new OptionParser();
    }

    public ProtoParser getProtoParser(String line) {
        ProtoParser protoParser = NULL_PARSER;

        /**
         * Пока просто определяет требуется ли упаковка
         * Если [packed=true] имеется, то ее убирают
         */
        if(matchesFieldPackage(line)){
            if (matchesFieldPackage(line)){
//                System.out.println("Требуется упаковка >>>>> " + line);
                line = line.replaceAll("\\s*\\[packed\\s*=\\s*true\\]\\s*", "");
            }
        }

        /**
         * Пока просто определяет
         * указан ли тип поля
         * для proto 3 отсутствие типа как optional в proto 2
         */
        if (isField(line)){
            if (!fieldHasType(line)){
                line = "optional " + line;
            }
//            System.out.println(">>>> " + line);
            // Указан ли тип поля
        }

        if (!this.matchesPackagePattern(line)) {
            if (this.matchesJavaPackagePattern(line)) {
                protoParser = this.optionParser;
            }

            // Проверяет указан ли в строке тип генерации java класса
            else if (this.matchesJavaOptimizeFor(line)){
                // пока ничего не делает
            }


            else if (!this.matchesJavaOuterClassnamePattern(line)) {
                if (this.matchesMessageStartPattern(line)) {
                    protoParser = this.messageParser;
                }
                else if (this.matchesEnumStartPattern(line)) {
                    protoParser = this.enumParser;
                } else if (this.matchesEnumValuePattern(line)) {
                    protoParser = this.enumValueParser;
                } else if (this.matchesEnumEndPattern(line)) {
                    this.resetEnumValues();
                } else if (this.matchesMessageEndPattern(line)) {
                    this.resetAll();
                }
                else if (this.matchesProtoSyntax(line)){
                    // Запись о синтаксисе
                }

//                if (!fieldHasType(line)){
//                    line = "optional " + line;
//                }
                // Если прошел предидущие условия и не был пойман, то это одно из полей proto3 без пометки "optional"
                else if (this.matchesMessageFieldPattern(line)) {
                    protoParser = this.fieldParser;
                }
                else if (!fieldHasType(line)){
                    line = "optional " + line;
                    if (this.matchesMessageFieldPattern(line)) {
                        protoParser = this.fieldParser;
                    }
                    else {
                        line = line.replaceAll("\\s*optional\\s*", "");
                    }
                }

                else if (line.trim().length() > 0) {
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
        return Pattern.compile("(package[\\s]++)(.*)(;{1}$)").matcher(line).matches();
    }

    /**
     * Указан ли тип поля
     */
    private boolean fieldHasType(String line){
        String regExp = ".*(required|optional|repeated).*";
        boolean pattern = Pattern.compile(regExp).matcher(line).matches();
        if (pattern){
            /*
            Пока просто определяет
             */
        }
        return pattern;
    }

    /**
     * Является ли строка полем в сообщении
     */
    private boolean isField(String line){
        String regExp = ".*(double|float|int32|int64|uint32|uint64|sint32|sint64|fixed32|fixed64|sfixed32|sfixed64|bool|string|bytes).*";
        boolean pattern = Pattern.compile(regExp).matcher(line).matches();
        if (pattern){
            /*
            Пока просто определяет
             */
        }
        return pattern;
    }

    /**
     * Проверяет является ли строка указанием на тип синтаксиса
     */
    private boolean matchesProtoSyntax(String line){
        String regExp = "^\\s*syntax\\s*=\\s*\"proto[2,3]\"\\s*;";
        boolean pattern = Pattern.compile(regExp).matcher(line).matches();
        if (pattern){
            /**
             * В зависимости от типа синтаксиса
             * Если синтаксис не указан или указан proto2, то генерируются классы совместимые с proto2
             * Если указан синтаксис proto3, то генерируются классы совместимые с proto3
             */
        }
        return pattern;
    }

    /**
     * Проверяет указан ли в строке тип генерации java класса
     */
    private boolean matchesJavaOptimizeFor(String line){
        String regExp = "^.*option.*optimize_for.*$";
        boolean pattern = Pattern.compile(regExp).matcher(line).matches();
        if (pattern){
            /*
            Пока просто определяет
             */
        }
        return pattern;
    }

    private boolean matchesJavaPackagePattern(String line) {
        boolean pattern;
        if (this.hasJavaPackage) {
            pattern = false;
        } else {
            pattern = Pattern.compile("(option[\\s]++java_package[\\s]*=[\\s]*\")([a-zA-Z$]{1})(.[^\"]*+)([\"]{1}[;]{1}$)").matcher(line).matches();
            this.hasJavaPackage = pattern;
        }

        return pattern;
    }

    private boolean matchesJavaOuterClassnamePattern(String line) {
        return Pattern.compile("option java_outer_classname.*").matcher(line).matches();
    }

    private boolean matchesMessageStartPattern(String line) {
        boolean pattern;
        if (!this.hasMessageStart && !this.hasFields) {
            pattern = Pattern.compile("message[\\s]*\\w++[\\s]++[{]{1}$").matcher(line).matches();
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
            pattern = Pattern.compile("[\\s]*(required|optional|repeated)[\\s]++[\\w0-9]++[\\s]++[\\w0-9]++[\\s]*[=]{1}[\\s]*[0-9]++[;]{1}$").matcher(line).matches();
            if (!this.hasFields) {
                this.hasFields = pattern;
            }
        } else {
            pattern = false;
        }

        return pattern;
    }

    /**
     * Проверяет требуется ли для поля более эффективная упаковка
     * [packed=true];
     */
    private boolean matchesFieldPackage(String line){
        boolean pack = false;
        String regExp = "^.*\\[packed\\s*=\\s*true\\]\\s*;$";
        pack = Pattern.compile(regExp).matcher(line).matches();
        if (pack){
            // Если требуется упаковка

        }
        return pack;
    }

    private boolean matchesEnumStartPattern(String line) {
        boolean pattern;
        if (!this.hasEnumStart) {
            pattern = Pattern.compile("[\\s]*enum[\\s]++\\w++[\\s]++[{]{1}$").matcher(line).matches();
            this.hasEnumStart = pattern;
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesEnumValuePattern(String line) {
        boolean pattern;
        if (this.hasEnumStart && !this.hasEnumEnd) {
            pattern = Pattern.compile("[\\s]*[\\w0-9]++[\\s]*[=]{1}[\\s]*[0-9]++[;]{1}$").matcher(line).matches();
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
            pattern = Pattern.compile("[\\s]*}{1}[\\s]*").matcher(line).matches();
            this.hasEnumEnd = pattern;
        } else {
            pattern = false;
        }

        return pattern;
    }

    private boolean matchesMessageEndPattern(String line) {
        boolean pattern;
        if (this.hasMessageStart && this.hasFields && !this.hasMessageEnd) {
            pattern = Pattern.compile("[\\s]*}{1}[\\s]*").matcher(line).matches();
            this.hasMessageEnd = pattern;
        } else {
            pattern = false;
        }

        return pattern;
    }
}
