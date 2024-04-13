package com.sw.cocomong.fooditem;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sw.cocomong.MainFoodListActivity;
import com.sw.cocomong.R;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodListItem>{
    private final Activity context;
    private List<FoodListItem> foodListItems;


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


        FoodListItem foodListItem = foodListItems.get(position);

        tvFoodName.setText(foodListItem.getName());
        tvCategory.setText(foodListItem.getCategory());
        tvExpire.setText(foodListItem.getExpire());
        cbFavorite.setChecked(foodListItem.isFavorite());

        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setFavoriteItems(isChecked);
        });

        return rowView;
    }
    public void setFavoriteItems(boolean isFavorite){
        MainFoodListActivity.favoriteItems.clear();

        if(isFavorite){
           for(FoodListItem item : MainFoodListActivity.foodListItems){
               // 로직 오류
               // TODO: 2024-04-14 favoriteItems에 추가하는 로직 이랑 버튼 안되는거 오류잡기
               //if(item.isFavorite()){
                    //MainFoodListActivity.favoriteItems.add(item);
                //}
            }
        }

       // notifyDataSetChanged();
    }
}
