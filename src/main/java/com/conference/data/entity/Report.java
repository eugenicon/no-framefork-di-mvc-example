package com.conference.data.entity;

public class Report {
    private Integer id;
    private String theme;
    private String place;
    private String reporter;

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
}
