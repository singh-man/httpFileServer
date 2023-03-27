package net.file.server.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class Config {

    @Bean
    public String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
