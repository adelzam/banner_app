package com.test_app.banner_app.repositories.interfaces;

import com.test_app.banner_app.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User findByUsername(String username);

    void save(User user);
}
