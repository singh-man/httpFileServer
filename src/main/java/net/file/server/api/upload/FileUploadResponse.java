package net.file.server.api.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FileUploadResponse {
	private String fileName;
	private String downloadUri;
	private long size;
}
