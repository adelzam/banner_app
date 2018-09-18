package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuditRepository extends CrudRepository<Audit, Integer> {
    List<Audit> findAuditsByBanner(Banner banner);
}
