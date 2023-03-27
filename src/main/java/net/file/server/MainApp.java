package net.file.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MainApp {

    @Value("${server.port}")
    private String port;

    @Autowired
    private String host;

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(String.format("Using Spring-Doc-OpenAPI- \n" +
                "http://%s:%s/v3/api-docs/ \n" +
                "http://%s:%s/swagger-ui/index.html \n", this.host, port, this.host, port));
    }

}
