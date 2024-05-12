package com.sw.cocomong.dto;

import java.util.ArrayList;
import java.util.List;

public class RefListItemDto {
    private String name;
    public static List<RefListItemDto> refListItemDtos = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public RefListItemDto(String name){
        this.name=name;
    }
}
