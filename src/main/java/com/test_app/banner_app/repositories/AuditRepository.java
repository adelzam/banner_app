package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuditRepository extends CrudRepository<Audit, Integer> {

    List<Audit> findAuditsByUserId(Integer id);

    List<Audit> findAuditsByBannerId(Integer id);

    Iterable<Audit> getAuditsByOrderByDateDesc();
}
