package com.sw.cocomong.Favorite;

public class FoodListItem {
    private String name;
    private String category;
    private String expire;
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
    public FoodListItem(String name, String category, String expire, boolean favorite){
        this.category=category;
        this.name=name;
        this.expire = expire;
        this.favorite=false;
    }
}
