## File Server over HTTP - Quarkus

This is an isolated Quarkus conversion of the Spring Boot file server in the
parent project. It supports file listing, multipart upload, and file download.

Run locally with:

```shell
mvn quarkus:dev
```

You can also run `net.file.server.MainApp` directly from VS Code, Eclipse, or
IntelliJ as a standard Java application. Set an optional program argument to
override the storage directory.

At startup, `MainApp` prints the OpenAPI and Swagger UI URLs to the console.

An optional command-line argument selects the permanent file directory:

```shell
mvn quarkus:dev -Dquarkus.args=/tmp/http-file-server
```

The API documentation is available at:

- `http://localhost:8080/v3/api-docs`
- `http://localhost:8080/swagger-ui`

The original Spring Boot project remains in the parent directory.
