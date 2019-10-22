package com.project.artisan.clientopenhab.Model;

import android.graphics.Color;

import com.project.artisan.clientopenhab.Entity.ControlItemsEntity;

import java.util.ArrayList;

public class ControlItemsModel {

    public static ArrayList<ControlItemsEntity> getList(){
        ArrayList<ControlItemsEntity> list=new ArrayList<>();
        //Rojo
        list.add(new ControlItemsEntity(Color.parseColor("#FE0000"),"0xF720DF"));
        list.add(new ControlItemsEntity(Color.parseColor("#FF3300"),"0xF710EF"));
        list.add(new ControlItemsEntity(Color.parseColor("#FF6600"),"0xF730CF"));
        list.add(new ControlItemsEntity(Color.parseColor("#FE9900"),"0xF708F7"));
        list.add(new ControlItemsEntity(Color.parseColor("#FFCC00"),"0xF728D7"));
        //----------------------------------------------------------------------------------------

        return list;
    }
}
