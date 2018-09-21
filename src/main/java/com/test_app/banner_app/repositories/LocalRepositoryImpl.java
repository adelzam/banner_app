package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.repositories.interfaces.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocalRepositoryImpl implements LocalRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LocalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Local> findAllByIdIsNot(Integer id) {
        return   jdbcTemplate.query("select * from local_info where id!=?", new Object[] {id}, new BeanPropertyRowMapper(Local.class));
    }

    @Override
    public Iterable<Local> findAll() {
        return jdbcTemplate.query("select * from local_info", new BeanPropertyRowMapper(Local.class));
    }

    @Override
    public Local findById(Integer id) {
        for (Local local : findAll()) {
            System.out.println(local.getCountry()+" "+local.getLanguage()+" "+local.getId());
        }
        return (Local) jdbcTemplate.queryForObject("select * from local_info where id=?", new Object[] {id}, new BeanPropertyRowMapper(Local.class));
    }
}
