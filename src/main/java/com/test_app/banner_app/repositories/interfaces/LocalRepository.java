package com.test_app.banner_app.repositories.interfaces;

import com.test_app.banner_app.entity.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository {

    List<Local> findAllByIdIsNot(Integer id);

    Iterable<Local> findAll();

    Local findById(Integer id);
}
