package com.project.artisan.clientopenhab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.project.artisan.clientopenhab.Services.ItemService;
import com.project.artisan.clientopenhab.Services.VolleyCallBack;
import com.project.artisan.clientopenhab.ui.settings.SettingsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tilUsername=findViewById(R.id.username);
        tilPassword=findViewById(R.id.password);
        loading=findViewById(R.id.loading);
        Button bLogin=findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                login();

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 3000);

            }
        });
    }

    private void login(){
        String userName=tilUsername.getEditText().getText().toString();
        String password=tilPassword.getEditText().getText().toString();
        final ItemService service=new ItemService(LoginActivity.this);
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        loading.setVisibility(View.INVISIBLE);
        LoginActivity.this.startActivity(myIntent);
//        service.login(LoginActivity.this, new VolleyCallBack() {
//            @Override
//            public void onSuccess() {
//                if(service.getList().size()>0) {
//                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//                    loading.setVisibility(View.INVISIBLE);
//                    LoginActivity.this.startActivity(myIntent);
//                }else{
//                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError() {
//                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
//                loading.setVisibility(View.INVISIBLE);
//                tilUsername.getEditText().setText("");
//                tilPassword.getEditText().setText("");
//            }
//        },userName.trim(),password);
    }
}
