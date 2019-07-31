package com.conference.data.entity;

import com.conference.validation.Matches;
import com.conference.validation.NotEmpty;

import java.util.Date;

public class Report {
    private Integer id;

    @NotEmpty("You must specify the theme")
    private String theme;

    @NotEmpty("Choose a place for the report")
    private Location place;

    @NotEmpty("Select the main reporter")
    private User reporter;

    @NotEmpty("Set start time")
    private Date startTime;

    @NotEmpty("Set end time")
    private Date endTime;

    @Matches(regex = ".{10,}", message = "Description must be at least 10 symbols")
    private String description;

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

    public Location getPlace() {
        return place;
    }

    public void setPlace(Location place) {
        this.place = place;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
