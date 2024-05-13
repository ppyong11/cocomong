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
import com.sw.cocomong.dto.RefListItemDto;

import java.util.List;

public class RefAdapter extends ArrayAdapter<RefListItemDto> {
    private final Activity context;
    private List<RefListItemDto> refListItemDtos;
    RefListItemDto refListItemDto;

    public RefAdapter(Activity context, List<RefListItemDto> refListItemDtos){
        super(context, R.layout.ref_item, refListItemDtos);
        this.context=context;
        this.refListItemDtos = refListItemDtos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.ref_item,null,true);

        TextView tvRefName= (TextView) rowView.findViewById(R.id.tv_refname);

        refListItemDto = refListItemDtos.get(position);

        tvRefName.setText(refListItemDto.getName());

        return rowView;
    }
}
