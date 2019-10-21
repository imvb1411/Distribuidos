package com.project.artisan.clientopenhab.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.artisan.clientopenhab.Entity.ItemAdapter;
import com.project.artisan.clientopenhab.Entity.ItemEntity;
import com.project.artisan.clientopenhab.Model.Global;
import com.project.artisan.clientopenhab.Model.ItemModel;
import com.project.artisan.clientopenhab.R;
import com.project.artisan.clientopenhab.ui.settings.SettingsFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ArrayList<ItemEntity> items;
    private ArrayAdapter<ItemEntity> itemAdapter;
    private RequestQueue requestQueue;
    private ListView listItems;
    private Timer myTimer;
    private SettingsFragment settingsFragment;
    private int progress=0;
    private ProgressBar progressBar;
    private Handler handler=new Handler();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestQueue= Volley.newRequestQueue(view.getContext());
        listItems=view.findViewById(R.id.list);
//        myTimer=new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
                loadItems(getContext());
//            }
//        },0,3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        //myTimer.cancel();
    }



    private void loadItems(final Context context) {
        String url=Global.url+"/rest/items";
//        progressBar.setProgress(progress);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            items= ItemModel.fromJSONArray(response);
                            itemAdapter = new ItemAdapter(context,android.R.layout.simple_list_item_1,items );
                            listItems.setAdapter(itemAdapter);

                        } catch (Exception e) {
                            showToast("Error obteniendo los items.");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        showToast("ERROR:"+error.getMessage());
                        Log.d("Error","PROBLEMA: "+error);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}