package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BannerRepository extends CrudRepository<Banner, Integer> {

    List<Banner> findBannersByLang(Local local);

   List<Banner> findByOrderByLangIdAsc();

    List<Banner> findByOrderByLangIdDesc();

    List<Banner> findByOrderById();

}
