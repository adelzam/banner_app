package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Audit;
import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<Audit, Integer> {
}
