package com.conference.data.dto;

import com.conference.data.entity.User;

import java.util.Date;

public class ConferenceDto {
    private Integer id;
    private String name;
    private Date date;
    private User moderator;
    private Integer totalTickets;
    private Integer purchasedTickets;
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

    public Integer getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(Integer purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public Integer getRemainingTickets() {
        return totalTickets - purchasedTickets;
    }

}
