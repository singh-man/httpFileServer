package net.file.server.api.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IFileService {

    List<String> allFiles(Path path) throws IOException;

    String saveFile(String fileName, MultipartFile multipartFile) throws IOException;

    Resource fetchFileAsResource(String fileCode) throws IOException;

    default String replaceSpacesWithdDash(String text) {
        return Stream.of(text.split(" ")).collect(Collectors.joining("-"));
    }

}
