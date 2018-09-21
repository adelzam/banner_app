package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.BannerSortEnum;
import com.test_app.banner_app.entity.enums.TypeChange;
import com.test_app.banner_app.repositories.interfaces.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BannerService {


    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private LocalService localService;
    @Autowired
    private AuditService auditService;


    public Map<String, Object> getPreload() {
        Map<String, Object> localModel = new HashMap<>();
        localModel.put("banners", getAllBannerFromDB());
        localModel.put("filters", getFiltersList());
        localModel.put("locals", localService.getLocals());
        localModel.put("isGroup", false);
        return localModel;
    }


    public void createOrUpdateBanner(User user, Banner banner, Integer langId) {
        Local local = localService.getLocalById(langId);
        String comment = "";
        TypeChange type = null;
        if (banner.getId() == null) {
            type = TypeChange.CREATED;
            comment = "new banner";
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
        Banner banner = bannerRepository.findById(id);
        final String[] comment = {""};
        comment[0] = banner.toString();
        auditService.updateAuditDeleted(banner.getId());
        Audit audit = new Audit(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
                user,
                null,
                TypeChange.DELETED,
                comment[0]);
        auditService.saveAuditLine(audit);
        auditService.changeBannerIdToNullIfDeleted(id);
        bannerRepository.deleteById(id);
    }

    public Map<String, Object> getPreloadWithFilter(BannerSortEnum filter) {
        Map<String, Object> localModel = new HashMap<>();
        localModel.put("filters", getFiltersList());
        localModel.put("banners", getFilerBanners(filter));
        localModel.put("locals", localService.getLocals());
        return localModel;
    }

    public Map<String, Object> getBannerForEdit(Integer id) {
        Map<String, Object> localModel = new HashMap<>();
        Banner banner = bannerRepository.findById(id);
        localModel.put("banner", banner);
        List<Local> localList = localService.getLocalsWithoutOne(banner);
        localModel.put("locals", localList);
        return localModel;
    }

    public Banner getBanner(Integer id) {
        return bannerRepository.findById(id);
    }

    private String getBannerChanges(Banner banner, Integer id) {
        Map<String, String> changeMap = new HashMap<>();
        final String[] changeComment = {""};
        Banner bannerFromDB = bannerRepository.findById(id);
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
        changeMap.forEach((k, v) -> {
            if (v != null) {
                changeComment[0] = changeComment[0] + " " + k + " old value: " + v + ";";
            }
        });
        return changeComment[0];
    }

    private void saveBannerToDB(Banner banner, Audit audit) {
        if (banner.getId() == null) {
            Integer id = bannerRepository.save(banner);
            banner.setId(id);
            audit.setBanner(banner);
        } else{
            bannerRepository.update(banner);
        }
        auditService.saveAuditLine(audit);
    }

    private Set<Map.Entry<String, String>> getFiltersList() {
        Map<String, String> filters = new HashMap<>();
        for (BannerSortEnum key : BannerSortEnum.values()) {
            String value = key.toString().toLowerCase().replace("_", " ");
            filters.put(key.toString(), value);
        }
        return filters.entrySet();
    }

    private Iterable<Banner> getAllBannerFromDB() {
        return bannerRepository.findAll();
    }

    private Iterable<Banner> getFilerBanners(BannerSortEnum filter) {
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
        return banners;
    }

    public Map<String, Object> getPreloadWithGroup(Integer id) {
        Map<String, Object> localModel = new HashMap<>();
        localModel.put("filters", getFiltersList());
        localModel.put("locals", localService.getLocals());
        Iterable<Banner> banners = bannerRepository.findBannersByLang(localService.getLocalById(id));
        localModel.put("banners", banners);
        return localModel;
    }
}
