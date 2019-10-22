package com.project.artisan.clientopenhab.Model;

import com.project.artisan.clientopenhab.Entity.ItemEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemModel {

    public static ItemEntity fromJSONObject(JSONObject jsonObject){
        ItemEntity item=null;
        try {
            item=new ItemEntity(
                    jsonObject.getString("state"),
                    jsonObject.getString("label"),
                    jsonObject.getString("name"),
                    jsonObject.getString("type"),
                    jsonObject.has("category")?jsonObject.getString("category"):""
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static ArrayList<ItemEntity> fromJSONArray(JSONArray jsonArray){
        ArrayList<ItemEntity> list=new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                list.add(fromJSONObject(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
