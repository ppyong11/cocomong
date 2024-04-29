package com.sw.cocomong.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.cocomong.view.activity.FoodInfoActivity;
import com.sw.cocomong.view.activity.MainFoodListActivity;
import com.sw.cocomong.R;
import com.sw.cocomong.view.item.FoodListItem;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodListItem>{
    private final Activity context;
    private List<FoodListItem> foodListItems;
    FoodListItem foodListItem;


    public FoodAdapter(Activity context, List<FoodListItem> foodListItems) {
        super(context, R.layout.food_item, foodListItems);
        this.context=context;
        this.foodListItems=foodListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.food_item,null,true);

        TextView tvFoodName =  (TextView) rowView.findViewById(R.id.tv_listFoodName);
        TextView tvCategory = (TextView) rowView.findViewById(R.id.tv_listCategory);
        TextView tvExpire = (TextView) rowView.findViewById(R.id.tv_listFoodExpire);
        CheckBox cbFavorite = (CheckBox) rowView.findViewById(R.id.cb_favorite);


        foodListItem = foodListItems.get(position);

        tvFoodName.setText(foodListItem.getName());
        tvCategory.setText(foodListItem.getCategory());
        tvExpire.setText(foodListItem.getExpire());
        cbFavorite.setChecked(foodListItem.isFavorite());

        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setFavoriteItems(position,isChecked);
        });

        return rowView;
    }
    public void setFavoriteItems(int position, boolean isFavorite){
        if(isFavorite){
            foodListItems.get(position).setFavorite(true);

            FoodListItem.getFavoriteItems().add(foodListItems.get(position));
            //System.out.println("Favorite: "+MainFoodListActivity.favoriteItems);
            //System.out.println("Food: "+MainFoodListActivity.foodListItems);
            //System.out.println("Adapter: "+ foodListItems);

        }else {
            foodListItems.get(position).setFavorite(false);
            FoodListItem.getFavoriteItems().remove(foodListItems.get(position));


            //System.out.println("Favorite: "+MainFoodListActivity.favoriteItems);
            //System.out.println("Food: "+MainFoodListActivity.foodListItems);
            //System.out.println("Adapter: "+ foodListItems);
        }

       notifyDataSetChanged();
    }
}
