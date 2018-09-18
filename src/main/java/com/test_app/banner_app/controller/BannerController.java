package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.*;
import com.test_app.banner_app.repositories.AuditRepository;
import com.test_app.banner_app.repositories.BannerRepository;
import com.test_app.banner_app.repositories.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/banners")
public class BannerController {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private LocalRepository localRepository;
    @Autowired
    private AuditRepository auditRepository;

    @GetMapping
    public String getAll(Map<String, Object> model) {
        Iterable<Local> locals = localRepository.findAll();
        Iterable<Banner> banners = bannerRepository.findAll();
        model.put("banners", banners);
        model.put("locals", locals);
        return "banners";
    }

    @PostMapping
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam Integer langId,
                      @RequestParam String targetUrl,
                      @RequestParam Integer height,
                      @RequestParam Integer width,
                      @RequestParam String imgSrc,
                      Map<String, Object> model) {
        Optional<Local> local = localRepository.findById(langId);
        if (local.isPresent()) {
            Banner banner = new Banner(local.get(), targetUrl, height, width, imgSrc);
            Audit audit = new Audit(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                    user,
                    banner,
                    TypeChange.CREATED,
                    "new banner");
            auditRepository.save(audit);
            bannerRepository.save(banner);
        }

        Iterable<Banner> banners = bannerRepository.findAll();
        model.put("banners", banners);
        return "banners";
    }

    @PostMapping("group")
    public String getAllGroupByLandId(@RequestParam Integer langId, Map<String, Object> model) {
        Iterable<Banner> banners = bannerRepository.findByOrderByLangIdAsc();
        model.put("banners", banners);
        return "banners";
    }
}
