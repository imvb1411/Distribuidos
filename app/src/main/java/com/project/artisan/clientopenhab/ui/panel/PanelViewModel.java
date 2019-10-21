package com.project.artisan.clientopenhab.ui.panel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PanelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PanelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}