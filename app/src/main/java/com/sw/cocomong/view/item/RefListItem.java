package com.sw.cocomong.view.item;

import java.util.ArrayList;
import java.util.List;

public class RefListItem {
    private String name;
    public static List<RefListItem> refListItems = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public RefListItem(String name){
        this.name=name;
    }
}
