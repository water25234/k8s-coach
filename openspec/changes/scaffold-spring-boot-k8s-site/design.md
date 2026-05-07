## Context

The repository currently contains OpenSpec artifacts but no application code. This change will introduce a new Java 21 Spring Boot project that acts as a deployable baseline website and proves the path from local execution to Docker, Docker Compose, and Kubernetes.

The application is intentionally small: it should be easy to understand, quick to build, and useful as a starting point for later features. The website content will present the application as a cloud-ready Spring Boot starter rather than a generic hello-world page.

## Goals / Non-Goals

**Goals:**
- Build a Java 21 Spring Boot application using Spring Boot 3.x.
- Provide a browser-rendered website with a polished but simple deployment-focused landing/dashboard page.
- Provide machine-readable status and health endpoints for local, container, and Kubernetes verification.
- Support local execution with Maven.
- Support container execution with Docker and Docker Compose.
- Support Kubernetes deployment with standard manifests.
- Document the main build, run, and deploy commands.

**Non-Goals:**
- Add database persistence.
- Add authentication or user accounts.
- Add CI/CD pipeline configuration.
- Add cloud-provider-specific Kubernetes resources such as Ingress controllers, managed certificates, or cloud load balancer annotations.
- Build a large multi-page marketing website.

## Decisions

### Use Maven for the Java project

Maven will be used because it is conventional for Spring Boot starters, has first-class Spring Initializr support, and keeps the generated project approachable.

Alternative considered: Gradle. Gradle is also well supported, but Maven is simpler for a small starter project and avoids extra build-script decisions.

### Use Spring MVC with Thymeleaf for the website

The homepage will be rendered server-side with Thymeleaf. This keeps the project focused on Spring Framework and Spring Boot without introducing a frontend build toolchain.

Alternative considered: static HTML only. Static HTML would be simpler, but Thymeleaf demonstrates Spring MVC integration and allows runtime values such as active profile, Java version, and application version to be shown in the page.

Alternative considered: React/Vite frontend. A separate frontend stack is unnecessary for a simple deployable starter and would add build and container complexity outside the current goal.

### Use Spring Boot Actuator for health checks

Spring Boot Actuator will provide `/actuator/health` for platform health verification. The application will also expose a small `/api/status` endpoint with runtime metadata for human and API checks.

Alternative considered: custom-only health endpoint. Actuator is the standard Spring Boot approach and maps better to Docker and Kubernetes conventions.

### Use a multi-stage Dockerfile

The Dockerfile will build the application in a Maven image and run it in a smaller Java 21 runtime image. This keeps local container builds self-contained and avoids requiring a pre-built JAR on the host.

Alternative considered: copy a locally built JAR into the image. That is faster after a local build, but it makes the Docker image build depend on a separate host command.

### Use simple Kubernetes manifests

Kubernetes support will include a Deployment and Service. The Deployment will define container port, readiness/liveness probes, and basic resource requests/limits. The Service will expose the application inside the cluster by default.

Alternative considered: Helm chart or Kustomize. Those are useful later, but plain manifests are more transparent for the first deployable baseline.

## Risks / Trade-offs

- Runtime metadata may differ between local, Docker, and Kubernetes environments -> Keep displayed fields simple and derived from Spring environment and JVM properties.
- Multi-stage Docker builds can be slower on first run -> Docker layer caching will improve rebuilds, and the first version prioritizes reproducibility.
- Kubernetes Service type choice depends on the target cluster -> Use `ClusterIP` as the portable default and document how to port-forward for local testing.
- Actuator exposes operational endpoints -> Include only the health endpoint by default and avoid exposing sensitive management data.

## Migration Plan

This is a new project scaffold, so no data migration is required.

Implementation rollout:
- Add the Spring Boot project and verify local tests.
- Add Dockerfile and verify image build/run.
- Add Docker Compose and verify compose startup.
- Add Kubernetes manifests and verify they are syntactically deployable.

Rollback:
- Remove the added application, container, Kubernetes, and documentation files if the scaffold is not accepted.

## Open Questions

- The initial Kubernetes exposure will use a portable `ClusterIP` Service. If the target environment needs public access, a later change can add Ingress or a cloud-specific Service type.
