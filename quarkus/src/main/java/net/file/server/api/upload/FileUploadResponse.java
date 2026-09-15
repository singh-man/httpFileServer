package net.file.server.api.upload;

public record FileUploadResponse(String fileName, String downloadUri, long size) {
}
