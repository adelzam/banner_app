package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.entity.enums.TypeChange;
import com.test_app.banner_app.repositories.AuditRepository;
import com.test_app.banner_app.repositories.BannerRepository;
import com.test_app.banner_app.repositories.LocalRepository;
import com.test_app.banner_app.service.BannerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/banners")
public class BannerController {

    private final BannerService bannerService;
    private final BannerRepository bannerRepository;
    private final LocalRepository localRepository;
    private final AuditRepository auditRepository;

    public BannerController(BannerService bannerService, BannerRepository bannerRepository, LocalRepository localRepository, AuditRepository auditRepository) {
        this.bannerService = bannerService;
        this.bannerRepository = bannerRepository;
        this.localRepository = localRepository;
        this.auditRepository = auditRepository;
    }

    @GetMapping
    public String getAll(Map<String, Object> model) {
        bannerService.getPreload(model);
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
            bannerRepository.save(banner);
            auditRepository.save(audit);
        }
        bannerService.getPreload(model);
        return "banners";
    }

    @PostMapping("filter")
    public String filter(@RequestParam BannerSortEnum sortType, Map<String, Object> model) {
        bannerService.getPreloadWithFilter(model, sortType);
        return "banners";
    }

    @GetMapping("edit/{id}")
    public String editBanner(@PathVariable("id") Integer id, Map<String, Object> model ) {
        bannerService.getBannerForEdit(model, id);
        return "bannerEdit";
    }

    @PostMapping("update")
    public String updateBanner(@AuthenticationPrincipal User user,
                               @RequestParam Integer id,
                               @RequestParam Integer langId,
                               @RequestParam String targetUrl,
                               @RequestParam Integer height,
                               @RequestParam Integer width,
                               @RequestParam String imgSrc,
                               Map<String, Object> model) {
        Banner banner = new Banner(null, targetUrl, height, width, imgSrc);
        banner.setId(id);
        localRepository.findById(langId).ifPresent(banner::setLang);
        String changeComment = bannerService.getBannerChanges(banner, id);
        Optional<Local> local = localRepository.findById(langId);
        if (local.isPresent()) {
            Audit audit = new Audit(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                    user,
                    banner,
                    TypeChange.UPDATED,
                    changeComment);
            bannerRepository.save(banner);
            auditRepository.save(audit);
        }
        bannerService.getPreload(model);
        return "banners";
    }
}
