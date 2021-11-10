package content.io.protoinput.options;


import content.domain.proto.ProtoFileInput;
import content.io.protoinput.AbstractProtoParser;

public final class OptionParser extends AbstractProtoParser {
    public OptionParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        String packageName = this.strings[3].substring(0, this.strings[3].length() - 1);
        packageName = packageName.replace("\"", "");
        protoInput.setPackageName(packageName);
    }
}
