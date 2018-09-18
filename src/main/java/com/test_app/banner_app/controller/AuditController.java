package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = "/audit")
public class AuditController {

    @Autowired
    private AuditRepository auditRepository;

    @GetMapping
    public String getAll(Map<String, Object> model) {
        Iterable<Audit> audits = auditRepository.findAll();
        model.put("audits", audits);
        return "audits";
    }
}
