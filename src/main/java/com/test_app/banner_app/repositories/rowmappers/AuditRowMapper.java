package com.test_app.banner_app.repositories.rowmappers;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.entity.Banner;
import com.test_app.banner_app.entity.Local;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.entity.enums.TypeChange;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditRowMapper implements RowMapper {
    @Override
    public Audit mapRow(ResultSet resultSet, int i) throws SQLException {
        Local local = new Local();
        Banner banner = new Banner();
        Audit audit = new Audit();
        User user = new User();
        if (resultSet.getInt("b_id")!=0) {
            banner.setId(resultSet.getInt("b_id"));
            banner.setHeight(resultSet.getInt("height"));
            banner.setWidth(resultSet.getInt("width"));
            banner.setImgSrc(resultSet.getString("img_src"));
            banner.setTargetUrl(resultSet.getString("target_url"));
            local.setId(resultSet.getInt("l_id"));
            local.setCountry(resultSet.getString("country"));
            local.setLanguage(resultSet.getString("language"));
            banner.setLang(local);
        } else {
            banner=null;
        }
        user.setId(resultSet.getInt("u_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setActive(resultSet.getBoolean("active"));
        audit.setUser(user);
        audit.setBanner(banner);
        audit.setComment(resultSet.getString("comment"));
        audit.setDate(resultSet.getString("date"));
        audit.setId(resultSet.getInt("id"));
        audit.setType(TypeChange.values()[resultSet.getInt("type")]);
        return audit;
    }
}
