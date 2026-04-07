package com.employee.backend.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DotEnvPostProcessor implements EnvironmentPostProcessor {

    private static final String DOT_ENV_PROPERTY_SOURCE_NAME = "dotenv";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        File envFile = new File(".env");
        if (!envFile.exists()) {
            return;
        }

        Map<String, Object> properties = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(envFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                int separatorIndex = line.indexOf('=');
                if (separatorIndex > 0) {
                    String key = line.substring(0, separatorIndex).trim();
                    String value = line.substring(separatorIndex + 1).trim();
                    properties.put(key, value);
                }
            }
        } catch (IOException e) {
            // If the .env file cannot be read, continue without it
        }

        if (!properties.isEmpty()) {
            // Add with lowest priority so OS env vars and system properties still take precedence
            environment.getPropertySources().addLast(
                    new MapPropertySource(DOT_ENV_PROPERTY_SOURCE_NAME, properties)
            );
        }
    }
}
