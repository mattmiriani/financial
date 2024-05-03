package com.matt.financial;

import com.matt.financial.config.properties.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static java.util.Objects.nonNull;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class FinancialApplication {

    private static final Logger log = LoggerFactory.getLogger(FinancialApplication.class);

    public static void main(String[] args) {
        var app = new SpringApplication(FinancialApplication.class);
        var env = app.run(args).getEnvironment();
        var protocol = "http";

        if (nonNull(env.getProperty("server.ssl.key-store"))) {
            protocol = "https";
        }

		log.info("""
                \n\t----------------------------------------------------------
                \tLocal: \t\t{}://localhost:{}
                \t----------------------------------------------------------
                """,
                protocol,
				env.getProperty("server.port")
        );
    }

}
