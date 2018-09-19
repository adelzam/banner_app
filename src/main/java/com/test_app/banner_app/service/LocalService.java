package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.repositories.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LocalService {

    private final LocalRepository localRepository;

    @Autowired
    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    void addLocalsToModel(Map<String, Object> model) {
        Iterable<Local> locals = localRepository.findAll();
        model.put("locals", locals);
    }

    List<Local> getLocalsWithoutOne(Banner banner) {
        return localRepository.findAllByIdIsNot(banner.getLang().getId());
    }

    Local getLocalById(Integer id) {
        Optional<Local> local = localRepository.findById(id);
        return local.orElse(null);
    }
}
