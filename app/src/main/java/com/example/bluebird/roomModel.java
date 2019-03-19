package com.example.bluebird;

public class roomModel {
    private int image;
    private  String type;
    private  int quantityMember;
    private  int quantityComment;
    private  String name;
    private  int price;
    private  int area;
    private  String address;

    public roomModel(int image, String type, int quantityMember, int quantityComment, String name, int price, int area, String address)
    {
        this.image = image;
        this.type = type;
        this.quantityMember = quantityMember;
        this.quantityComment = quantityComment;
        this.name = name;
        this.price = price;
        this.area = area;
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

