package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.repositories.interfaces.BannerRepository;
import com.test_app.banner_app.repositories.rowmappers.BannerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BannerRepositoryImpl implements BannerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_LINE = "select b.id, l.id as local_id, l.country, l.language, b.height, b.width, b.target_url, b.img_src from banner b, local_info l where b.lang_id=l.id";

    @Override
    public List<Banner> findBannersByLang(Local local) {
        return jdbcTemplate.query(SQL_SELECT_LINE + " and b.lang_id=?", new Object[]{local.getId()}, new BannerRowMapper());
    }

    @Override
    public List<Banner> findByOrderByLangIdAsc() {
        return jdbcTemplate.query(SQL_SELECT_LINE + " order by b.lang_id asc", new BannerRowMapper());
    }

    @Override
    public List<Banner> findByOrderByLangIdDesc() {
        return jdbcTemplate.query(SQL_SELECT_LINE + " order by b.lang_id desc", new BannerRowMapper());
    }

    @Override
    public List<Banner> findByOrderById() {
        return jdbcTemplate.query(SQL_SELECT_LINE + " order by b.id", new BannerRowMapper());
    }

    @Override
    public Banner findById(Integer id) {
        List<Banner> banners = jdbcTemplate.query(SQL_SELECT_LINE + "  and b.id=?", new Object[]{id}, new BannerRowMapper());
        if (banners.size() == 0) return null;
        else
            return banners.get(0);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("delete from banner where id=?", new Object[]{id});
    }

    @Override
    public Integer save(Banner banner) {
        String sql = "insert into banner(id, height, img_src, target_url, width, lang_id) VALUES (nextval('hibernate_sequence'),?,?,?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1, banner.getHeight());
                    ps.setString(2, banner.getImgSrc());
                    ps.setString(3, banner.getTargetUrl());
                    ps.setInt(4, banner.getWidth());
                    ps.setInt(5, banner.getLang().getId());
                    return ps;
                });
        return jdbcTemplate.queryForObject("select max(id) from banner", Integer.class);
    }

    @Override
    public void update(Banner banner) {
        jdbcTemplate.update("update banner set height=?, img_src=?, target_url=?, width=?, lang_id=? where id=?",
                new Object[]{banner.getHeight(), banner.getImgSrc(), banner.getTargetUrl(), banner.getWidth(), banner.getLang().getId(), banner.getId()});
    }

    @Override
    public Iterable<Banner> findAll() {
        return jdbcTemplate.query(SQL_SELECT_LINE, new BannerRowMapper());
    }
}
