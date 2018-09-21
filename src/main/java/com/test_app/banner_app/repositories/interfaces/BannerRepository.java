package com.test_app.banner_app.repositories.interfaces;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository {

    List<Banner> findBannersByLang(Local local);

   List<Banner> findByOrderByLangIdAsc();

    List<Banner> findByOrderByLangIdDesc();

    List<Banner> findByOrderById();

    Banner findById(Integer id);

    void deleteById(Integer id);

    Integer save(Banner banner);

    void update(Banner banner);

    Iterable<Banner> findAll();
}
