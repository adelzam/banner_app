package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.repositories.BannerRepository;
import com.test_app.banner_app.repositories.LocalRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final LocalRepository localRepository;

    public BannerService(BannerRepository bannerRepository, LocalRepository localRepository) {
        this.bannerRepository = bannerRepository;
        this.localRepository = localRepository;
    }

    public void getPreload(Map<String, Object> model) {
        addBannersToModel(model);
        addFilterToModel(model);
        addLocalsToModel(model);
    }

    public void getPreloadWithFilter(Map<String, Object> model, BannerSortEnum filter) {
        addFilerBannersToModel(model, filter);
        addFilterToModel(model);
        addLocalsToModel(model);
    }

    public void getBannerForEdit(Map<String, Object> model, Integer id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        banner.ifPresent(banner1 -> {
            model.put("banner", banner1);
            List<Local> localList = localRepository.findAllByIdIsNot(banner1.getLang().getId());
            model.put("locals", localList);
        });
    }

    public String getBannerChanges(Banner banner, Integer id) {
        Map<String, String> changeMap = new HashMap<>();
        final String[] changeComment = {""};
        bannerRepository.findById(id).ifPresent(bannerFromDB -> {
            if (!bannerFromDB.getLang().equals(banner.getLang())) {
                changeMap.put("lang", bannerFromDB.getLang().toString());
            } else {
                changeMap.put("lang", null);
            }
            if (!bannerFromDB.getTargetUrl().equals(banner.getTargetUrl())) {
                changeMap.put("target_url", bannerFromDB.getTargetUrl());
            } else {
                changeMap.put("target_url", null);
            }
            if (!bannerFromDB.getHeight().equals(banner.getHeight())) {
                changeMap.put("height", bannerFromDB.getHeight().toString());
            } else {
                changeMap.put("height", null);
            }
            if (!bannerFromDB.getWidth().equals(banner.getWidth())) {
                changeMap.put("width", bannerFromDB.getWidth().toString());
            } else {
                changeMap.put("width", null);
            }
            if (!bannerFromDB.getImgSrc().equals(banner.getImgSrc())) {
                changeMap.put("img_src", bannerFromDB.getImgSrc());
            } else {
                changeMap.put("img_src", null);
            }
        });
        changeMap.forEach((k, v) -> {
            if (v!=null) {
                changeComment[0] = changeComment[0] + " "+k+" old value: "+v+";";
            }
        });
        return changeComment[0];
    }

    private void addFilterToModel(Map<String, Object> model) {
        HashMap<BannerSortEnum, String> filters = new HashMap<>();
        for (BannerSortEnum key : BannerSortEnum.values()) {
            String value = key.toString().toLowerCase().replace("_", " ");
            filters.put(key, value);
        }
        model.put("filters", filters.entrySet());
    }

    private void addLocalsToModel(Map<String, Object> model) {
        Iterable<Local> locals = localRepository.findAll();
        model.put("locals", locals);
    }

    private void addBannersToModel(Map<String, Object> model) {
        Iterable<Banner> banners = bannerRepository.findAll();
        model.put("banners", banners);
    }

    private void addFilerBannersToModel(Map<String, Object> model, BannerSortEnum filter) {
        Iterable<Banner> banners = null;
        switch (filter) {
            case DEFAULT_SORT:
                banners = bannerRepository.findByOrderById();
                break;
            case SORT_BY_LOCAL_ASCENDING:
                banners = bannerRepository.findByOrderByLangIdAsc();
                break;
            case SORT_BY_LOCAL_DESCENDING:
                banners = bannerRepository.findByOrderByLangIdDesc();
                break;
        }
        model.put("banners", banners);
    }

}
