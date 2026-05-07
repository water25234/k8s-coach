## 1. Project Scaffold

- [x] 1.1 Create Maven-based Java 21 Spring Boot project structure.
- [x] 1.2 Add Spring Boot dependencies for web MVC, Thymeleaf, Actuator, and tests.
- [x] 1.3 Configure application metadata, default server port, and management health endpoint exposure.

## 2. Website and Runtime Endpoints

- [x] 2.1 Implement the main Spring Boot application entry point.
- [x] 2.2 Implement a web controller that renders the homepage at `/`.
- [x] 2.3 Create a Thymeleaf homepage showing application name, technology stack, deployment targets, and runtime status.
- [x] 2.4 Add static CSS for a clean deployment-dashboard style layout.
- [x] 2.5 Implement `/api/status` JSON endpoint with application name, version, active profile, Java version, and status.

## 3. Tests

- [x] 3.1 Add a context-load test for the Spring Boot application.
- [x] 3.2 Add web tests verifying the homepage and `/api/status` endpoint.
- [x] 3.3 Verify Actuator health endpoint is available in tests or local runtime.

## 4. Docker and Compose

- [x] 4.1 Add a multi-stage Dockerfile that builds with Maven and runs on a Java 21 runtime image.
- [x] 4.2 Add `.dockerignore` to keep Docker build context small.
- [x] 4.3 Add Docker Compose configuration exposing the application on the documented port.
- [x] 4.4 Verify Docker image build and container startup commands.
- [x] 4.5 Verify Docker Compose startup command.

## 5. Kubernetes

- [x] 5.1 Add Kubernetes Deployment manifest with container port, resource requests/limits, and readiness/liveness probes.
- [x] 5.2 Add Kubernetes Service manifest exposing the application within the cluster.
- [x] 5.3 Add namespace or manifest organization if useful for applying the deployment cleanly.
- [x] 5.4 Document Kubernetes apply and port-forward commands.

## 6. Documentation and Verification

- [x] 6.1 Add README instructions for local Maven run/test commands.
- [x] 6.2 Add README instructions for Docker build/run and Docker Compose usage.
- [x] 6.3 Add README instructions for Kubernetes deployment and verification.
- [x] 6.4 Run the project test suite and record any limitations if external tools are unavailable.
