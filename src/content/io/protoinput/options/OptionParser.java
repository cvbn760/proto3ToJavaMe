package content.io.protoinput.options;

import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;
import net.jarlehansen.proto2javame.io.protoinput.AbstractProtoParser;

public final class OptionParser extends AbstractProtoParser {
    public OptionParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        String packageName = this.strings[3].substring(0, this.strings[3].length() - 1);
        packageName = packageName.replace("\"", "");
        protoInput.setPackageName(packageName);
    }
}
