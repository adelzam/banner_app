package com.test_app.banner_app.service;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.repositories.interfaces.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalService {
    private final LocalRepository localRepository;

    @Autowired
    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    Iterable<Local> getLocals() {
        return localRepository.findAll();
    }

    List<Local> getLocalsWithoutOne(Banner banner) {
        return localRepository.findAllByIdIsNot(banner.getLang().getId());
    }

    public Local getLocalById(Integer id) {
        return localRepository.findById(id);
    }
}
