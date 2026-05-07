package com.example.demo.app;

import java.time.Instant;

public record AppStatus(
        String application,
        String version,
        String profile,
        String javaVersion,
        String status,
        Instant checkedAt
) {
}
