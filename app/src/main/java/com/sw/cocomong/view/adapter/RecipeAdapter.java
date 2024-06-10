package com.sw.cocomong.view.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.Object.RecipeObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.foodtask.FoodEditTask;
import com.sw.cocomong.task.foodtask.RecipeListGetTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class RecipeAdapter extends ArrayAdapter<RecipeObj> implements RecipeListGetTask.RecipeListGetTaskListener {
    private final Activity context;
   // private List<FoodListItemDto> foodListItemDtos;
   // private FoodListItemDto foodListItemDto;
    //private RefListItemDto refListItemDto;

    private List<RecipeObj> recipeObjs;
    private RecipeObj recipeObj;


    public RecipeAdapter(Activity context, List<RecipeObj> recipeObjs) {
        super(context, R.layout.recipe_item, recipeObjs);
        this.context=context;
        this.recipeObjs = recipeObjs;
    }

    public static AdapterView<?> createFromResource(Context applicationContext, int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.recipe_item, null, true);

        TextView tvRecipeName = (TextView) rowView.findViewById(R.id.tv_recipename);
        TextView tvIn1 = (TextView) rowView.findViewById(R.id.tv_in1);
        TextView tvIn2 = (TextView) rowView.findViewById(R.id.tv_in2);
        TextView tvIn3 = (TextView) rowView.findViewById(R.id.tv_in3);

        recipeObj = getItem(position); // 여기에서 getItem을 사용하여 올바른 객체를 가져옵니다

        tvRecipeName.setText(recipeObj.getRecipename());
        tvIn1.setText(recipeObj.getMain());
        tvIn2.setText(recipeObj.getSub1());
        tvIn3.setText(recipeObj.getSub2());
        return rowView;
    }

    @Override
    public void onRecipeListReceived(List<RecipeObj> recipeObjs) {

    }
}

