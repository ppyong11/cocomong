package com.sw.cocomong.Favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sw.cocomong.R;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter{

    public FoodAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    ArrayList<FoodListItem> foodListItems = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return foodListItems.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return foodListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        context=getContext();
        FoodListItem foodListItem = foodListItems.get(position);

        if(convertView !=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorite_item,parent,false);
        }
        TextView tvFoodName = convertView.findViewById(R.id.tv_foodName);
        TextView tvCategory = convertView.findViewById(R.id.tv_category);
        TextView tvExpire = convertView.findViewById(R.id.tv_exprire);
        CheckBox cbFavorite = convertView.findViewById(R.id.cb_favorite);

        tvFoodName.setText(foodListItem.getName());
        tvCategory.setText(foodListItem.getCategory());
        tvExpire.setText(foodListItem.getExpire());
        cbFavorite.setChecked(foodListItem.isFavorite());

        return convertView;
    }
}
