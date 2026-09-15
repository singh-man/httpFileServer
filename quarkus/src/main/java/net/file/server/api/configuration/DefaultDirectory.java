package net.file.server.api.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.file.Path;

@ApplicationScoped
public class DefaultDirectory {

    private Path path;

    public DefaultDirectory(@ConfigProperty(name = "file-server.directory") String directory) {
        this.path = Path.of(directory);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
