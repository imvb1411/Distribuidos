package com.project.artisan.clientopenhab.Entity;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.project.artisan.clientopenhab.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ItemAdapter extends ArrayAdapter<ItemEntity> {
    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<ItemEntity> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ItemEntity objItem=getItem(position);
        LayoutInflater inflater= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contenedor=inflater.inflate(R.layout.res_item,parent,false);
        TextView name=contenedor.findViewById(R.id.tvName);
        TextView label=contenedor.findViewById(R.id.tvLabel);
        TextView state=contenedor.findViewById(R.id.tvState);
        Switch sw=contenedor.findViewById(R.id.swButton);
        sw.setVisibility(View.INVISIBLE);
        name.setText(objItem.getName());
        label.setText(objItem.getLabel());
        state.setText(objItem.getState());
        if(objItem.getType().equals("Switch")){
            sw.setVisibility(View.VISIBLE);
        }
        return contenedor;
    }
}