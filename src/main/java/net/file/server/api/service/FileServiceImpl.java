package net.file.server.api.service;

import lombok.extern.slf4j.Slf4j;
import net.file.server.api.configuration.DefaultDirectory;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    @Autowired
    DefaultDirectory defaultDirectory;

    @Override
    public List<String> allFiles(Path path) throws IOException {
        log.info("Getting all files listed under: " + path.toString());
        Path uploadDirectory = defaultDirectory.getPath();
        List<String> allFiles = Files.list(uploadDirectory).filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        return allFiles;
    }

    @Override
    public String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        log.info("Saving file: " + fileName);
        String fileCode = RandomStringUtils.randomAlphanumeric(8);
        String newFileName = String.format("%s-%s.%s", FilenameUtils.removeExtension(fileName),
                fileCode, FilenameUtils.getExtension(fileName));
        newFileName = replaceSpacesWithdDash(newFileName);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = defaultDirectory.getPath().resolve(newFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException io) {
            log.info("Encountered Issue: " + io.getLocalizedMessage());
            throw new IOException("Error saving uploaded file: " + fileName, io);
        }
        return fileCode;
    }

    @Override
    public Resource fetchFileAsResource(String fileCode) throws IOException {
        log.info("Fetching file with name or with code: " + fileCode);
        Path file = Files.list(defaultDirectory.getPath())
                .filter(e -> e.getFileName().toString().contains(fileCode))
                .findFirst()
                .orElseThrow(() -> new IOException("No such file present"));
        return new UrlResource(file.toUri());
    }
}
