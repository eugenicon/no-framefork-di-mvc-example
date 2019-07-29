package com.conference.data.entity;

import com.conference.validation.NotEmpty;
import com.conference.validation.Number;

public class Location {
    private Integer id;

    @NotEmpty("You must specify the name")
    private String name;

    @Number(min = 1, message = "Location should have at least 1 place")
    private Integer places;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }
}
