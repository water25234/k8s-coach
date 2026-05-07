# K8s Coach

A small Spring Boot 3.x website that shows a complete deployment path from local development to Docker, Docker Compose, and Kubernetes.

## Stack

- Java 21
- Spring Boot 3.x
- Spring MVC + Thymeleaf
- Spring Boot Actuator
- Docker and Docker Compose
- Kubernetes manifests

## Run Locally

Start the application:

```bash
mvn spring-boot:run
```

Run tests:

```bash
mvn test
```

Open the website:

```text
http://localhost:8080
```

Useful endpoints:

```text
http://localhost:8080/api/status
http://localhost:8080/actuator/health
```

## Docker

Build the image:

```bash
docker build -t k8s-coach:0.1.0 .
```

Run the container:

```bash
docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker k8s-coach:0.1.0
```

Verify:

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/api/status
```

## Docker Compose

Start the service:

```bash
docker compose up --build
```

Stop the service:

```bash
docker compose down
```

## Kubernetes

Build the image before deploying to a local cluster:

```bash
docker build -t k8s-coach:0.1.0 .
```

Apply the manifests:

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

Check rollout status:

```bash
kubectl rollout status deployment/k8s-coach -n k8s-coach
kubectl get pods -n k8s-coach
```

Port-forward the Service:

```bash
kubectl port-forward service/k8s-coach 8080:8080 -n k8s-coach
```

Then open:

```text
http://localhost:8080
```

Remove the deployment:

```bash
kubectl delete -f k8s/service.yaml
kubectl delete -f k8s/deployment.yaml
kubectl delete -f k8s/namespace.yaml
```
