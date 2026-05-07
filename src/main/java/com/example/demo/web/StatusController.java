package com.example.demo.web;

import com.example.demo.app.AppStatus;
import com.example.demo.app.AppStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private final AppStatusService statusService;

    public StatusController(AppStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/api/status")
    public AppStatus status() {
        return statusService.current();
    }
}
