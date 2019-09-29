package com.tsdreamdeveloper.places.mvp.model;

import java.io.Serializable;

/**
 * @author Timur Seisembayev
 * @since 28.09.2019
 */
public class Data implements Serializable {

    private String id;
    private String name;
    private String country;
    private Long lat;
    private Long lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }
}
