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

import androidx.privacysandbox.tools.core.model.Method;

import com.bumptech.glide.request.Request;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.foodtask.FoodEditTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class RecipeAdapter<StringRequest> extends ArrayAdapter<FoodResObj>{
    private final Activity context;
   // private List<FoodListItemDto> foodListItemDtos;
   // private FoodListItemDto foodListItemDto;
    //private RefListItemDto refListItemDto;

    private List<FoodResObj> foodResObjs;
    private FoodResObj foodResObj;


    public RecipeAdapter(Activity context, List<FoodResObj> foodResObjs) {
        super(context, R.layout.food_item, foodResObjs);
        this.context=context;
        this.foodResObjs = foodResObjs;
    }

    public static AdapterView<?> createFromResource(Context applicationContext, int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.recipe_item,null,true);

        TextView tvRecipeName =  (TextView) rowView.findViewById(R.id.tv_recipename);
        TextView tvIn1 = (TextView) rowView.findViewById(R.id.tv_in1);
        TextView tvIn2 = (TextView) rowView.findViewById(R.id.tv_in2);
        TextView tnIn3 = (TextView) rowView.findViewById(R.id.tv_in3);


        foodResObj = foodResObjs.get(position);

        /*tvFoodName.setText(foodResObj.getFoodname());
        tvCategory.setText(foodResObj.getCategory());
        tvExpire.setText(foodResObj.getExpiredate());
        cbFavorite.setChecked(Boolean.parseBoolean(foodResObj.getFavorite()));

        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                setFavoriteItems(position,isChecked);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });*/

        return rowView;
    }
    public void setRecipeItems(int position, boolean isRecipe) throws JSONException, IOException {
        FoodResObj favObj= foodResObjs.get(position);
        if(isRecipe){
            favObj.setFavorite("true");

            FoodEditTask foodEditTask = new FoodEditTask(favObj);
        }else {
            favObj.setFavorite("false");
            FoodEditTask foodEditTask = new FoodEditTask(favObj);
        }
       notifyDataSetChanged();
    }



}

