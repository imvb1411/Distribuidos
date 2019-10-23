package com.project.artisan.clientopenhab.ui.panel;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.textfield.TextInputLayout;
import com.project.artisan.clientopenhab.Entity.ControlItemsEntity;
import com.project.artisan.clientopenhab.Entity.IconAdapter;
import com.project.artisan.clientopenhab.Entity.IconEntity;
import com.project.artisan.clientopenhab.Entity.ItemEntity;
import com.project.artisan.clientopenhab.Model.ControlItemsModel;
import com.project.artisan.clientopenhab.R;
import com.project.artisan.clientopenhab.Services.ItemService;
import com.project.artisan.clientopenhab.Services.VolleyCallBack;
import com.project.artisan.clientopenhab.Model.IconModel;

import java.util.ArrayList;

public class PanelFragment extends Fragment {

    private int rows=3;
    private final int COLS=2;
    private ItemService itemService;
    private AlertDialog dialogAddItem;
    private AlertDialog dialogSetValueItem;
    private AlertDialog dialogControlFoco;
    private AlertDialog dialogTvControl;
    private Spinner sItems;
    private TextInputLayout tilUnit;
    private Spinner sIcons;
    private Button addItem;
    private Button setValue;
    private String typeFormat="Type : %s";
    private TextView value;
    private SeekBar seekBar;
    private View[][] panelItems;
    private View[][] controlItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PanelViewModel panelViewModel = ViewModelProviders.of(this).get(PanelViewModel.class);
        return inflater.inflate(R.layout.fragment_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton tableSettings=view.findViewById(R.id.bEditTable);
        ToggleButton mode=view.findViewById(R.id.tbMode);
        itemService=new ItemService(view.getContext());
        panelItems=new View[rows][COLS];
        controlItems=new View[4][5];
        initAddItemDialog(view.getContext());
        initSetValueItemDialog(view.getContext());
        //initDialogControlFoco(view.getContext());
        tableSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySettings();
            }
        });
        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                modeRun(b);
            }
        });
        TableLayout tableLayout=view.findViewById(R.id.table);
        tableLayout.removeAllViews();
        populateButtons();
    }

    private void initAddItemDialog(final Context context){
        final AlertDialog.Builder builderDialogAddItem=new AlertDialog.Builder(context);
        View viewDialog=getLayoutInflater().inflate(R.layout.dialog_additem, null);
        builderDialogAddItem.setView(viewDialog);
        sItems=viewDialog.findViewById(R.id.sItems);
        sIcons=viewDialog.findViewById(R.id.sIcons);
        addItem=viewDialog.findViewById(R.id.bAddItem);
        final TextView tvType=viewDialog.findViewById(R.id.tvType);
        tilUnit=viewDialog.findViewById(R.id.tilUnit);
        itemService.getItems(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                sItems.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,itemService.getList()));
                sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemEntity item=(ItemEntity) sItems.getSelectedItem();
                        tvType.setText(String.format(typeFormat, item.getType()));
                        tilUnit.setVisibility(View.INVISIBLE);
                        if(item.getType().equals("Switch")){
                            tilUnit.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                sIcons.setAdapter(new IconAdapter(context, IconModel.getItemIcons()));
                dialogAddItem=builderDialogAddItem.create();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initSetValueItemDialog(final Context context){
        final AlertDialog.Builder builderDialogAddItem=new AlertDialog.Builder(context);
        View viewDialog=getLayoutInflater().inflate(R.layout.setvalueitem, null);
        builderDialogAddItem.setView(viewDialog);
        value=viewDialog.findViewById(R.id.tvValue);
        seekBar=viewDialog.findViewById(R.id.seekBar);
        setValue=viewDialog.findViewById(R.id.bSetValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress=i;
                value.setText("Max Temp."+progress +" ºC");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                value.setText("Max Temp."+progress+" ºC");
            }
        });
        dialogSetValueItem=builderDialogAddItem.create();
    }

    private void initDialogControlFoco(final Context context, final View itemPanel, final ItemEntity item){
        final AlertDialog.Builder builderDialogControlFoco=new AlertDialog.Builder(context);
        View viewDialog=getLayoutInflater().inflate(R.layout.light_item, null);
        final ToggleButton powerFoco=viewDialog.findViewById(R.id.tbPowerLight);

        powerFoco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                if(b){
                    aux.setState("0xF7C03F");
                }else{
                    aux.setState("0xF740BF");
                }
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                    }
                });
            }
        });
        Button moreBright=viewDialog.findViewById(R.id.bSubirBrillo);
        moreBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xF700FF");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        Button lessBright=viewDialog.findViewById(R.id.bBajarBrillo);
        lessBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xF7807F");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        final TableLayout tableLayout=viewDialog.findViewById(R.id.tlControlFoco);
        tableLayout.removeAllViews();
        int index=0;
        final ArrayList<ControlItemsEntity> list= ControlItemsModel.getList();
        for (int i=0;i<4;i++) {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 20, 1.0f));
            tableLayout.addView(row);
            for (int j = 0; j < 5; j++) {
                final int rowT = i;
                final int colT = j;
                final TextView textView=new TextView(context);
                textView.setLayoutParams(new TableRow.LayoutParams(20,TableRow.LayoutParams.MATCH_PARENT,1.0f));
                if(index<list.size()) {
                    textView.setText(list.get(index).getName());
                    textView.setBackgroundColor(list.get(index).getBackground());
                    final int finalIndex = index;
                    final ItemEntity aux=new ItemEntity();
                    aux.setName(item.getName());
                    aux.setType(item.getType());
                    aux.setCategory(item.getCategory());
                    aux.setState(list.get(index).getValue());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {

                                }
                            });
                        }
                    });
                }
                index++;
                this.controlItems[i][j]=textView;
                row.addView(textView);
            }
        }
        builderDialogControlFoco.setView(viewDialog);
        dialogControlFoco=builderDialogControlFoco.create();
    }

    private void initDialogTvControl(final Context context, final View itemPanel, final ItemEntity item){
        final AlertDialog.Builder builderDialogAddItem=new AlertDialog.Builder(context);
        View viewDialog=getLayoutInflater().inflate(R.layout.tv_item, null);
        builderDialogAddItem.setView(viewDialog);
        ImageButton powerTv=viewDialog.findViewById(R.id.ibPowerTv);
        Button channelM=viewDialog.findViewById(R.id.bTvChM);
        Button channelm=viewDialog.findViewById(R.id.bTvChm);
        Button volumeM=viewDialog.findViewById(R.id.bTvVM);
        Button volumem=viewDialog.findViewById(R.id.bTvVm);
        Button video=viewDialog.findViewById(R.id.bTvVideo);
        powerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xBD807F");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        channelM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xBDD02F");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        channelm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xBDF00F");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        volumeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xBD52AD");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        volumem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xBD926D");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ItemEntity aux=new ItemEntity();
                aux.setName(item.getName());
                aux.setType(item.getType());
                aux.setCategory(item.getCategory());
                aux.setState("0xBD629D");
                itemService.sendcommand(itemPanel, aux, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        dialogTvControl=builderDialogAddItem.create();
    }

    private void populateButtons(){
        final TableLayout tableLayout=this.getView().findViewById(R.id.table);
        tableLayout.removeAllViews();
        for (int i=0;i<rows;i++){
            TableRow row=new TableRow(getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,50,1.0f));
            tableLayout.addView(row);
            for (int j=0;j<COLS;j++){
                final int rowT=i;
                final int colT=j;
                final View itemPanel=getLayoutInflater().inflate(R.layout.panel_item,null);
                itemPanel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1.0f));
                ImageButton edit=itemPanel.findViewById(R.id.ibEdit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "button:"+rowT+","+colT, Toast.LENGTH_SHORT).show();
                        addItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final View itemSelected=panelItems[rowT][colT];
                                final ImageButton itemLogo=itemSelected.findViewById(R.id.ibLogoItem);
                                TextView label=itemSelected.findViewById(R.id.tvLabel);
                                final TextView state=itemSelected.findViewById(R.id.tvState);
                                IconEntity currentIcon=(IconEntity)sIcons.getSelectedItem();
                                itemLogo.setImageResource(currentIcon.getIconInt());
                                final ItemEntity item=(ItemEntity) sItems.getSelectedItem();
                                label.setText(item.getLabel());
                                state.setText(item.getState().concat(tilUnit.getEditText().getText().toString()));
                                itemService.sincronize(itemSelected);
                                switch (item.getType()){
                                    case "Switch":
                                        itemLogo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                itemService.sendcommand(itemSelected, item, new VolleyCallBack() {
                                                    @Override
                                                    public void onSuccess() {
                                                        if(state.getText().toString().equals("ON")){
                                                            itemLogo.setImageResource(R.drawable.lightoff);
                                                        }else{
                                                            itemLogo.setImageResource(R.drawable.lighton);
                                                        }
                                                    }
                                                    @Override
                                                    public void onError() {

                                                    }
                                                });
                                            }
                                        });
                                        break;
                                    case "Number":
                                        itemLogo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogSetValueItem.show();
                                            }
                                        });
                                        setValue.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                item.setState(seekBar.getProgress() + "");
                                                itemService.sendcommand(itemSelected, item, new VolleyCallBack() {
                                                    @Override
                                                    public void onSuccess() {
                                                        state.setText(seekBar.getProgress() + "");
                                                        dialogSetValueItem.dismiss();
                                                    }

                                                    @Override
                                                    public void onError() {
                                                        dialogSetValueItem.dismiss();
                                                    }
                                                });
                                            }
                                        });
                                        break;
                                    case "String":
                                        if(item.getCategory().equals("Color")) {
                                            itemLogo.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    initDialogControlFoco(getContext(),itemSelected,item);
                                                    dialogControlFoco.show();
                                                }
                                            });
                                        }else if(item.getCategory().equals("TV")){
                                            itemLogo.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    initDialogTvControl(getContext(),itemSelected,item);
                                                    dialogTvControl.show();
                                                }
                                            });
                                        }
                                        break;
                                    default:
                                        break;
                                }
