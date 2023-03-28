package net.file.server.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "HTTP based File Server",
                description = "" +
                        "An Http based File Server; can be used to upload media as well",
                contact = @Contact(
                        name = "Manish Singh",
//                        url = "https://reflectoring.io",
                        email = "prataponandroid@gmail.com"
                )
//                license = @License(
//                        name = "MIT Licence",
//                        url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")),
//        servers = @Server(url = "http://localhost:8080")
        ))
class OpenAPITitleDescription {
}
