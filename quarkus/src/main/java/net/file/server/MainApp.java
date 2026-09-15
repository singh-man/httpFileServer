package net.file.server;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.config.ConfigProvider;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IDE-friendly entry point for the Quarkus application.
 *
 * <p>Quarkus also supports {@code jakarta.annotation.PostConstruct} on CDI
 * beans. This entry point deliberately uses {@link QuarkusApplication} so the
 * URL message is printed after Quarkus has started accepting requests. Use
 * {@code @PostConstruct} for initializing a CDI bean; use a startup observer
 * or {@code QuarkusApplication} when the logic depends on the running server.</p>
 */
@QuarkusMain
public class MainApp {

    public static void main(String... args) {
        Quarkus.run(Application.class, args);
    }

    public static class Application implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            String port = ConfigProvider.getConfig()
                    .getOptionalValue("quarkus.http.port", String.class)
                    .orElse("8080");
            String host = localHostAddress();

            System.out.printf("Using Quarkus-OpenAPI-%n"
                    + "http://%s:%s/v3/api-docs/ %n"
                    + "http://%s:%s/swagger-ui/ %n", host, port, host, port);

            host = "localhost";
            System.out.printf("Using Quarkus-OpenAPI-%n"
                    + "http://%s:%s/v3/api-docs/ %n"
                    + "http://%s:%s/swagger-ui/ %n", host, port, host, port);
            Quarkus.waitForExit();
            return 0;
        }

        private static String localHostAddress() {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException exception) {
                return "localhost";
            }
        }
    }
}