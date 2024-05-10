package com.codecademy.myapplication;

public class SoccerItem {
    private String id;
    private String name;
    private String details;
    private String location;
    private int totalTeams;
    private int currentTeams;
    private String date;
    private  int imageResource;
    public SoccerItem(){}


    public String getSoccerName() {
        return name;
    }



    public String getDetails() {
        return details;
    }


    public String getLocation() {
        return location;
    }


    public int getTotalTeams() {
        return totalTeams;
    }


    public int getCurrentTeams() {
        return currentTeams;
    }


    public String getDate() {
        return date;
    }


    public int getImageResource() {
        return imageResource;
    }
    public String _getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
