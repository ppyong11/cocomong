package com.sw.cocomong.dto;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class FoodListItemDto {
    private Bitmap foodImage;
    private String foodname;
    private String category;
    private String expire;
    private String memo;
    private boolean favorite;

    public FoodListItemDto(Bitmap foodImage, String foodname, String category, String expire, String memo){
        this.foodImage=foodImage;
        this.foodname =foodname;
        this.expire=expire;
        this.category=category;
        this.memo=memo;
        //favorite=false;
    }

    public Bitmap getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Bitmap foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "FoodListItem{" +
                "name='" + foodname + '\'' +
                ", category='" + category + '\'' +
                ", expire='" + expire + '\'' +
                ", memo='" + memo + '\'' +
                ", favorite=" + favorite +
                '}';
    }

}
