## Why

The project needs a clean baseline Spring Boot application that demonstrates a complete path from local development to containerized and Kubernetes deployment. Starting with a small but useful website keeps the first version focused while proving the Java 21, Spring Boot, Docker, Docker Compose, and Kubernetes workflow end to end.

## What Changes

- Create a Java 21 Spring Boot web application scaffold.
- Add a simple website that presents the application as a deployable Spring Boot starter site rather than a blank hello-world page.
- Provide runtime/status endpoints suitable for browser checks, API checks, and platform health checks.
- Add Docker support for building and running the application as a container image.
- Add Docker Compose support for local container-based execution.
- Add Kubernetes manifests for deploying the application and exposing it through a Service.
- Add project documentation covering local, Docker, Compose, and Kubernetes usage.

## Capabilities

### New Capabilities
- `deployable-spring-site`: A Java 21 Spring Boot website that can run locally, in Docker, through Docker Compose, and on Kubernetes.

### Modified Capabilities

None.

## Impact

- Adds a new Spring Boot project structure, including Maven configuration, application source, templates, static assets, and tests.
- Adds deployment artifacts including `Dockerfile`, Docker Compose configuration, and Kubernetes manifests.
- Adds operational endpoints for application status and health verification.
- Adds documentation for developers and operators to build, run, and deploy the application.