//                                if(item.getType().equals("Switch")){
//
//                                }else if(item.getType().equals("Number")){
//
//                                }if(item.getType().equals("Number")) {
//                                    setValue.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            item.setState(seekBar.getProgress() + "");
//                                            itemService.sendcommand(itemSelected, item, new VolleyCallBack() {
//                                                @Override
//                                                public void onSuccess() {
//                                                    state.setText(seekBar.getProgress() + "");
//                                                    dialogSetValueItem.dismiss();
//                                                }
//
//                                                @Override
//                                                public void onError() {
//                                                    dialogSetValueItem.dismiss();
//                                                }
//                                            });
//                                        }
//                                    });
//                                }
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(label,28,29,2, TypedValue.COMPLEX_UNIT_SP);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(state,28,29,2,TypedValue.COMPLEX_UNIT_SP);
                                itemLogo.setVisibility(View.VISIBLE);
                                label.setVisibility(View.VISIBLE);
                                state.setVisibility(View.VISIBLE);
                            }
                        });
                        dialogAddItem.show();
                    }
                });
                ImageButton itemLogo=itemPanel.findViewById(R.id.ibLogoItem);
                TextView label=itemPanel.findViewById(R.id.tvLabel);
                TextView state=itemPanel.findViewById(R.id.tvState);
                itemLogo.setVisibility(View.INVISIBLE);
                label.setVisibility(View.INVISIBLE);
                state.setVisibility(View.INVISIBLE);
                this.panelItems[i][j]=itemPanel;
                row.addView(itemPanel);
            }
        }
    }

    private void modeRun(boolean b){
        for(int i=0;i<rows;i++){
            for (int j=0;j<COLS;j++){
                View item=panelItems[i][j];
                ImageButton edit=item.findViewById(R.id.ibEdit);
                edit.setVisibility(View.VISIBLE);
                if(b){
                    edit.setVisibility(View.GONE);
                }

            }
        }
    }

    private void applySettings(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View dView=getLayoutInflater().inflate(R.layout.dialog_tablesettings,null);
        final TextInputLayout rowsE=dView.findViewById(R.id.tRow);
        builder.setView(dView);
        final AlertDialog dialog=builder.create();
        Button save=dView.findViewById(R.id.bSaveTableSettings);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rowsE.getEditText().getText().toString().isEmpty()) {
                    rows = Integer.valueOf(rowsE.getEditText().getText().toString());
                    panelItems = new View[rows][COLS];
                    populateButtons();
                }
                Toast.makeText(getContext(), "Error: Num rows less than 0", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}