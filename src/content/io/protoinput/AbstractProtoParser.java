package content.io.protoinput;

import content.domain.proto.ProtoFileInput;
import content.io.protoinput.util.LineSplitterUtil;

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
