package com.conference.data.entity;

import com.conference.validation.annotation.Matches;
import com.conference.validation.annotation.NotEmpty;
import com.conference.validation.annotation.Number;

import java.util.Date;

public class Conference {
    private Integer id;

    @NotEmpty("You must specify the name")
    private String name;

    @NotEmpty("Select the date of the conference")
    private Date date;

    @NotEmpty("Please choose a moderator")
    private User moderator;

    @Number(min = 1, message = "Conference makes sense with at least 1 ticket")
    private Integer totalTickets;

    @Matches(regex = "(.|\\s){10,}", message = "Description must be at least 10 symbols")
    private String description;

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

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
