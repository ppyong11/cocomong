package com.sw.cocomong.Object;

public class FoodObj {
    private String username;
    private String refnum;
    private String foodname;
    private String expiredate;
    private String category;
    private String memo;
    private String favorite;

    public FoodObj(String username, String refnum,String foodname, String expiredate, String category, String memo, String favorite){
        this.username=username;
        this.refnum=refnum;
        this.foodname=foodname;
        this.expiredate=expiredate;
        this.category=category;
        this.memo=memo;
        this.favorite=favorite;
    }
}
