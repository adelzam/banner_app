package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.entity.enums.TypeChange;
import com.test_app.banner_app.repositories.BannerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final LocalService localService;
    private final AuditService auditService;

    public BannerService(BannerRepository bannerRepository, LocalService localService, AuditService auditService) {
        this.bannerRepository = bannerRepository;
        this.localService = localService;
        this.auditService = auditService;
    }

    public void getPreload(Map<String, Object> model) {
        addBannersToModel(model);
        addFilterToModel(model);
        localService.addLocalsToModel(model);
    }

    public void createOrUpdateBanner(User user, Banner banner, Integer langId) {
        Local local = localService.getLocalById(langId);
        String comment = "";
        TypeChange type = null;
        if (banner.getId()== null) {
            type = TypeChange.CREATED;
            comment = "new comment";
        } else {
            type = TypeChange.UPDATED;
            comment = getBannerChanges(banner, Math.toIntExact(banner.getId()));
            banner.setId(banner.getId());
        }
        banner.setLang(local);
        Audit audit = new Audit(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                user,
                banner,
                type,
                comment);
        saveBannerToDB(banner, audit);
    }

    public void deletedBanner(User user, Integer id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        final String[] comment = {""};
        banner.ifPresent(banner1 -> {
            comment[0] = banner1.toString();
            auditService.updateAuditDeleted(banner1.getId());
        });
        Audit audit = new Audit(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                user,
                null,
                TypeChange.DELETED,
                comment[0]);
        bannerRepository.deleteById(id);
        auditService.saveAuditLine(audit);
    }

    public void getPreloadWithFilter(Map<String, Object> model, BannerSortEnum filter) {
        addFilerBannersToModel(model, filter);
        addFilterToModel(model);
        localService.addLocalsToModel(model);
    }

    public void getBannerForEdit(Map<String, Object> model, Integer id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        banner.ifPresent(banner1 -> {
            model.put("banner", banner1);
            List<Local> localList = localService.getLocalsWithoutOne(banner1);
            model.put("locals", localList);
        });
    }

    private String getBannerChanges(Banner banner, Integer id) {
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
            if (v != null) {
                changeComment[0] = changeComment[0] + " " + k + " old value: " + v + ";";
            }
        });
        return changeComment[0];
    }

    private void saveBannerToDB(Banner banner, Audit audit) {
        bannerRepository.save(banner);
        auditService.saveAuditLine(audit);
    }

    private void addFilterToModel(Map<String, Object> model) {
        HashMap<BannerSortEnum, String> filters = new HashMap<>();
        for (BannerSortEnum key : BannerSortEnum.values()) {
            String value = key.toString().toLowerCase().replace("_", " ");
            filters.put(key, value);
        }
        model.put("filters", filters.entrySet());
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
