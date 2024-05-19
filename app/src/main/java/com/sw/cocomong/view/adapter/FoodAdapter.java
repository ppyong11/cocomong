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

import com.sw.cocomong.R;
import com.sw.cocomong.dto.FoodListItemDto;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodListItemDto>{
    private final Activity context;
    private List<FoodListItemDto> foodListItemDtos;
    private FoodListItemDto foodListItemDto;


    public FoodAdapter(Activity context, List<FoodListItemDto> foodListItemDtos) {
        super(context, R.layout.food_item, foodListItemDtos);
        this.context=context;
        this.foodListItemDtos = foodListItemDtos;
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


        foodListItemDto = foodListItemDtos.get(position);

        tvFoodName.setText(foodListItemDto.getFoodname());
        tvCategory.setText(foodListItemDto.getCategory());
        tvExpire.setText(foodListItemDto.getExpire());
        cbFavorite.setChecked(foodListItemDto.isFavorite());

        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setFavoriteItems(position,isChecked);
        });

        return rowView;
    }
    public void setFavoriteItems(int position, boolean isFavorite){
        if(isFavorite){
            foodListItemDtos.get(position).setFavorite(true);

            FoodListItemDto.getFavoriteItems().add(foodListItemDtos.get(position));
            //System.out.println("Favorite: "+MainFoodListActivity.favoriteItems);
            //System.out.println("Food: "+MainFoodListActivity.foodListItems);
            //System.out.println("Adapter: "+ foodListItems);

        }else {
            foodListItemDtos.get(position).setFavorite(false);
            FoodListItemDto.getFavoriteItems().remove(foodListItemDtos.get(position));


            //System.out.println("Favorite: "+MainFoodListActivity.favoriteItems);
            //System.out.println("Food: "+MainFoodListActivity.foodListItems);
            //System.out.println("Adapter: "+ foodListItems);
        }

       notifyDataSetChanged();
    }
}
