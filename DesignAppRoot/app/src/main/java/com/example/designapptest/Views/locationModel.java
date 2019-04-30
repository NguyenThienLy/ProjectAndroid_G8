package com.example.designapptest.Views;

public class locationModel {
    private int image;
    private String name;
    private String room;

    public locationModel(int image, String name, String room) {
        this.image = image;
        this.name = name;
        this.room = room;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
