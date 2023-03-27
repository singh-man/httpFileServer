package net.file.server.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import net.file.server.api.service.IFileService;
import net.file.server.api.upload.FileUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUpController {

    @Autowired
    private IFileService fileService;

    /**
     * MultipartFile[] wasn't working with swagger
     */
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "uploads one file/photo/video to server", hidden = true)
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        String fileCode = fileService.saveFile(fileName, multipartFile);
        return new ResponseEntity<>(new FileUploadResponse(fileName,
                "/downloadFile/" + fileCode, size), HttpStatus.OK);
    }

    @PostMapping(value = "/uploadFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "uploads multiple files/photos/videos to server")
    public ResponseEntity<List<FileUploadResponse>> uploadFiles(
            @RequestParam("files") List<MultipartFile> multipartFiles) throws IOException {
        List<FileUploadResponse> res = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();
            String fileCode = fileService.saveFile(fileName, multipartFile);
            res.add(new FileUploadResponse(fileName, "/downloadFile/" + fileCode, size));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("down/{fileCode}")
    @Operation(summary = "download the file", hidden = true) // Not to show in swagger
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        Resource resource = null;

        try {
            resource = fileService.fetchFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
