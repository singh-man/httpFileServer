package net.file.server.api;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.CommandLineArguments;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import net.file.server.api.configuration.DefaultDirectory;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class DirectoryInit {

    private static final Logger LOG = Logger.getLogger(DirectoryInit.class);

    private final DefaultDirectory defaultDirectory;
    private final String[] args;

    public DirectoryInit(DefaultDirectory defaultDirectory,
                         @CommandLineArguments String[] args) {
        this.defaultDirectory = defaultDirectory;
        this.args = args;
    }

    void onStart(@Observes StartupEvent event) throws IOException {
        if (args.length > 1) {
            throw new IllegalArgumentException("Only 1 directory path is allowed");
        }

        Path directory = args.length == 1 ? Path.of(args[0]) : defaultDirectory.getPath();
        if (Files.exists(directory) && !Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Must be a directory");
        }

        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
            LOG.infof("New directory created for file server: %s", directory);
        }

        defaultDirectory.setPath(directory);
    }
}
