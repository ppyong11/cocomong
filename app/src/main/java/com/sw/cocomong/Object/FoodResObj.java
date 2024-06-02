package com.sw.cocomong.Object;

public class FoodResObj {
    private String username;
    private String refname;
    private String foodname;
    private String expiredate;
    private String category;
    private String memo;
    private String favorite;
    private String foodid;

    public FoodResObj(String foodid, String foodname, String username,String expiredate, String category, String memo, String favorite, String refname){
        this.foodid = foodid;
        this.username=username;
        this.refname=refname;
        this.foodname=foodname;
        this.expiredate=expiredate;
        this.category=category;
        this.memo=memo;
        this.favorite=favorite;
    }
    public FoodResObj(String foodname, String username,String expiredate, String category, String memo, String favorite, String refname){
        this.username=username;
        this.refname=refname;
        this.foodname=foodname;
        this.expiredate=expiredate;
        this.category=category;
        this.memo=memo;
        this.favorite=favorite;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
