package content.io.protoinput;

import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;

public interface ProtoParser {
    void setParserLine(String var1);

    void parseAndAddProtoFile(ProtoFileInput var1);
}
