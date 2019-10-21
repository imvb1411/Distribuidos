package com.project.artisan.clientopenhab.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.project.artisan.clientopenhab.Model.Global;
import com.project.artisan.clientopenhab.R;

public class SettingsFragment extends Fragment {

    private TextInputLayout address;
    private TextInputLayout port;
    private Button buttonConnect;
    private Global global;
    private int progress=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        address=view.findViewById(R.id.tilAddress);
        this.port=view.findViewById(R.id.tilPort);
        buttonConnect=view.findViewById(R.id.connectButton);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!address.getEditText().getText().toString().isEmpty() && !port.getEditText().getText().toString().isEmpty()) {
                    String addressServer=address.getEditText().getText().toString();
                    int portServer=Integer.valueOf(port.getEditText().getText().toString());
                    global=new Global(addressServer,portServer);
                    Navigation.findNavController(view).navigate(R.id.action_nav_settings_to_nav_home);
                }else{
                    Toast.makeText(view.getContext(), "Rellene los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}