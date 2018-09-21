package com.test_app.banner_app.controllers;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.service.BannerService;
import com.test_app.banner_app.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private LocalService localService;


    @GetMapping
    public String getAll(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Map<String, Object> buffMap = bannerService.getPreload();
        buffMap.forEach(model::put);
        model.put("user", user.getUsername());
        return "banners";
    }

    @PostMapping
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam Integer langId,
                      @Valid Banner banner,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("enteredBanner", banner);
        } else {
            banner.setId(null);
            bannerService.createOrUpdateBanner(user, banner, langId);
        }
        model.addAllAttributes(bannerService.getPreload());
        model.addAttribute("user", user.getUsername());
        return "banners";
    }

    @PostMapping("filter")
    public String filter(@AuthenticationPrincipal User user, @RequestParam BannerSortEnum sortType, Map<String, Object> model) {
        model.putAll(bannerService.getPreloadWithFilter(sortType));
        model.put("user", user.getUsername());
        return "banners";
    }

    @PostMapping("group-local")
    public String groupBannersByLocal(@AuthenticationPrincipal User user, @RequestParam Integer langId, Map<String, Object> model) {
        model.putAll(bannerService.getPreloadWithGroup(langId));
        model.put("isGroup", true);
        model.put("user", user.getUsername());
        return "banners";
    }

    @GetMapping("edit/{id}")
    public String editBanner(@PathVariable("id") Integer id, @AuthenticationPrincipal User user, Map<String, Object> model) {
        model.putAll(bannerService.getBannerForEdit(id));
        model.put("user", user.getUsername());
        return "bannerEdit";
    }

    @GetMapping("delete/{id}")
    public String deleteBanner(@PathVariable("id") Integer id, @AuthenticationPrincipal User user, Map<String, Object> model) {
        bannerService.deletedBanner(user, id);
        model.putAll(bannerService.getPreload());
        model.put("user", user.getUsername());
        return "redirect:/banners";
    }

    @PostMapping("update")
    public String updateBanner(@AuthenticationPrincipal User user,
                               @RequestParam Integer langId,
                               @Valid Banner banner,
                               BindingResult bindingResult,
                               Model model) {
        String page;
        if (bindingResult.hasErrors()) {
                banner.setLang(localService.getLocalById(langId));
                model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
                model.addAttribute("enteredBanner", banner);
                page = "bannerEdit";
        } else {
            bannerService.createOrUpdateBanner(user, banner, langId);
            page = "redirect:/banners";
        }
        model.mergeAttributes(bannerService.getPreload());
        model.addAttribute("user", user.getUsername());
        return page;
    }
}
