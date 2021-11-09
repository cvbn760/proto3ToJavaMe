package content.modules.io;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.jarlehansen.proto2javame.modules.business.CodeGeneratorModule;
import net.jarlehansen.proto2javame.modules.io.IoModule;

public enum DependencyInjector {
    MAIN;

    private final Injector injector = Guice.createInjector(new CodeGeneratorModule(), new IoModule());

    DependencyInjector() {
    }

    public <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}

