package com.example.designapptest.ClassOther;

public class PriceFilter extends  myFilter {
    float minPrice;
    float maxPrice;

    public float getMinPrice() {
        return minPrice;
    }

    public PriceFilter(){

    }

    public PriceFilter(float minPrice, float maxPrice) {
        this.name = minPrice+" triệu - "+maxPrice+" triệu";
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public void replace(myFilter filter) {

    }
}
