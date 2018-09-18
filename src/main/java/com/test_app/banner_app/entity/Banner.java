package com.test_app.banner_app.entity;

import javax.persistence.*;

@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lang_id")
    private Local lang;

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

    public Banner(Local lang, String targetUrl, Integer height, Integer width, String imgSrc) {
        this.lang = lang;
        this.targetUrl = targetUrl;
        this.height = height;
        this.width = width;
        this.imgSrc = imgSrc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Local getLang() {
        return lang;
    }

    public void setLang(Local lang) {
        this.lang = lang;
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

    @Override
    public String toString() {
        return "Banner{" +
                "lang=" + lang.toString() +
                ", targetUrl='" + targetUrl + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
