package com.sw.cocomong.task;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class FoodSharedPreference {

    public static final String PREF_NAME = "food_preferences"; // 변경: public으로 설정
    private static final String KEY_FOOD_NAME = "name";
    private static final String KEY_FOOD_EXP = "exp";

    private SharedPreferences sharedPreferences;

    public FoodSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveFoodData(String name, String exp) {
        // 이전에 저장된 데이터 가져오기
        Map<String, String> foodData = getFoodData();

        // 새로운 데이터 추가
        foodData.put(name, exp);

        // 변경된 데이터 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : foodData.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.apply();
    }

    public Map<String, String> getFoodData() {
        Map<String, String> foodData = new HashMap<>();
        // 저장된 데이터 불러오기
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue() instanceof String) {
                foodData.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return foodData;
    }
}
