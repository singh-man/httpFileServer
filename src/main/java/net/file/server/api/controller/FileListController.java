package net.file.server.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import net.file.server.api.configuration.DefaultDirectory;
import net.file.server.api.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileListController {
    @Autowired
    private IFileService fileService;

    @Autowired
    private DefaultDirectory defaultDirectory;

    @Value("${server.port}")
    private String port;

    @Autowired
    private String host;

    @GetMapping(value = "listFiles")
    @Operation(summary = "list all available files in server!", hidden = true) // Not to show in swagger
    public ResponseEntity<List<String>> listFiles() throws IOException {
        return ResponseEntity.ok(fileService.allFiles(defaultDirectory.getPath()));
    }

    @GetMapping("filesURL")
    @Operation(summary = "gives the complete URL of the file to be downloaded")
    public ResponseEntity<List<String>> downloadFiles() throws IOException {
        var allFiles = fileService.allFiles(defaultDirectory.getPath());

        List<String> processedFiles = allFiles.stream()
                .map(e -> String.format("http://%s:%s/down/%s", host, port, e))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(processedFiles);
    }
}
