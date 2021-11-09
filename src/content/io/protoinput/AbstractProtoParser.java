package content.io.protoinput;

import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;
import net.jarlehansen.proto2javame.io.protoinput.ProtoParser;
import net.jarlehansen.proto2javame.io.protoinput.util.LineSplitterUtil;

public abstract class AbstractProtoParser implements ProtoParser {
    protected String line;
    protected String[] strings;

    public AbstractProtoParser() {
    }

    public void setParserLine(String line) {
        this.line = line;
        this.strings = LineSplitterUtil.split(line);
    }

    public abstract void parseAndAddProtoFile(ProtoFileInput var1);
}
