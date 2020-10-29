package com.example.eventifier;

public class Book {
    String Id, Name, Place, Time, Date;

    public Book(String id, String name, String place, String time, String date) {
        Id = id;
        Name = name;
        Place = place;
        Time = time;
        Date = date;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Book() {
    }

    public String getId() {
        return Id;
    }



    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
