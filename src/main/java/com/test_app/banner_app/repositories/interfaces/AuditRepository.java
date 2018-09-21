package com.test_app.banner_app.repositories.interfaces;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository {

    List<Audit> findAuditsByUserId(Integer id);

    List<Audit> findAuditsByBannerId(Integer id);

    Iterable<Audit> getAuditsByOrderByDateDesc();

    void save(Audit audit);

    void changeBannerIdToNullIfDeleted(Integer id);
}
