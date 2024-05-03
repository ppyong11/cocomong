package com.sw.cocomong.dto;

public class BarcodeResDto {
    private String productName;
    private String dayCount;
    private String category;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BarcodeResDto{" +
                "productName='" + productName + '\'' +
                ", dayCount='" + dayCount + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
