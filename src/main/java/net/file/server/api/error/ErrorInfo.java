package net.file.server.api.error;

import javax.servlet.http.HttpServletRequest;

public record ErrorInfo(String url, String message) {

	public ErrorInfo(HttpServletRequest request, Exception ex) {
		this(request, ex.getMessage());
	}

	public ErrorInfo(HttpServletRequest request, String message) {
		this(request.getRequestURL().toString(), message);
	}
}