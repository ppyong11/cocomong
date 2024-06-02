package com.sw.cocomong.task.reftask;

import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.RefObj;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RefListGetTask {
    ArrayList<RefObj> reflist=new ArrayList<>();
    public RefListGetTask(String username) throws JSONException {

    }
    public ArrayList<RefObj> getList(){
        return reflist;
    }
}
