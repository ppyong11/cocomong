package com.sw.cocomong.task.foodtask;

import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;

import org.json.JSONException;

import java.io.IOException;

public class FoodDetailTask {
    FoodResObj foodResObj;
    public FoodDetailTask(int foodid) throws JSONException, IOException {

    }
    public FoodResObj getFoodResObj(){
        return foodResObj;
    }
}
