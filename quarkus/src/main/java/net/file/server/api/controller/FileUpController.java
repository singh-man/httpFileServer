package net.file.server.api.controller;

import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.file.server.api.service.IFileService;
import net.file.server.api.upload.FileUploadResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Path("/")
@Blocking
public class FileUpController {

    private final IFileService fileService;

    public FileUpController(IFileService fileService) {
        this.fileService = fileService;
    }

    @POST
    @Path("uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "uploads one file/photo/video to server", hidden = true)
    public FileUploadResponse uploadFile(@RestForm("file") FileUpload file) throws IOException {
        if (file == null || file.fileName() == null) {
            throw new IllegalArgumentException("A file is required");
        }

        java.nio.file.Path uploadedFile = file.filePath();
        long size = Files.size(uploadedFile);
        String fileCode = fileService.saveFile(file.fileName(), uploadedFile);
        return new FileUploadResponse(file.fileName(), "/down/" + fileCode, size);
    }

    @POST
    @Path("uploadFiles")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "uploads multiple files/photos/videos to server")
    public List<FileUploadResponse> uploadFiles(@RestForm("files") List<FileUpload> files)
            throws IOException {
        List<FileUploadResponse> responses = new ArrayList<>();
        for (FileUpload file : files) {
            if (file == null || file.fileName() == null) {
                throw new IllegalArgumentException("A file is required");
            }

            java.nio.file.Path uploadedFile = file.filePath();
            long size = Files.size(uploadedFile);
            String fileCode = fileService.saveFile(file.fileName(), uploadedFile);
            responses.add(new FileUploadResponse(file.fileName(), "/down/" + fileCode, size));
        }
        return responses;
    }

    @GET
    @Path("down/{fileCode}")
    @Operation(summary = "download the file", hidden = true)
    public Response downloadFile(@PathParam("fileCode") String fileCode) {
        try {
            java.nio.file.Path file = fileService.fetchFile(fileCode);
            if (file == null) {
                throw new NotFoundException("File not found");
            }

            return Response.ok(file)
                    .type(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .build();
        } catch (IOException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
