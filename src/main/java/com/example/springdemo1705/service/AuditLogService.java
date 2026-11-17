package com.example.springdemo1705.service;

import com.example.springdemo1705.entity.AuditLog;
import com.example.springdemo1705.model.AuditLogDto;
import com.example.springdemo1705.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(String message) {
        AuditLog auditLog = new AuditLog();
        auditLog.setMessage(message);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    public List<AuditLogDto> getAllLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(log -> new AuditLogDto(
                        log.getId(),
                        log.getMessage(),
                        log.getCreatedAt()
                ))
                .toList();
    }
}

