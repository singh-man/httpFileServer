package net.file.server.api.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Setter
@Getter
public class DefaultDirectory {

    private Path path;

    DefaultDirectory() {
        this.path = Paths.get(System.getProperty("user.home") + "/mani/xx/");
    }
}
