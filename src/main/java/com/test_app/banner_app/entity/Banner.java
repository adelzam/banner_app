package com.test_app.banner_app.entity;

import javax.persistence.*;

@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "lang_id")
    private Integer langId;

    @Column(name = "target_url")
    private String targetUrl;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "img_src")
    private String imgSrc;

    public Banner() {
    }

    public Banner(Integer lang_id, String target_url, Integer height, Integer width, String img_src) {
        this.langId = lang_id;
        this.targetUrl = target_url;
        this.height = height;
        this.width = width;
        this.imgSrc = img_src;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
