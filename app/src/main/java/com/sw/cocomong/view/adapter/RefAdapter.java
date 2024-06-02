package com.sw.cocomong.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefListItemDto;

import java.util.List;

public class RefAdapter extends ArrayAdapter<RefObj> {
    private final Activity context;
    //private List<RefListItemDto> refListItemDtos;
    //RefListItemDto refListItemDto;
    RefObj refObj;
    List<RefObj> refList;

    public RefAdapter(Activity context, List<RefObj> refList){
        super(context, R.layout.ref_item, refList);
        this.context=context;
        this.refList = refList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.ref_item,null,true);

        TextView tvRefName= (TextView) rowView.findViewById(R.id.tv_refname);

        refObj = refList.get(position);

        tvRefName.setText(refObj.getRefname());

        return rowView;
    }
}
