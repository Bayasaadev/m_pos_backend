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
        // Or sometimes: postgresql://user:password@host/database (no port)
        // Convert to JDBC format: jdbc:postgresql://host:port/database
        try {
            URI dbUri = new URI(databaseUrl);
            
            String userInfo = dbUri.getUserInfo();
            if (userInfo == null || !userInfo.contains(":")) {
                throw new IllegalStateException("DATABASE_URL must contain username:password");
            }
            
            String[] credentials = userInfo.split(":", 2);
            String username = credentials[0];
            String password = credentials.length > 1 ? credentials[1] : "";
            
            String host = dbUri.getHost();
            if (host == null) {
                throw new IllegalStateException("DATABASE_URL must contain a valid host");
            }
            
            int port = dbUri.getPort();
            // If port is -1 (not specified), use default PostgreSQL port 5432
            if (port == -1) {
                port = 5432;
            }
            
            String path = dbUri.getPath();
            if (path == null || path.isEmpty() || path.equals("/")) {
                throw new IllegalStateException("DATABASE_URL must contain a database name");
            }
            String database = path.startsWith("/") ? path.substring(1) : path;
            
            String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
            
            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse DATABASE_URL: " + databaseUrl + ". Error: " + e.getMessage(), e);
        }
    }
}

