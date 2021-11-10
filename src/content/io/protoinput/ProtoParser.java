package content.io.protoinput;

import content.domain.proto.ProtoFileInput;

public interface ProtoParser {
    void setParserLine(String var1);

    void parseAndAddProtoFile(ProtoFileInput var1);
}
