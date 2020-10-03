package com.example.eventifier;

public class Events {
    String Name, Place, Description, Date, Time, Charges, Committee, Category, Image, Verified;

    public Events(String name, String place, String description, String date, String time, String charges, String committee, String category, String image, String verified) {
        Name = name;
        Place = place;
        Description = description;
        Date = date;
        Time = time;
        Charges = charges;
        Committee = committee;
        Category = category;
        Image = image;
        Verified = verified;
    }

    public Events() {
    }

    public String getVerified() {
        return Verified;
    }

    public void setVerified(String verified) {
        Verified = verified;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCharges() {
        return Charges;
    }

    public void setCharges(String charges) {
        Charges = charges;
    }

    public String getCommittee() {
        return Committee;
    }

    public void setCommittee(String committee) {
        Committee = committee;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

