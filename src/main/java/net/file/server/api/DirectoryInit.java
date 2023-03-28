package net.file.server.api;

import net.file.server.api.configuration.DefaultDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class DirectoryInit implements ApplicationRunner {

    @Autowired
    DefaultDirectory defaultDirectory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> nonOptionArgs = args.getNonOptionArgs();

        if (nonOptionArgs.size() > 1) throw new RuntimeException("Only 1 directory path is allowed");

        String dir = nonOptionArgs.size() == 1 ? nonOptionArgs.get(0) : defaultDirectory.getPath().toString();

        File file = new File(dir);
        if (file.exists()) {
            if (file.isFile()) throw new RuntimeException("Must be a directory");
        } else {
            file.mkdirs();
            defaultDirectory.setPath(Path.of(file.getPath()));
        }
    }
}
