package com.example.demo.app;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AppStatusService {

    private final Environment environment;
    private final String applicationName;
    private final String applicationVersion;

    public AppStatusService(
            Environment environment,
            @Value("${app.name}") String applicationName,
            @Value("${app.version}") String applicationVersion
    ) {
        this.environment = environment;
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
    }

    public AppStatus current() {
        return new AppStatus(
                applicationName,
                applicationVersion,
                activeProfile(),
                Runtime.version().toString(),
                "UP",
                Instant.now()
        );
    }

    private String activeProfile() {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
            return "default";
        }
        return String.join(",", Arrays.asList(profiles));
    }
}
