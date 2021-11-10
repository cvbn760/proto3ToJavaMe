package content.io.protoinput.factory;


import content.io.protoinput.ProtoParser;

public interface ProtoParserFactory {
    ProtoParser getProtoParser(String var1);

    boolean getHasMessageEnd();

    boolean isNewProto();
}
