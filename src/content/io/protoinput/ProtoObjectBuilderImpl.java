package content.io.protoinput;

import content.domain.proto.FieldData;
import content.domain.proto.ProtoFileInput;
import content.io.exception.ProtoFileValidationException;
import content.io.protoinput.factory.ProtoParserFactory;
import content.io.protoinput.factory.ProtoParserFactoryImpl;
import content.io.protoinput.util.ProtoTagUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class ProtoObjectBuilderImpl implements ProtoObjectBuilder {
    private final ProtoParserFactory protoParserFactory;
    private final List<Integer> idList = new ArrayList();
    private final List<ProtoFileInput> protoInputList = new ArrayList();
    private int lineNumber = 1;
    private ProtoFileInput currentProtoInput = new ProtoFileInput();
    private String commonClassPackage = null;

    public ProtoObjectBuilderImpl() {
        this.protoParserFactory = new ProtoParserFactoryImpl();
    }

    public List<ProtoFileInput> validateAndSaveProtoData(String protoLocation) {
        ProtoTagUtil.isValidProtoFile(protoLocation);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(protoLocation));

            /**
             * Читается очередная строка
             * (крашится при переносе открывающей фигурной скобки в начале описания сообщения)
             */
            for (String line = reader.readLine(); line != null; ++this.lineNumber) {
                line = giveMeTheNormalLine(line);
                line = noOpeningAtTheBegining(line);

                if (hasOpen(line)){
                    line = "//" + line;
                }
       //         line = this.removeComment(line);
                this.parseLine(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException var12) {
            throw new ProtoFileValidationException("FileNotFoundException thrown in " + this.getClass().getName() + ", this should never happen", var12);
        } catch (IOException var13) {
            throw new ProtoFileValidationException("IOException thrown in " + this.getClass().getName() + ", this should never happen", var13);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException var11) {
            }

        }

        if (!this.protoParserFactory.getHasMessageEnd()) {
            ProtoFileValidationException pfve = new ProtoFileValidationException("The proto-file does not end with }");
            pfve.setLineNumber(this.lineNumber);
            throw pfve;
        } else {
            this.validateFieldIds();
            return this.protoInputList;
        }
    }

    /**
     * Приводит строку к нормальному виду
     * @param line возвращает строку, понятную для генератора
     */
    private String giveMeTheNormalLine(String line){
//        System.out.println("old line: " + line);
        line = line.replaceAll("\\{", " { ");
        line = line.replaceAll("\\}", " } ");
        line = line.replaceAll("[//].*", "");       // Убрать комментарии в строке
        line = line.replaceAll("\\s*=\\s*", " = "); // По 1 пробелу, до и после знака "="
        line = line.replaceAll("\\s*;\\s*", ";");
        line = line.replaceAll("\\s+", " ");
        line = line.trim();                               // Убрать пробелы с результирующей строки
//        System.out.println("new line: " + line);
        return line;
    }

    private String noOpeningAtTheBegining(String line){
        boolean result;
        // Строка является началом описания сообщения или перечисления?
        result = Pattern.compile("^.*(message|enum).*$").matcher(line).matches();
        if (result){
            // Есть ли в этой строке открывающая скобка \s*{\s*
            if (!Pattern.compile("[^{]*\\{[^{]*").matcher(line).matches()){
                // Если нет, то добавить
                line = line + " {";
                return line;
            }
            return line;
        }
        return line;
    }

    /**
     * Содержит ли строка пустую открывающую скобку и больше ничего
     */
    private boolean hasOpen(String line){
        return  Pattern.compile("\\s*\\{\\s*").matcher(line).matches();
    }

    private void validateFieldIds() {
        Iterator i$ = this.protoInputList.iterator();

        while (i$.hasNext()) {
            ProtoFileInput protoInput = (ProtoFileInput) i$.next();
            List<Integer> idList = new ArrayList();
            Iterator it = protoInput.getFields().iterator();

            while (it.hasNext()) {
                FieldData fieldData = (FieldData) it.next();
                int fieldId = fieldData.getId();
                if (idList.contains(fieldId)) {
                    throw new ProtoFileValidationException("Message field id must be unique, field: " + fieldData.getName());
                }

                idList.add(fieldId);
            }
        }

    }

    private String removeComment(String line) {
        String uncommentedLine;
        if (line.contains("//")) {
            uncommentedLine = line.substring(0, line.indexOf("//"));
        } else {
            uncommentedLine = line;
        }

        uncommentedLine = this.removeTrailingWhitespaces(uncommentedLine);
        return uncommentedLine;
    }

    private String removeTrailingWhitespaces(String uncommentedLine) {
        while (uncommentedLine.endsWith(" ")) {
            uncommentedLine = uncommentedLine.substring(0, uncommentedLine.length() - 1);
        }

        return uncommentedLine;
    }

    private void parseLine(String line) {
        try {
            ProtoParser protoParser = this.protoParserFactory.getProtoParser(line);
            protoParser.parseAndAddProtoFile(this.currentProtoInput);
            if (this.protoParserFactory.isNewProto()) {
                if (this.commonClassPackage == null) {
                    this.commonClassPackage = this.currentProtoInput.getPackageName();
                }

                this.protoInputList.add(this.currentProtoInput);
                this.currentProtoInput = new ProtoFileInput();
                this.currentProtoInput.setPackageName(this.commonClassPackage);
            }

        } catch (ProtoFileValidationException var3) {
            var3.setLineNumber(this.lineNumber);
            throw var3;
        }
    }
}
