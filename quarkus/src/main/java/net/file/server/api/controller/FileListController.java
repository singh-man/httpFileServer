package net.file.server.api.controller;

import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import net.file.server.api.configuration.DefaultDirectory;
import net.file.server.api.service.IFileService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.io.IOException;
import java.util.List;

@Path("/")
@Blocking
public class FileListController {

    private final IFileService fileService;
    private final DefaultDirectory defaultDirectory;

    public FileListController(IFileService fileService, DefaultDirectory defaultDirectory) {
        this.fileService = fileService;
        this.defaultDirectory = defaultDirectory;
    }

    @GET
    @Path("listFiles")
    @Operation(summary = "list all available files in server!", hidden = true)
    public List<String> listFiles() throws IOException {
        return fileService.allFiles(defaultDirectory.getPath());
    }

    @GET
    @Path("filesURL")
    @Operation(summary = "gives the complete URL of the file to be downloaded")
    public List<String> downloadFiles(@Context UriInfo uriInfo) throws IOException {
        UriBuilder baseUri = UriBuilder.fromUri(uriInfo.getBaseUri());
        return fileService.allFiles(defaultDirectory.getPath()).stream()
                .map(file -> baseUri.clone().path("down").path(file).build().toString())
                .toList();
    }
}
