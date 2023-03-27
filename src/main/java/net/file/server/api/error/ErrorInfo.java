package net.file.server.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Getter
public class ErrorInfo {
	private final String url;
	private final String message;

	public ErrorInfo(HttpServletRequest request, Exception ex) {
		this(request, ex.getMessage());
	}
	
	public ErrorInfo(HttpServletRequest request, String message) {
		this.url = request.getRequestURL().toString();
		this.message = message;
	}		
}