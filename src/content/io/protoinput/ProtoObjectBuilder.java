package content.io.protoinput;

import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;

import java.util.List;

public interface ProtoObjectBuilder {
    List<ProtoFileInput> validateAndSaveProtoData(String var1);
}
