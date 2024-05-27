package com.sw.cocomong.dto;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class FoodListItemDto {
    private String userName;
    private Bitmap foodImage;
    private String foodname;
    private String category;
    private String expire;
    private String memo;
    private boolean favorite;
    private int refNum;

    public FoodListItemDto( Bitmap foodImage, String foodname, String category, String expire, String memo, boolean favorite, int refNum){
        this.foodname =foodname;
        this.foodImage=foodImage;
        this.expire=expire;
        this.category=category;
        this.memo=memo;
        this.favorite=favorite;
        this.refNum = refNum;
    }

    public Bitmap getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Bitmap foodImage) {
        this.foodImage = foodImage;
    }

    public int getRefNum() {
        return refNum;
    }

    public void setRefNum(int refNum) {
        this.refNum = refNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
