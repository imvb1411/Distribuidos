package com.project.artisan.clientopenhab.Model;

import android.graphics.Color;

import com.project.artisan.clientopenhab.Entity.ControlItemsEntity;

import java.util.ArrayList;

public class ControlItemsModel {

    public static ArrayList<ControlItemsEntity> getList(){
        ArrayList<ControlItemsEntity> list=new ArrayList<>();
        //Rojo
        list.add(new ControlItemsEntity(Color.parseColor("#FE0000"),"0xF720DF",""));
        list.add(new ControlItemsEntity(Color.parseColor("#FF3300"),"0xF710EF",""));
        list.add(new ControlItemsEntity(Color.parseColor("#FF6600"),"0xF730CF",""));
        list.add(new ControlItemsEntity(Color.parseColor("#FE9900"),"0xF708F7",""));
        list.add(new ControlItemsEntity(Color.parseColor("#FFCC00"),"0xF728D7",""));
        //----------------------------------------------------------------------------------------
        //Verde
        list.add(new ControlItemsEntity(Color.parseColor("#00FF01"),"0xF7A05F",""));
        list.add(new ControlItemsEntity(Color.parseColor("#00FE67"),"0xF7906F",""));
        list.add(new ControlItemsEntity(Color.parseColor("#01FFCD"),"0xF7B04F",""));
        list.add(new ControlItemsEntity(Color.parseColor("#00CCFF"),"0xF78877",""));
        list.add(new ControlItemsEntity(Color.parseColor("#0099FF"),"0xF7A857",""));
        //Azul
        list.add(new ControlItemsEntity(Color.parseColor("#0000FE"),"0xF7609F",""));
        list.add(new ControlItemsEntity(Color.parseColor("#3300FF"),"0xF750AF",""));
        list.add(new ControlItemsEntity(Color.parseColor("#6601FF"),"0xF7708F",""));
        list.add(new ControlItemsEntity(Color.parseColor("#9A00FF"),"0xF748B7",""));
        list.add(new ControlItemsEntity(Color.parseColor("#CC00FF"),"0xF76897",""));
        //Blanco
        list.add(new ControlItemsEntity(Color.parseColor("#FFFFFF"),"0xF7E01F",""));
        list.add(new ControlItemsEntity(Color.parseColor("#8C8C8C"),"0xF7D02F","Flash"));
        list.add(new ControlItemsEntity(Color.parseColor("#8C8C8C"),"0xF7F00F","Strobe"));
        list.add(new ControlItemsEntity(Color.parseColor("#8C8C8C"),"0xF7C837","Fade"));
        list.add(new ControlItemsEntity(Color.parseColor("#8C8C8C"),"0xF7E817","Smooth"));
        return list;
    }
}
