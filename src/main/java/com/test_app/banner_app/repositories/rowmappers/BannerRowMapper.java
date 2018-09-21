package com.test_app.banner_app.repositories.rowmappers;

import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BannerRowMapper implements RowMapper {
    @Override
    public Banner mapRow(ResultSet resultSet, int i) throws SQLException {
        Banner banner = new Banner();
        banner.setId(resultSet.getInt("id"));
        banner.setHeight(resultSet.getInt("height"));
        banner.setWidth(resultSet.getInt("width"));
        banner.setImgSrc(resultSet.getString("img_src"));
        banner.setTargetUrl(resultSet.getString("target_url"));
        Local local = new Local();
        local.setId(resultSet.getInt("local_id"));
        local.setCountry(resultSet.getString("country"));
        local.setLanguage(resultSet.getString("language"));
        banner.setLang(local);
        return banner;
    }
}
