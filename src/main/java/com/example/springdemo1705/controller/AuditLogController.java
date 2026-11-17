package com.example.springdemo1705.controller;

import com.example.springdemo1705.model.AuditLogDto;
import com.example.springdemo1705.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLogDto> getAllLogs() {
        return auditLogService.getAllLogs();
    }
}