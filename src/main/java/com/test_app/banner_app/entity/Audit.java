package com.test_app.banner_app.entity;


import com.test_app.banner_app.entity.enums.TypeChange;

import javax.persistence.*;

@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banner_id")
    private Banner banner;

    private TypeChange type;

    private String comment;

    public Audit(String date, User user, Banner banner, TypeChange type, String comment) {
        this.date = date;
        this.user = user;
        this.banner = banner;
        this.type = type;
        this.comment = comment;
    }

    public Audit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public TypeChange getType() {
        return type;
    }

    public void setType(TypeChange type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
