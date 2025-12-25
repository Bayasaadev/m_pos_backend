package com.bank.pos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            throw new IllegalStateException("DATABASE_URL environment variable is not set");
        }

        // Render provides DATABASE_URL in format: postgresql://user:password@host:port/database
        // Convert to JDBC format: jdbc:postgresql://host:port/database
        try {
            URI dbUri = new URI(databaseUrl);
            
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String host = dbUri.getHost();
            int port = dbUri.getPort();
            String database = dbUri.getPath().substring(1); // Remove leading '/'
            
            String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
            
            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse DATABASE_URL: " + databaseUrl, e);
        }
    }
}

