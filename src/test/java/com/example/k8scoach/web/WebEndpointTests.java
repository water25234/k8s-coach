package com.example.k8scoach.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WebEndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homepageRendersDeploymentFocusedContent() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("K8s Coach")))
                .andExpect(content().string(containsString("Deployment Targets")))
                .andExpect(content().string(containsString("Kubernetes Deployment + Service")))
                .andExpect(content().string(containsString("Open Kubernetes guide")));
    }

    @Test
    void statusEndpointReturnsRuntimeMetadata() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").value("K8s Coach"))
                .andExpect(jsonPath("$.version").value("0.1.0"))
                .andExpect(jsonPath("$.profile").value("default"))
                .andExpect(jsonPath("$.javaVersion").exists())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void actuatorHealthEndpointIsAvailable() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void kubernetesGuideExplainsCoreResources() throws Exception {
        mockMvc.perform(get("/kubernetes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("這個 Spring Boot 網站在 K8s 裡怎麼運作")))
                .andExpect(content().string(containsString("Kubernetes 教學")))
                .andExpect(content().string(containsString("左側切換主題，右側閱讀內容")))
                .andExpect(content().string(containsString("先看整體")))
                .andExpect(content().string(containsString("K8s 核心元件")))
                .andExpect(content().string(containsString("後續預留主題")))
                .andExpect(content().string(containsString("未來新增 Ingress、ConfigMap、Secret、HPA 等主題")))
                .andExpect(content().string(matchesPattern("(?s).*這個 Spring Boot 網站在 K8s 裡怎麼運作.*建議閱讀順序.*從檔案到部署的主線流程.*")))
                .andExpect(content().string(containsString("從檔案到部署的主線流程")))
                .andExpect(content().string(containsString("Dockerfile 建立 image")))
                .andExpect(content().string(containsString("docker-compose.yml 本機跑 container")))
                .andExpect(content().string(containsString("deployment.yaml 讓 K8s 跑 Pod")))
                .andExpect(content().string(containsString("service.yaml 提供 Pod 入口")))
                .andExpect(content().string(containsString("K8s 架構總覽")))
                .andExpect(content().string(containsString("/images/k8s-architecture.png")))
                .andExpect(content().string(containsString("Control Plane")))
                .andExpect(content().string(containsString("Master 是舊稱")))
                .andExpect(content().string(containsString("Scheduler")))
                .andExpect(content().string(containsString("Controller Manager")))
                .andExpect(content().string(containsString("API Server")))
                .andExpect(content().string(containsString("etcd")))
                .andExpect(content().string(containsString("Worker Nodes")))
                .andExpect(content().string(containsString("Worker Node A")))
                .andExpect(content().string(containsString("kubelet")))
                .andExpect(content().string(containsString("Container Runtime")))
                .andExpect(content().string(containsString("本專案網站流量")))
                .andExpect(content().string(containsString("建議閱讀順序")))
                .andExpect(content().string(containsString("修改網站後重新部署")))
                .andExpect(content().string(containsString("docker build -t k8s-coach:0.1.0 .")))
                .andExpect(content().string(containsString("kubectl rollout restart deployment/k8s-coach -n k8s-coach")))
                .andExpect(content().string(containsString("/kubernetes/build-to-deploy")))
                .andExpect(content().string(containsString("/kubernetes/deployment-pipeline")))
                .andExpect(content().string(containsString("/kubernetes/image-command")))
                .andExpect(content().string(containsString("/kubernetes/kubelet")))
                .andExpect(content().string(containsString("/kubernetes/namespace")))
                .andExpect(content().string(containsString("/kubernetes/deployment")))
                .andExpect(content().string(containsString("/kubernetes/service")));
    }

    @Test
    void buildToDeployGuideExplainsImageAndDeploymentFlow() throws Exception {
        mockMvc.perform(get("/kubernetes/build-to-deploy"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("aria-current=\"page\"")))
                .andExpect(content().string(containsString("Java 程式如何變成 Kubernetes 裡的 Pod")))
                .andExpect(content().string(containsString("Dockerfile 打包 application")))
                .andExpect(content().string(containsString("建立 image 並加上 tag")))
                .andExpect(content().string(containsString("Deployment 指定 image")))
                .andExpect(content().string(containsString("docker build -t k8s-coach:0.1.0 .")))
                .andExpect(content().string(containsString("image: k8s-coach:0.1.0")))
                .andExpect(content().string(containsString("確認 cluster 拿得到新版 image")))
                .andExpect(content().string(containsString("kubectl rollout restart deployment/k8s-coach -n k8s-coach")));
    }

    @Test
    void deploymentPipelineGuideExplainsCicdFlow() throws Exception {
        mockMvc.perform(get("/kubernetes/deployment-pipeline"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("從 git push 到 Kubernetes 上線")))
                .andExpect(content().string(containsString("公司常見 CI/CD 部署流程")))
                .andExpect(content().string(containsString("Image Registry")))
                .andExpect(content().string(containsString("本機 cluster 到底去哪裡拿 image")))
                .andExpect(content().string(containsString("Docker Desktop Kubernetes")))
                .andExpect(content().string(containsString("minikube image load k8s-coach:0.1.0")))
                .andExpect(content().string(containsString("kind load docker-image k8s-coach:0.1.0")))
                .andExpect(content().string(containsString("CI/CD system flow")))
                .andExpect(content().string(containsString("CI docker build")))
                .andExpect(content().string(containsString("CI docker push")))
                .andExpect(content().string(containsString("CD 更新 image tag")))
                .andExpect(content().string(containsString("固定 tag 與 rollout restart 的陷阱")))
                .andExpect(content().string(containsString("正式環境怎麼讓使用者進來")))
                .andExpect(content().string(containsString("deployment.yaml 在 pipeline 裡做什麼")))
                .andExpect(content().string(containsString("docker push registry.example.com/k8s-coach/k8s-coach:&lt;git-sha&gt;")))
                .andExpect(content().string(containsString("kubectl rollout undo deployment/k8s-coach -n k8s-coach")))
                .andExpect(content().string(containsString("Kubernetes 只負責拉 image 並執行 Pod")));
    }

    @Test
    void imageCommandGuideExplainsImageCommandAndEntrypoint() throws Exception {
        mockMvc.perform(get("/kubernetes/image-command"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("image 是來源，command 是啟動指令")))
                .andExpect(content().string(containsString("Dockerfile")))
                .andExpect(content().string(containsString("ENTRYPOINT")))
                .andExpect(content().string(containsString("command")))
                .andExpect(content().string(containsString("args")))
                .andExpect(content().string(containsString("Container 啟動 system flow")))
                .andExpect(content().string(containsString("deployment.yaml 沒有 command")))
                .andExpect(content().string(containsString("你看到的 HAPI FHIR YAML 為什麼有 command")))
                .andExpect(content().string(containsString("如果 image 裡沒有")))
                .andExpect(content().string(containsString("/app/main.war")))
                .andExpect(content().string(containsString("Pod 會啟動失敗")));
    }

    @Test
    void kubeletGuideExplainsWorkerNodeRoleAndKubectlCommands() throws Exception {
        mockMvc.perform(get("/kubernetes/kubelet"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("kubelet 是 Worker Node 上的現場管理員")))
                .andExpect(content().string(containsString("先分清楚 kubectl 和 kubelet")))
                .andExpect(content().string(containsString("Container Runtime")))
                .andExpect(content().string(containsString("kubelet 請 Runtime 啟動 container")))
                .andExpect(content().string(containsString("kubectl 常用指令")))
                .andExpect(content().string(containsString("kubectl get pods -n k8s-coach -o wide")))
                .andExpect(content().string(containsString("kubectl describe pod &lt;pod-name&gt; -n k8s-coach")))
                .andExpect(content().string(containsString("kubectl logs deployment/k8s-coach -n k8s-coach")))
                .andExpect(content().string(containsString("kubectl delete namespace k8s-coach")));
    }

    @Test
    void namespaceGuideExplainsNamespaceYaml() throws Exception {
        mockMvc.perform(get("/kubernetes/namespace"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Namespace YAML 詳解")))
                .andExpect(content().string(containsString("apiVersion: v1")))
                .andExpect(content().string(containsString("kind: Namespace")))
                .andExpect(content().string(containsString("metadata.name: k8s-coach")));
    }

    @Test
    void deploymentGuideExplainsDeploymentYaml() throws Exception {
        mockMvc.perform(get("/kubernetes/deployment"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Deployment YAML 詳解")))
                .andExpect(content().string(containsString("replicas: 2")))
                .andExpect(content().string(containsString("readinessProbe")))
                .andExpect(content().string(containsString("livenessProbe")))
                .andExpect(content().string(containsString("resources.requests / limits")));
    }

    @Test
    void serviceGuideExplainsServiceYaml() throws Exception {
        mockMvc.perform(get("/kubernetes/service"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Service YAML 詳解")))
                .andExpect(content().string(containsString("type: ClusterIP")))
                .andExpect(content().string(containsString("selector.app: k8s-coach")))
                .andExpect(content().string(containsString("targetPort: http")));
    }
}
