package com.example.demo.web;

import java.util.List;

import com.example.demo.app.AppStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AppStatusService statusService;

    public HomeController(AppStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("status", statusService.current());
        model.addAttribute("technologies", List.of(
                "Java 21",
                "Spring Boot 3.x",
                "Spring MVC",
                "Thymeleaf",
                "Actuator"
        ));
        model.addAttribute("deploymentTargets", List.of(
                "Local Maven run",
                "Docker container",
                "Docker Compose",
                "Kubernetes Deployment + Service"
        ));
        return "index";
    }
}
