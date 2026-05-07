# Repository Guidelines

## Project Structure & Module Organization

This repository contains a Java 21 Spring Boot website with container and Kubernetes deployment assets.

- `src/main/java/com/example/k8scoach/`: Spring Boot application code, controllers, and app services.
- `src/main/resources/templates/`: Thymeleaf HTML views.
- `src/main/resources/static/`: static assets such as CSS.
- `src/main/resources/application.yml`: application and Actuator configuration.
- `src/test/java/com/example/k8scoach/`: JUnit and Spring Boot tests.
- `Dockerfile`, `docker-compose.yml`: container build and local Compose runtime.
- `k8s/`: Kubernetes namespace, Deployment, and Service manifests.
- `openspec/`: OpenSpec proposal, design, specs, and task tracking.

## Build, Test, and Development Commands

Run locally with Maven:

```bash
mvn spring-boot:run
```

Run the test suite:

```bash
mvn test
```

Build and run with Docker:

```bash
docker build -t k8s-coach:0.1.0 .
docker run --rm -p 8080:8080 k8s-coach:0.1.0
```

Start the Compose service:

```bash
docker compose up --build
```

Deploy to Kubernetes:

```bash
kubectl apply -f k8s/namespace.yaml -f k8s/deployment.yaml -f k8s/service.yaml
```

## Coding Style & Naming Conventions

Use standard Java conventions: 4-space indentation, `PascalCase` for classes and records, `camelCase` for methods and fields, and lowercase package names. Keep controllers in `web`, application support code in `app`, and avoid adding broad abstractions before they are needed. HTML and CSS should stay concise and server-rendered through Thymeleaf.

## Testing Guidelines

Tests use JUnit 5, Spring Boot Test, and MockMvc. Name test classes with the `*Tests` suffix, and write endpoint tests for new routes. Run `mvn test` before submitting changes. If Maven is unavailable locally, run tests through Docker using the Maven Java 21 image.

## Commit & Pull Request Guidelines

There is no existing commit history yet. Use concise imperative commit messages, for example `Add status endpoint tests` or `Update Kubernetes probes`. Pull requests should include a short summary, verification commands run, linked issue or OpenSpec change when applicable, and screenshots for visible website changes.

## Security & Configuration Tips

Do not expose sensitive Actuator endpoints by default. Keep Kubernetes manifests portable unless a change explicitly targets a specific cloud provider.
