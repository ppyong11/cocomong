package com.sw.cocomong.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sw.cocomong.R;
import com.sw.cocomong.view.item.RefListItem;

import java.util.List;

public class RefAdapter extends ArrayAdapter<RefListItem> {
    private final Activity context;
    private List<RefListItem> refListItems;
    RefListItem refListItem;

    public RefAdapter(Activity context, List<RefListItem> refListItems){
        super(context, R.layout.ref_item, refListItems);
        this.context=context;
        this.refListItems=refListItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.ref_item,null,true);

        TextView tvRefName= (TextView) rowView.findViewById(R.id.tv_refname);

        refListItem = refListItems.get(position);

        tvRefName.setText(refListItem.getName());

        return rowView;
    }
}
