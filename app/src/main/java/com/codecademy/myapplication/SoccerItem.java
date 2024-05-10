package com.codecademy.myapplication;

public class SoccerItem {
    private String name;
    private String details;
    private String location;
    private int totalTeams;
    private int currentTeams;
    private String date;
    private final int imageResource;


    public String getSoccerName() {
        return name;
    }

    public void setSoccerName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(int totalTeams) {
        this.totalTeams = totalTeams;
    }

    public int getCurrentTeams() {
        return currentTeams;
    }

    public void setCurrentTeams(int currentTeams) {
        this.currentTeams = currentTeams;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageResource() {
        return imageResource;
    }

    public SoccerItem(String name, String details, String location, int totalTeams, int currentTeams, String date, int imageResource) {
        this.name = name;
        this.details = details;
        this.location = location;
        this.totalTeams = totalTeams;
        this.currentTeams = currentTeams;
        this.date = date;
        this.imageResource = imageResource;
    }
}
