package com.test_app.banner_app.repositories;

import com.test_app.banner_app.entity.Audit;
import com.test_app.banner_app.repositories.interfaces.AuditRepository;
import com.test_app.banner_app.repositories.rowmappers.AuditRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

@Repository
public class AuditRepositoryImpl implements AuditRepository {

    private final String BASE_SQL_AUDIT_AND_USER = "select a.id, a.comment, a.date, a.type, u.id as u_id, u.active, u.first_name, u.last_name, u.password, u.username, q1.b_id as b_id, q1.lang_id,q1.img_src, q1.target_url, q1.width, q1.height, q1.l_id as l_id, q1.language, q1.country" +
            " from audit a inner join usr u on a.user_id = u.id";
    private final String JOIN_CLAUSE = "q1 on a.banner_id=q1.b_id";

    private final String BASE_SQL_BANNER_AND_LAND = "select b.id as b_id, b.lang_id as lang_id,b.img_src as img_src, b.target_url as target_url, b.width as width , b.height as height, l.id as l_id, l.language as language , l.country as country" +
            " from banner b inner join local_info l on b.lang_id = l.id";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Audit> findAuditsByUserId(Integer id) {
        String localSQL = BASE_SQL_AUDIT_AND_USER + " and u.id=? left join ("+BASE_SQL_BANNER_AND_LAND+")"+ JOIN_CLAUSE;
        return jdbcTemplate.query(localSQL, new Object[]{id}, new AuditRowMapper());
    }

    @Override
    public List<Audit> findAuditsByBannerId(Integer id) {
        String localSQL = BASE_SQL_AUDIT_AND_USER + " inner join ("+BASE_SQL_BANNER_AND_LAND+" and b.id=?)"+ JOIN_CLAUSE;
        return jdbcTemplate.query(localSQL, new Object[]{id}, new AuditRowMapper());
    }

    @Override
    public Iterable<Audit> getAuditsByOrderByDateDesc() {
        String localSQL = BASE_SQL_AUDIT_AND_USER + " left join ("+BASE_SQL_BANNER_AND_LAND+")"+JOIN_CLAUSE+ " ORDER BY date desc ";
        return jdbcTemplate.query(localSQL, new AuditRowMapper());
    }

    @Override
    public void save(Audit audit) {
        String sql = "";
            if (audit.getId()==null) {
                sql = "insert into audit(id, comment, date, type, banner_id, user_id) VALUES (nextval('hibernate_sequence'),?,?,?,?,?)";
            } else {
               sql = "update audit set (comment, date, type, banner_id, user_id)=(?,?,?,?,?) where id=?";
            }
        String finalSql = sql;
        jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(finalSql);
                        ps.setString(1, audit.getComment());
                        ps.setString(2, audit.getDate());
                        ps.setInt(3, audit.getType().ordinal());
                        if (audit.getBanner() == null) {
                            ps.setNull(4, Types.INTEGER);
                        } else {
                            ps.setInt(4, audit.getBanner().getId());
                        }
                        ps.setInt(5, audit.getUser().getId());
                        if (audit.getId()!=null) {
                            ps.setInt(6, audit.getId());
                        }
                        return ps;
                    });
    }

    @Override
    public void changeBannerIdToNullIfDeleted(Integer id) {
        jdbcTemplate.update("update audit set banner_id = null where banner_id = ?", new Object[]{id});
    }
}
