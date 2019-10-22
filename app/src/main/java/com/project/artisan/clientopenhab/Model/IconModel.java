package com.project.artisan.clientopenhab.Model;

import com.project.artisan.clientopenhab.Entity.IconEntity;
import com.project.artisan.clientopenhab.R;

import java.util.ArrayList;

public class IconModel {
    public static ArrayList<IconEntity> getItemIcons(){
        ArrayList<IconEntity> list=new ArrayList<>();
        list.add(new IconEntity(R.drawable.lightoff));
        list.add(new IconEntity(R.drawable.speedometer));
        list.add(new IconEntity(R.drawable.humedity));
        list.add(new IconEntity(R.drawable.fire));
        list.add(new IconEntity(R.drawable.thermometer));
        return list;
    }
}
