package com.test_app.banner_app.controllers;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/audit")
public class AuditController {

    private final AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public String getAll(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Iterable<Audit> audits = auditService.getAll();
        model.put("audits", audits);
        model.put("auditCheck", ((List<Audit>) audits).isEmpty());
        model.put("user", user.getUsername());
        return "audits";
    }

    @GetMapping("user/{id}")
    public String getAuditByUser(@PathVariable("id") Integer id, @AuthenticationPrincipal User user, Map<String, Object> model) {
        Iterable<Audit> audits = auditService.getAuditByUserId(id);
        model.put("audits", audits);
        model.put("auditCheck", ((List<Audit>) audits).isEmpty());
        model.put("user", user.getUsername());
        return "audits";
    }

    @GetMapping("banner/{id}")
    public String getAuditByBanner(@PathVariable("id") Integer id,@AuthenticationPrincipal User user, Map<String, Object> model) {
        Iterable<Audit> audits = auditService.getAuditByBannerId(id);
        model.put("audits", audits);
        model.put("auditCheck", ((List<Audit>) audits).isEmpty());
        model.put("user", user.getUsername());
        return "audits";
    }

    @GetMapping("my")
    public String getMyAudit(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Iterable<Audit> audits = auditService.getAuditByUserName(user.getUsername());
        model.put("audits", audits);
        model.put("auditCheck", ((List<Audit>) audits).isEmpty());
        model.put("user", user.getUsername());
        return "audits";
    }

}
