package com.sw.cocomong.fooditem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sw.cocomong.R;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodListItem>{
    private final Activity context;
    private List<FoodListItem> foodListItems;


    public FoodAdapter(Activity context, List<FoodListItem> foodListItems) {
        super(context, R.layout.favorite_item, foodListItems);
        this.context=context;
        this.foodListItems=foodListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.favorite_item,null,true);

        TextView tvFoodName =  (TextView) rowView.findViewById(R.id.food_name);
        TextView tvCategory = (TextView) rowView.findViewById(R.id.category);
        TextView tvExpire = (TextView) rowView.findViewById(R.id.food_date);
        CheckBox cbFavorite = (CheckBox) rowView.findViewById(R.id.cb_favorite);


        FoodListItem foodListItem = foodListItems.get(position);

        tvFoodName.setText(foodListItem.getName());
        tvCategory.setText(foodListItem.getCategory());
        tvExpire.setText(foodListItem.getExpire());
        cbFavorite.setChecked(foodListItem.isFavorite());

        return rowView;
    }
}