package net.file.server.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import net.file.server.api.configuration.DefaultDirectory;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@ApplicationScoped
public class FileServiceImpl implements IFileService {

    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);

    private final DefaultDirectory defaultDirectory;

    public FileServiceImpl(DefaultDirectory defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    @Override
    public List<String> allFiles(Path path) throws IOException {
        LOG.infof("Getting all files listed under: %s", path);
        try (var files = Files.list(path)) {
            return files.filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        }
    }

    @Override
    public String saveFile(String fileName, Path uploadedFile) throws IOException {
        LOG.infof("Saving file: %s", fileName);
        String fileCode = randomCode();
        String newFileName = buildFileName(fileName, fileCode).replace(' ', '-');

        try {
            Path filePath = defaultDirectory.getPath().resolve(newFileName);
            Files.copy(uploadedFile, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            LOG.warnf(exception, "Encountered issue while saving %s", fileName);
            throw new IOException("Error saving uploaded file: " + fileName, exception);
        }
        return fileCode;
    }

    @Override
    public Path fetchFile(String fileCode) throws IOException {
        LOG.infof("Fetching file with code: %s", fileCode);
        try (var files = Files.list(defaultDirectory.getPath())) {
            return files.filter(file -> file.getFileName().toString().contains(fileCode))
                    .findFirst()
                    .orElse(null);
        }
    }

    private static String randomCode() {
        return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    private static String buildFileName(String fileName, String fileCode) {
        int extensionStart = fileName.lastIndexOf('.');
        if (extensionStart <= 0 || extensionStart == fileName.length() - 1) {
            return fileName + "-" + fileCode;
        }

        return fileName.substring(0, extensionStart) + "-" + fileCode
                + fileName.substring(extensionStart);
    }
}
