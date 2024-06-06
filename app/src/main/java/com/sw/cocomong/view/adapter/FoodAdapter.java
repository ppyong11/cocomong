package com.sw.cocomong.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.FoodListItemDto;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.foodtask.FoodEditTask;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodResObj> implements FoodEditTask.FoodEditTaskListener {
    private final Activity context;
   // private List<FoodListItemDto> foodListItemDtos;
   // private FoodListItemDto foodListItemDto;
    //private RefListItemDto refListItemDto;

    private List<FoodResObj> foodResObjs;
    private FoodResObj foodResObj;


    public FoodAdapter(Activity context, List<FoodResObj> foodResObjs) {
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
        View rowView = inflater.inflate(R.layout.food_item,null,true);

        TextView tvFoodName =  (TextView) rowView.findViewById(R.id.tv_listFoodName);
        TextView tvCategory = (TextView) rowView.findViewById(R.id.tv_listCategory);
        TextView tvExpire = (TextView) rowView.findViewById(R.id.tv_listFoodExpire);
        CheckBox cbFavorite = (CheckBox) rowView.findViewById(R.id.cb_favorite);


        foodResObj = foodResObjs.get(position);

        tvFoodName.setText(foodResObj.getFoodname());
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
        });

        return rowView;
    }
    public void setFavoriteItems(int position, boolean isFavorite) throws JSONException, IOException  {
        FoodResObj favObj= foodResObjs.get(position);
        if(isFavorite){
            favObj.setFavorite("true");

            FoodEditTask foodEditTask = new FoodEditTask(favObj,this);
        }else {
            favObj.setFavorite("false");
            FoodEditTask foodEditTask = new FoodEditTask(favObj,this);
        }
       notifyDataSetChanged();
    }

    @Override
    public void onFoodEditReceived(FoodResObj foodResObj) {

    }
}
