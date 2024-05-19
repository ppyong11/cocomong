package com.sw.cocomong.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefFavoriteMap {
    private static List<FoodListItemDto> favoriteItems=new ArrayList<>();
    private static Map<RefListItemDto, List<FoodListItemDto>> refFoodMap;


    public RefFavoriteMap(){
        // refId=refListItemDto.getRefId();
        refFoodMap = new HashMap<>();
    }

    public Map<RefListItemDto, List<FoodListItemDto>> getRefFoodMap(){
        return refFoodMap;
    }

    public static List<FoodListItemDto> getFavoriteItems() {
        return favoriteItems;
    }
}
