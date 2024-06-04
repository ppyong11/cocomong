package com.sw.cocomong.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefFoodMap {

    private static List<RefListItemDto> refListItemDtos = new ArrayList<>();

    private static Map<RefListItemDto, List<FoodListItemDto>> refFoodMap= new HashMap<>();

    private static Map<RefListItemDto, List<FoodListItemDto>> refFavMap= new HashMap<>();

    private static Map<RefListItemDto, List<FoodListItemDto>> refRecipeMap= new HashMap<>();

    public static List<RefListItemDto> getRefListItemDtos() {
        return refListItemDtos;
    }

    public static List<FoodListItemDto> getFoodListItemDtos(RefListItemDto refListItemDto) {
        return refFoodMap.get(refListItemDto);
    }

    public static Map<RefListItemDto, List<FoodListItemDto>> getRefFoodMap() {
        return refFoodMap;
    }

    public static List<FoodListItemDto> getFavoriteItems(RefListItemDto refListItemDto) {
        return refFavMap.get(refListItemDto);
    }

    public static Map<RefListItemDto, List<FoodListItemDto>> getRefFavMap() {
        return refFavMap;
    }

    public static void addRefListItemDto(RefListItemDto refListItemDto) {
        RefFoodMap.refListItemDtos.add(refListItemDto);
        refFoodMap.put(refListItemDto,new ArrayList<>());
        refFavMap.put(refListItemDto, new ArrayList<>());

    }
    public static Map<RefListItemDto, List<FoodListItemDto>> getRefRecipeMap() {return refRecipeMap;}

    public static List<FoodListItemDto> getRecipeItems(RefListItemDto refListItemDto){
        return refRecipeMap.get(refListItemDto);
    }

    // TODO: 2024-05-21 get(refListItemDto)가 null값인 경우 로직 추가
    public static void addFoodListItemDto(RefListItemDto refListItemDto, FoodListItemDto foodListItemDto) {
        List<FoodListItemDto> foodListItemDtos=RefFoodMap.refFoodMap.get(refListItemDto);
        foodListItemDtos.add(foodListItemDto);
        RefFoodMap.refFoodMap.put(refListItemDto,foodListItemDtos);
    }

    public static void addFavoriteItem(RefListItemDto refListItemDto, FoodListItemDto favoriteItem) {
        List<FoodListItemDto> favoriteList=RefFoodMap.refFoodMap.get(refListItemDto);
        favoriteList.add(favoriteItem);
        RefFoodMap.getFavoriteItems(refListItemDto).add(favoriteItem);
    }
}
