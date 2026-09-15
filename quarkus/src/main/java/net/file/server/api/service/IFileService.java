package net.file.server.api.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IFileService {

    List<String> allFiles(Path path) throws IOException;

    String saveFile(String fileName, Path uploadedFile) throws IOException;

    Path fetchFile(String fileCode) throws IOException;
}
