package com.conference.data.entity;

import com.conference.validation.Matches;
import com.conference.validation.NotEmpty;

public class Report {
    private Integer id;

    @NotEmpty("You must specify the theme")
    private String theme;

    @NotEmpty("Choose a place for the report")
    private String place;

    @NotEmpty("Select the main reporter")
    @Matches(regex = Matches.LETTERS_ONLY, message = "Reporter should have a valid name")
    private String reporter;

    @Matches(regex = ".{10,}", message = "Description must be at least 10 symbols")
    private String description;

    public Report() {
    }

    public Report(String theme, String place, String reporter) {
        this.theme = theme;
        this.place = place;
        this.reporter = reporter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
