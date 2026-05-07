package com.example.demo.web;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class KubernetesGuideController {

    private static final PageLink OVERVIEW =
            new PageLink("K8s 總覽", "/kubernetes", "整體地圖、架構圖、網站流量");

    private static final List<PageLink> PAGES = List.of(
            new PageLink("Build 到 Deploy", "/kubernetes/build-to-deploy", "Java 原始碼如何變成 K8s Pod"),
            new PageLink("部署流程", "/kubernetes/deployment-pipeline", "從 git push 到 K8s 上線的完整步驟"),
            new PageLink("Image 與 command", "/kubernetes/image-command", "分清楚 image、Dockerfile ENTRYPOINT、K8s command"),
            new PageLink("kubelet 與 kubectl", "/kubernetes/kubelet", "Worker Node 如何真的啟動 Pod"),
            new PageLink("Namespace", "/kubernetes/namespace", "先建立 spring-demo 這個資源範圍"),
            new PageLink("Deployment", "/kubernetes/deployment", "宣告 Pod、container、probes、resources"),
            new PageLink("Service", "/kubernetes/service", "提供穩定入口並把流量導到 Pod")
    );

    private static final List<NavSection> NAV_SECTIONS = List.of(
            new NavSection("先看整體", List.of(
                    OVERVIEW,
                    PAGES.get(0),
                    PAGES.get(1),
                    PAGES.get(2)
            )),
            new NavSection("K8s 核心元件", List.of(
                    PAGES.get(3),
                    PAGES.get(4),
                    PAGES.get(5),
                    PAGES.get(6)
            ))
    );

    private static final List<String> PLANNED_TOPICS = List.of(
            "Ingress",
            "ConfigMap",
            "Secret",
            "HPA",
            "Helm / GitOps"
    );

    @ModelAttribute
    public void addSharedKubernetesModel(Model model, HttpServletRequest request) {
        model.addAttribute("pages", PAGES);
        model.addAttribute("navSections", NAV_SECTIONS);
        model.addAttribute("plannedTopics", PLANNED_TOPICS);
        model.addAttribute("currentPath", request.getRequestURI());
    }

    @GetMapping("/kubernetes")
    public String kubernetes(Model model) {
        model.addAttribute("commands", List.of(
                new CommandStep("1", "建立 namespace", "kubectl apply -f k8s/namespace.yaml"),
                new CommandStep("2", "建立 Deployment", "kubectl apply -f k8s/deployment.yaml"),
                new CommandStep("3", "建立 Service", "kubectl apply -f k8s/service.yaml"),
                new CommandStep("4", "確認部署完成", "kubectl rollout status deployment/deployable-spring-site -n spring-demo"),
                new CommandStep("5", "把 cluster 內的 Service 暫時轉到本機 8080", "kubectl port-forward service/deployable-spring-site 8080:8080 -n spring-demo")
        ));
        model.addAttribute("updateCommands", List.of(
                new CommandStep("1", "重新 build Docker image", "docker build -t deployable-spring-site:0.1.0 ."),
                new CommandStep("2", "重啟 Deployment，讓 Kubernetes 建立新版 Pod", "kubectl rollout restart deployment/deployable-spring-site -n spring-demo"),
                new CommandStep("3", "等待 Deployment 更新完成", "kubectl rollout status deployment/deployable-spring-site -n spring-demo"),
                new CommandStep("4", "重新建立本機到 Service 的臨時通道", "kubectl port-forward service/deployable-spring-site 8080:8080 -n spring-demo")
        ));
        return "kubernetes";
    }

    @GetMapping("/kubernetes/build-to-deploy")
    public String buildToDeploy(Model model) {
        return "kubernetes-build-to-deploy";
    }

    @GetMapping("/kubernetes/deployment-pipeline")
    public String deploymentPipeline(Model model) {
        return "kubernetes-deployment-pipeline";
    }

    @GetMapping("/kubernetes/image-command")
    public String imageCommand(Model model) {
        return "kubernetes-image-command";
    }

    @GetMapping("/kubernetes/kubelet")
    public String kubelet(Model model) {
        model.addAttribute("commands", List.of(
                new CommandStep("1", "確認 kubectl 目前連到哪個 cluster", "kubectl config current-context"),
                new CommandStep("2", "查看 cluster 資訊", "kubectl cluster-info"),
                new CommandStep("3", "查看 spring-demo 裡所有主要資源", "kubectl get all -n spring-demo"),
                new CommandStep("4", "查看 Pod 狀態", "kubectl get pods -n spring-demo -o wide"),
                new CommandStep("5", "查看 Deployment 狀態", "kubectl get deployment deployable-spring-site -n spring-demo"),
                new CommandStep("6", "查看 Service 狀態", "kubectl get service deployable-spring-site -n spring-demo"),
                new CommandStep("7", "看某個 Pod 的詳細事件", "kubectl describe pod <pod-name> -n spring-demo"),
                new CommandStep("8", "看 Spring Boot log", "kubectl logs deployment/deployable-spring-site -n spring-demo"),
                new CommandStep("9", "重啟 Deployment", "kubectl rollout restart deployment/deployable-spring-site -n spring-demo"),
                new CommandStep("10", "等待 Deployment 更新完成", "kubectl rollout status deployment/deployable-spring-site -n spring-demo"),
                new CommandStep("11", "把 Service 暫時轉到本機 8080", "kubectl port-forward service/deployable-spring-site 8080:8080 -n spring-demo"),
                new CommandStep("12", "刪除本專案 K8s 資源", "kubectl delete namespace spring-demo")
        ));
        return "kubernetes-kubelet";
    }

    @GetMapping("/kubernetes/namespace")
    public String namespace(Model model) {
        model.addAttribute("doc", new YamlDoc(
                "Namespace YAML 詳解",
                "k8s/namespace.yaml",
                "Namespace 是 Kubernetes 裡的命名範圍。這份 YAML 只做一件事：建立一個名叫 spring-demo 的空間，讓後續 Deployment 和 Service 可以放進去。",
                """
                apiVersion: v1
                kind: Namespace
                metadata:
                  name: spring-demo
                """,
                List.of(
                        new YamlSection(
                                "apiVersion: v1",
                                "使用 Kubernetes 核心 API",
                                "Namespace 屬於 Kubernetes 內建的核心資源，所以 apiVersion 使用 v1。這行不是名稱，也不是版本號給你的 app 用，而是告訴 Kubernetes 用哪一組 API 規格來解析這份 YAML。"
                        ),
                        new YamlSection(
                                "kind: Namespace",
                                "宣告要建立的資源類型",
                                "kind 代表你要建立什麼 Kubernetes 物件。這裡寫 Namespace，所以 kubectl apply 後會建立命名空間，不會建立 Pod、Service 或 Deployment。"
                        ),
                        new YamlSection(
                                "metadata.name: spring-demo",
                                "幫這個 Namespace 命名",
                                "metadata 是資源基本資料區。name: spring-demo 代表這個 Namespace 的名字。後續你會看到 deployment.yaml 和 service.yaml 都寫 namespace: spring-demo，意思就是把那些資源放進這裡。"
                        )
                ),
                List.of(
                        "kubectl apply -f k8s/namespace.yaml",
                        "kubectl get namespace spring-demo",
                        "kubectl get all -n spring-demo"
                ),
                "建立 Namespace 不會讓網站開始跑。它只是先準備一個叫 spring-demo 的範圍。真正讓 Spring Boot 跑起來的是 Deployment。"
        ));
        return "kubernetes-detail";
    }

    @GetMapping("/kubernetes/deployment")
    public String deployment(Model model) {
        model.addAttribute("doc", new YamlDoc(
                "Deployment YAML 詳解",
                "k8s/deployment.yaml",
                "Deployment 是這個專案最重要的 K8s 檔案。它描述 Spring Boot app 要跑幾份、用哪個 Docker image、container 開哪個 port、如何健康檢查、以及 CPU/記憶體限制。",
                """
                apiVersion: apps/v1
                kind: Deployment
                metadata:
                  name: deployable-spring-site
                  namespace: spring-demo
                  labels:
                    app: deployable-spring-site
                spec:
                  replicas: 2
                  selector:
                    matchLabels:
                      app: deployable-spring-site
                  template:
                    metadata:
                      labels:
                        app: deployable-spring-site
                    spec:
                      containers:
                        - name: app
                          image: deployable-spring-site:0.1.0
                          imagePullPolicy: IfNotPresent
                          ports:
                            - name: http
                              containerPort: 8080
                          env:
                            - name: SPRING_PROFILES_ACTIVE
                              value: k8s
                          readinessProbe:
                            httpGet:
                              path: /actuator/health/readiness
                              port: http
                            initialDelaySeconds: 20
                            periodSeconds: 10
                            timeoutSeconds: 3
                            failureThreshold: 3
                          livenessProbe:
                            httpGet:
                              path: /actuator/health/liveness
                              port: http
                            initialDelaySeconds: 40
                            periodSeconds: 20
                            timeoutSeconds: 3
                            failureThreshold: 3
                          resources:
                            requests:
                              cpu: 100m
                              memory: 256Mi
                            limits:
                              cpu: 500m
                              memory: 512Mi
                """,
                List.of(
                        new YamlSection("apiVersion: apps/v1 + kind: Deployment", "使用 apps API 建立 Deployment", "Deployment 不屬於核心 v1，而是 apps/v1。kind: Deployment 代表 Kubernetes 會建立一個管理 Pod 的控制器。"),
                        new YamlSection("metadata.namespace: spring-demo", "放進 spring-demo Namespace", "這行和 namespace.yaml 接起來。namespace.yaml 建立 spring-demo，這裡指定 Deployment 要放進 spring-demo。"),
                        new YamlSection("spec.replicas: 2", "維持兩個 Pod", "replicas: 2 表示 Kubernetes 會努力維持兩個相同的 Pod。若其中一個 Pod 掛掉，Deployment 會建立新的補回來。"),
                        new YamlSection("selector.matchLabels + template.metadata.labels", "Deployment 如何找到自己管理的 Pod", "selector.matchLabels 必須和 template.metadata.labels 對得上。這裡都用 app=deployable-spring-site，代表這個 Deployment 管理帶有這個 label 的 Pod。"),
                        new YamlSection("containers.image", "指定要跑的 Docker image", "image: deployable-spring-site:0.1.0 代表 Pod 裡的 container 要用這個 image。若你改了網站但沒有重 build image 或讓 cluster 取得新版 image，K8s 可能仍跑舊版。"),
                        new YamlSection("ports.containerPort: 8080", "container 內部監聽 8080", "Spring Boot 在 container 裡聽 8080。這不是把服務開到你的電腦 localhost，而是告訴 Kubernetes container 裡有一個 http port。"),
                        new YamlSection("env.SPRING_PROFILES_ACTIVE: k8s", "讓 Spring Boot 使用 k8s profile", "Pod 啟動 container 時會帶這個環境變數，所以 /api/status 會看到 profile 是 k8s。"),
                        new YamlSection("readinessProbe", "決定 Pod 能不能接流量", "Readiness probe 打 /actuator/health/readiness。失敗時 Service 不會把流量導到這個 Pod，但不一定會重啟 container。"),
                        new YamlSection("livenessProbe", "決定 container 要不要被重啟", "Liveness probe 打 /actuator/health/liveness。若持續失敗，Kubernetes 會認為 container 壞了，進行重啟。"),
                        new YamlSection("resources.requests / limits", "設定資源需求與上限", "requests 是排程時保留的基本資源，limits 是最多可用資源。這能避免服務無限制吃 CPU 或記憶體。")
                ),
                List.of(
                        "kubectl apply -f k8s/deployment.yaml",
                        "kubectl get deployment -n spring-demo",
                        "kubectl get pods -n spring-demo",
                        "kubectl describe deployment deployable-spring-site -n spring-demo",
                        "kubectl logs deployment/deployable-spring-site -n spring-demo"
                ),
                "Deployment 是讓網站真的跑起來的地方。它不負責提供穩定網址，穩定入口由 Service 負責。"
        ));
        return "kubernetes-detail";
    }

    @GetMapping("/kubernetes/service")
    public String service(Model model) {
        model.addAttribute("doc", new YamlDoc(
                "Service YAML 詳解",
                "k8s/service.yaml",
                "Service 負責提供穩定入口。Pod 可能重建、IP 可能改變，所以瀏覽器或其他服務不應該直接記 Pod IP，而是透過 Service 進來。",
                """
                apiVersion: v1
                kind: Service
                metadata:
                  name: deployable-spring-site
                  namespace: spring-demo
                  labels:
                    app: deployable-spring-site
                spec:
                  type: ClusterIP
                  selector:
                    app: deployable-spring-site
                  ports:
                    - name: http
                      port: 8080
                      targetPort: http
                """,
                List.of(
                        new YamlSection("apiVersion: v1 + kind: Service", "建立 Service 資源", "Service 是 Kubernetes 核心資源，所以使用 apiVersion: v1。kind: Service 表示這份 YAML 會建立網路入口。"),
                        new YamlSection("metadata.namespace: spring-demo", "放進 spring-demo Namespace", "Service 和 Deployment 放在同一個 Namespace，查詢和刪除時都可以用 -n spring-demo。"),
                        new YamlSection("spec.type: ClusterIP", "只開在 cluster 內部", "ClusterIP 代表這個 Service 只能在 Kubernetes cluster 內被存取。你的筆電瀏覽器不是 cluster 內部，所以需要 kubectl port-forward 才能用 localhost:8080 看到網站。"),
                        new YamlSection("selector.app: deployable-spring-site", "Service 如何找到 Pod", "Service 會找 label 是 app=deployable-spring-site 的 Pod。Deployment 建立的 Pod template 也有同樣 label，所以兩者接得上。"),
                        new YamlSection("ports.port: 8080", "Service 對外提供的 port", "在 cluster 內，其他服務可以打這個 Service 的 8080 port。"),
                        new YamlSection("ports.targetPort: http", "轉送到 Pod 的 http port", "targetPort: http 對應 Deployment 裡 ports.name: http，而那個 http port 實際是 containerPort: 8080。")
                ),
                List.of(
                        "kubectl apply -f k8s/service.yaml",
                        "kubectl get service -n spring-demo",
                        "kubectl describe service deployable-spring-site -n spring-demo",
                        "kubectl port-forward service/deployable-spring-site 8080:8080 -n spring-demo"
                ),
                "Service 不會建立 Pod，也不會跑 app。它只負責把流量導到已經存在且 label 符合的 Pod。"
        ));
        return "kubernetes-detail";
    }

    public record PageLink(String title, String href, String summary) {
    }

    public record NavSection(String title, List<PageLink> pages) {
    }

    public record CommandStep(String number, String title, String command) {
    }

    public record YamlDoc(
            String title,
            String file,
            String summary,
            String yaml,
            List<YamlSection> sections,
            List<String> commands,
            String conclusion
    ) {
    }

    public record YamlSection(String code, String title, String explanation) {
    }
}
