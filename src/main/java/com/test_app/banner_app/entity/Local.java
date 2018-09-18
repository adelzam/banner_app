package com.test_app.banner_app.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "local_info")
public class Local {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    private String country;

    private String language;

    public Local() {
    }

    public Local(String country, String language) {
        this.country = country;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return Objects.equals(id, local.id) &&
                Objects.equals(country, local.country) &&
                Objects.equals(language, local.language);
    }
}
