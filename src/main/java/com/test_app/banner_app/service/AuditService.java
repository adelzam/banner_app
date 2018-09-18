package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.repositories.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    void saveAuditLine(Audit audit){
        auditRepository.save(audit);
    }

    public void updateAuditDeleted(Banner banner) {
        List<Audit> auditList = auditRepository.findAuditsByBanner(banner);
        for (Audit audit : auditList) {
            audit.setBanner(null);
            auditRepository.save(audit);
        }
    }
}
