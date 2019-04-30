package com.example.designapptest.Views;

public class roomModel {
    private int image;
    private String name;
    private String price;
    private String address;
    private int quantityMember;
    private int quantityComment;
    private String type;

    public roomModel(int image, String name, String price, String address, int quantityMember, int quantityComment, String type) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.address = address;
        this.quantityMember = quantityMember;
        this.quantityComment = quantityComment;
        this.type = type;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getQuantityMember() {
        return quantityMember;
    }

    public void setQuantityMember(int quantityMember) {
        this.quantityMember = quantityMember;
    }

    public int getQuantityComment() {
        return quantityComment;
    }

    public void setQuantityComment(int quantityComment) {
        this.quantityComment = quantityComment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
