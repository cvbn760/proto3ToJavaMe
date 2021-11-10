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

            for (String line = reader.readLine(); line != null; ++this.lineNumber) {
                line = this.removeComment(line);
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
