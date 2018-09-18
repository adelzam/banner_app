package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.entity.enums.TypeChange;
import com.test_app.banner_app.repositories.BannerRepository;
import com.test_app.banner_app.service.BannerService;
import com.test_app.banner_app.service.LocalService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequestMapping(value = "/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
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
        bannerService.createOrUpdateBanner(user, null, langId, targetUrl, height, width, imgSrc);
        bannerService.getPreload(model);
        return "banners";
    }

    @PostMapping("filter")
    public String filter(@RequestParam BannerSortEnum sortType, Map<String, Object> model) {
        bannerService.getPreloadWithFilter(model, sortType);
        return "banners";
    }

    @GetMapping("edit/{id}")
    public String editBanner(@PathVariable("id") Integer id, Map<String, Object> model) {
        bannerService.getBannerForEdit(model, id);
        return "bannerEdit";
    }

    @GetMapping("delete/{id}")
    public String deleteBanner(@PathVariable("id") Integer id, @AuthenticationPrincipal User user, Map<String, Object> model) {
        bannerService.deletedBanner(user, id);
        bannerService.getPreload(model);
        return "redirect:/banners";
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
        bannerService.createOrUpdateBanner(user, id, langId, targetUrl, height, width, imgSrc);
        bannerService.getPreload(model);
        return "redirect:/banners";
    }
}
