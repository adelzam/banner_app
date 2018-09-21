package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.repositories.interfaces.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    private final AuditRepository auditRepository;
    private final UserService userService;

    public AuditService(AuditRepository auditRepository, UserService userService) {
        this.auditRepository = auditRepository;
        this.userService = userService;
    }

    void saveAuditLine(Audit audit) {
        auditRepository.save(audit);
    }
    void changeBannerIdToNullIfDeleted(Integer id) {
        auditRepository.changeBannerIdToNullIfDeleted(id);
    }
    void updateAuditDeleted(Integer id) {
        List<Audit> auditList = auditRepository.findAuditsByBannerId(id);
        for (Audit audit : auditList) {
            audit.setBanner(null);
            auditRepository.save(audit);
        }
    }

    public List<Audit> getAuditByUserId(Integer id) {
        return auditRepository.findAuditsByUserId(id);
    }

    public Iterable<Audit> getAll() {
        return auditRepository.getAuditsByOrderByDateDesc();
    }

    public List<Audit> getAuditByBannerId(Integer id) {
        return auditRepository.findAuditsByBannerId(id);
    }

    public List<Audit> getAuditByUserName(String username) {
        Integer id = userService.getUserByUserName(username).getId();
        return auditRepository.findAuditsByUserId(id);
    }

}
