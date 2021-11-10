package content.io.protoinput;

import content.domain.proto.ProtoFileInput;

import java.util.List;

public interface ProtoObjectBuilder {
    List<ProtoFileInput> validateAndSaveProtoData(String var1);
}
