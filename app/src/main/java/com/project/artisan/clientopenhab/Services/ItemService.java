package com.project.artisan.clientopenhab.Services;

import android.content.Context;
import android.icu.util.LocaleData;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.project.artisan.clientopenhab.Entity.ItemEntity;
import com.project.artisan.clientopenhab.LoginActivity;
import com.project.artisan.clientopenhab.Model.Global;
import com.project.artisan.clientopenhab.Model.ItemModel;
import com.project.artisan.clientopenhab.R;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ItemService {

    private ArrayList<ItemEntity> list;
    private RequestQueue requestQueue;
    private RequestQueue requestQueuePost;
    private OkHttpClient okHttpClient;

    public ItemService(Context context) {
        requestQueue= Volley.newRequestQueue(context);
        requestQueuePost=Volley.newRequestQueue(context);
        okHttpClient=new OkHttpClient();
        list=new ArrayList<>();
    }

    public void getItems(final VolleyCallBack callBack){
        String url= Global.url+"/rest/items";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            list = ItemModel.fromJSONArray(response);
                            callBack.onSuccess();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("Error","PROBLEMA: "+error);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private Call createHttpGetMethodCall(String url)
    {
        try {
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

            // Set url.
            builder = builder.url(url);

            // Create request object.
            okhttp3.Request request = builder.build();

            // Create a new Call object.
            Call call = okHttpClient.newCall(request);
            return call;
        }catch (Exception ex){
            Log.d("GET ERROR:",ex.getMessage());
            return  null;
        }
    }

    public void login(final Context context, final VolleyCallBack callBack, final String username, final String password){
        String user=username.replace("@","%40");
        String url= "https://"+user+":"+password+"@home.myopenhab.org/rest/items";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("LIST",response.toString());
                            list = ItemModel.fromJSONArray(response);
                            if(list.size()>0){
                            callBack.onSuccess();
                            }else{
                                callBack.onError();
                            }
                        } catch (Exception e) {
                            Log.d("ERROR",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        callBack.onError();
                        Log.d("Error","PROBLEMA: "+error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                String creds = String.format("%s:%s",username,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization",auth);
                Log.d("Auth",auth);
                Log.d("LIST",list.toString());
                //params.put("Authorization","Basic aW12YjE0QGdtYWlsLmNvbTo3OTEzODUyNDU2VmFyZ2FzIQ==");
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    public void sincronize(View item){
        final TextView state=item.findViewById(R.id.tvState);
        final TextView label=item.findViewById(R.id.tvLabel);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getItems(new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        for(ItemEntity entity:list){
                            if(entity.getLabel().equals(label.getText().toString())){
                                state.setText(entity.getState());
                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        },0,2000);
    }

    public void sendcommand(View item, ItemEntity itemEntity, final VolleyCallBack callBack){
        final TextView state=item.findViewById(R.id.tvState);
        String jsonMimeType = "text/plain";
        MediaType jsonContentType = MediaType.parse(jsonMimeType);
        String jsonString;
        switch (itemEntity.getType()){
            case "Switch":
                jsonString = "ON";
                if(state.getText().toString().equals("ON")){
                    jsonString = "OFF";
                }
                break;
            case "Number":
                jsonString=itemEntity.getState();
                break;
                default:
                    jsonString="";
                    break;
        }
        RequestBody jsonRequestBody = RequestBody.create(jsonContentType, jsonString);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        String url=Global.url+"/rest/items/"+itemEntity.getName();
        builder = builder.url(url);
        builder = builder.post(jsonRequestBody);
        okhttp3.Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ERROR", e.getMessage());
                callBack.onError();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                int respCode = response.code();
                String respMsg = response.message();
                String respBody = response.body().string();
                callBack.onSuccess();
                Log.d("RESPONSE", "Response code : " + respCode);
                Log.d("RESPONSE", "Response message : " + respMsg);
                Log.d("RESPONSE", "Response body : " + respBody);
            }
        });
        //sendRequestBody(Global.url+"/rest/items/"+itemEntity.getName(), jsonRequestBody);
    }

    private void sendRequestBody(String url, RequestBody requestBody)
    {
        // Create request builder.
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        // Set url.
        builder = builder.url(url);
        // Post request body.
        builder = builder.post(requestBody);
        // Create request object.
        okhttp3.Request request = builder.build();
        // Get okhttp3.Call object.
        Call call = okHttpClient.newCall(request);
        // Execute the call asynchronously.
        call.enqueue(new Callback() {
            // If request fail.
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ERROR", e.getMessage());
            }
            // If request success.
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                int respCode = response.code();
                String respMsg = response.message();
                String respBody = response.body().string();

                Log.d("RESPONSE", "Response code : " + respCode);
                Log.d("RESPONSE", "Response message : " + respMsg);
                Log.d("RESPONSE", "Response body : " + respBody);
            }
        });
    }

    public ArrayList<ItemEntity> getList() {
        return list;
    }
}
