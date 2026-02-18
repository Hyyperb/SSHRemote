package com.hyyperb.sshremote.ui.panel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PanelViewModel extends ViewModel {

    private final MutableLiveData<String> statusLine;

    public PanelViewModel(){
        statusLine = new MutableLiveData<>();
        statusLine.setValue("Offline");
    }

    private void toggleStatus(){
        if (statusLine.getValue().equals("Offline")) {
            this.statusLine.setValue("Online");
        } else {
            this.statusLine.setValue("Offline");
        }

    }

    public LiveData<String> getText() {
        return statusLine;
    }
}