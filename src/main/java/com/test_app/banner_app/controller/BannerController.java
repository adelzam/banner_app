package com.test_app.banner_app.controller;

import com.sun.deploy.net.HttpResponse;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/banners")
public class BannerController {
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping
    public String getAll(Map<String, Object> model) {
        Iterable<Banner> banners = bannerRepository.findAll();
        model.put("banners", banners);
        return "banners";
    }

    @PostMapping
    public String add(@RequestParam Integer langId,
                      @RequestParam String targetUrl,
                      @RequestParam Integer height,
                      @RequestParam Integer width,
                      @RequestParam String imgSrc,
                      Map<String, Object> model) {
        Banner banner = new Banner(langId, targetUrl, height, width, imgSrc);
        bannerRepository.save(banner);
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
