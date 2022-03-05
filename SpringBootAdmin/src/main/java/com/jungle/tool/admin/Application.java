package com.jungle.tool.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        showApplicationInfo(context);
    }

    private static void showApplicationInfo(ConfigurableApplicationContext context) {
        Environment env = context.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            LOGGER.warn("The host name could not be determined, using `localhost` as fallback");
        }

        String portal = env.getProperty("server.port");
        LOGGER.info("\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\t{}://localhost:{}\n\t"
                        + "External: \t{}://{}:{}\n\t"
                        + "Swagger-ui: \t{}://{}:{}/doc.html\n\t"
                        + "Profile(s): \t{}\n\t"
                        + "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                portal,
                protocol,
                hostAddress,
                portal,
                protocol,
                hostAddress,
                portal,
                env.getActiveProfiles()
        );
    }
}
