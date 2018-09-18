package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
