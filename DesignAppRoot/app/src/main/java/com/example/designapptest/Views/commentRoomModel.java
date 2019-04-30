package com.example.designapptest.Views;

public class commentRoomModel {
    private int image;
    private String name;
    private float rate;
    private int quantityLike;
    private String headComment;
    private String mainComment;

    public commentRoomModel(int image, String name, int quantityLike, float rate, String headComment, String mainComment) {
        this.image = image;
        this.name = name;
        this.quantityLike = quantityLike;
        this.rate = rate;
        this.headComment = headComment;
        this.mainComment = mainComment;
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getQuantityLike() {
        return quantityLike;
    }

    public void setQuantityLike(int quantityLike) {
        this.quantityLike = quantityLike;
    }

    public String getHeadComment() {
        return headComment;
    }

    public void setHeadComment(String headComment) {
        this.headComment = headComment;
    }

    public String getMainComment() {
        return mainComment;
    }

    public void setMainComment(String mainComment) {
        this.mainComment = mainComment;
    }
}
