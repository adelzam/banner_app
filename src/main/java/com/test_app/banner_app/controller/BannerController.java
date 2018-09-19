package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.service.BannerService;
import com.test_app.banner_app.service.ErrorAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/banners")
public class BannerController {

    private final BannerService bannerService;
    private final ErrorAddService errorAddService;

    @Autowired
    public BannerController(BannerService bannerService, ErrorAddService errorAddService) {
        this.bannerService = bannerService;
        this.errorAddService = errorAddService;
    }


    @GetMapping
    public String getAll(Map<String, Object> model) {
        bannerService.getPreload(model);
        return "banners";
    }

    @PostMapping
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam Integer langId,
                      @Valid Banner banner,
                      BindingResult bindingResult,
                      Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            errorAddService.writeErrors(bindingResult, model);
        } else {
            banner.setId(null);
            bannerService.createOrUpdateBanner(user, banner, langId);
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
                               @RequestParam Integer langId,
                               @Valid Banner banner,
                               BindingResult bindingResult,
                               Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            errorAddService.writeErrors(bindingResult, model);
        } else {
            bannerService.createOrUpdateBanner(user, banner, langId);
        }
        bannerService.getPreload(model);
        return "redirect:/banners";
    }
}
