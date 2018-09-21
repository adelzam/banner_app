package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUsername(String username) {
        return (User) jdbcTemplate.queryForObject("select * from usr where username=?", new Object[]{username}, new BeanPropertyRowMapper(User.class));
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("insert into  usr(active, first_name, last_name, password, username) " +
                "values (?,?,?,?,?)", new Object[]{user.isActive(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getUsername()});
        Integer index = findByUsername(user.getUsername()).getId();
        jdbcTemplate.update("insert into user_role(user_id, roles) values (?, ?)", new Object[]{index, "USER"});
    }
}
