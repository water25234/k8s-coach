package com.example.k8scoach.app;

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
