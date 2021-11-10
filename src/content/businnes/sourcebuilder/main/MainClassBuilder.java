package content.businnes.sourcebuilder.main;

import content.domain.proto.ProtoFileInput;

public interface MainClassBuilder {
    StringBuilder createMainClass(String var1, ProtoFileInput var2);
}
