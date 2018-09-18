package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Local;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocalRepository extends CrudRepository<Local, Integer> {
    List<Local> findAllByIdIsNot(Integer id);
}
