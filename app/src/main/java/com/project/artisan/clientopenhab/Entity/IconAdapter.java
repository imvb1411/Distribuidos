package com.project.artisan.clientopenhab.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.project.artisan.clientopenhab.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IconAdapter extends ArrayAdapter<IconEntity> {

    public IconAdapter(@NonNull Context context, @NonNull ArrayList<IconEntity> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.icon_spinner_row,parent,false);
        }
        ImageView icon=convertView.findViewById(R.id.ivIcon);
        IconEntity currentIcon=getItem(position);
        if(currentIcon!=null) {
            icon.setImageResource(currentIcon.getIconInt());
        }
        return convertView;
    }
}
