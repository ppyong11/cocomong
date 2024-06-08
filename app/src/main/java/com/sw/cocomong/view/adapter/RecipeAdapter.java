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

    private List<RecipeObj> recipeObjs;
    private RecipeObj recipeObj;


    public RecipeAdapter(Activity context, List<RecipeObj> recipeObjs) {
        super(context, R.layout.food_item);
        this.context=context;
        this.recipeObjs = recipeObjs;
    }

    public static AdapterView<?> createFromResource(Context applicationContext, int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.recipe_item,null,true);

        TextView tvRecipeName =  (TextView) rowView.findViewById(R.id.tv_recipename);
        TextView tvMain = (TextView) rowView.findViewById(R.id.tv_main);
        TextView tvIn1 = (TextView) rowView.findViewById(R.id.tv_in1);
        TextView tvIn2 = (TextView) rowView.findViewById(R.id.tv_in2);
        TextView tvIn3 = (TextView) rowView.findViewById(R.id.tv_in3);


        recipeObj = recipeObjs.get(position);


        tvRecipeName.setText(recipeObj.getRecipe());
        tvMain.setText(recipeObj.getMain());
        tvIn1.setText(recipeObj.getSub1());
        tvIn2.setText(recipeObj.getSub2());
        tvIn3.setText(recipeObj.getSub3());


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

    /*
    public void setRecipeItems(int position, boolean isRecipe) throws JSONException, IOException {
        RecipeObj recObj= recipeObjs.get(position);
        if(isRecipe){
            recObj.setFavorite("true");

            FoodEditTask foodEditTask = new FoodEditTask(recObj,this);
        }else {
            recObj.setFavorite("false");
            FoodEditTask foodEditTask = new FoodEditTask(recObj,this);
        }
       notifyDataSetChanged();
    }
*/

    public void onFoodEditReceived(FoodResObj foodResObj) {

    }
}

