package content.io.protoinput.factory;

import net.jarlehansen.proto2javame.io.protoinput.ProtoParser;

public interface ProtoParserFactory {
    ProtoParser getProtoParser(String var1);

    boolean getHasMessageEnd();

    boolean isNewProto();
}
