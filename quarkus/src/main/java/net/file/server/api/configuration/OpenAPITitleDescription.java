package net.file.server.api.configuration;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "HTTP based File Server",
                version = "1.0",
                description = "An HTTP based file server; can be used to upload media as well. "
                        + "The default directory is configured from file-server.directory.",
                contact = @Contact(
                        name = "Manish Singh",
                        email = "prataponandroid@gmail.com"
                )
        )
)
public class OpenAPITitleDescription extends Application {
}
