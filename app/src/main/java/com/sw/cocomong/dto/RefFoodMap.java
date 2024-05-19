package com.sw.cocomong.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefFoodMap {
    private List<FoodListItemDto> foodListItemDtos;
    private static Map<RefListItemDto, List<FoodListItemDto>> refFoodMap= new HashMap<>();

    public RefFoodMap(int refPosition, List<FoodListItemDto> foodListItemDtos){
        // refId=refListItemDto.getRefId();
        this.foodListItemDtos=foodListItemDtos;
        refFoodMap.put(RefListItemDto.getRefListItemDtos().get(refPosition),foodListItemDtos);
    }

    public static Map<RefListItemDto, List<FoodListItemDto>> getRefFoodMap(){
        return refFoodMap;
    }

    public List<FoodListItemDto> getFoodListItemDtos() {
        return foodListItemDtos;
    }

    public static void setRefFoodMap(Map<RefListItemDto, List<FoodListItemDto>> refFoodMap) {
        RefFoodMap.refFoodMap = refFoodMap;
    }
}
