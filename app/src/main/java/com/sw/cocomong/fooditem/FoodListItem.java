package com.sw.cocomong.fooditem;

public class FoodListItem{
    private String name;
    private String category;
    private String expire;
    private String memo;
    private boolean favorite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public FoodListItem(String name, String category, String expire){
        this.category=category;
        this.name=name;
        this.expire = expire;
        //favorite=false;
    }
    public FoodListItem(String name, String category, String expire, String memo){
        this.name=name;
        this.expire=expire;
        this.category=category;
        this.memo=memo;
        //favorite=false;
    }

    @Override
    public String toString() {
        return "FoodListItem{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", expire='" + expire + '\'' +
                ", memo='" + memo + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
