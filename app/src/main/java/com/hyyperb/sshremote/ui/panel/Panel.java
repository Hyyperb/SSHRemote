package com.hyyperb.sshremote.ui.panel;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.hyyperb.sshremote.R;
import com.hyyperb.sshremote.SSHHandler;
import com.hyyperb.sshremote.SSHHostDetails;

public class Panel extends Fragment {

    private PanelViewModel mViewModel;

    private EditText editTextHostname;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextCommand;

    public static Panel newInstance() {
        return new Panel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_panel, container, false);

        editTextHostname = view.findViewById(R.id.editTextHostname);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextCommand = view.findViewById(R.id.editTextCommand);
        Button button = view.findViewById(R.id.buttonSSH);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sshSendHandler(v);
            }
        });
        return view;
    }
    public void sshSendHandler(View v) {
        SSHHostDetails sshHost;
        sshHost = new SSHHostDetails();

        sshHost.host     = editTextHostname.getText().toString();
        sshHost.username = editTextUsername.getText().toString();
        sshHost.password = editTextPassword.getText().toString();
        String command =   editTextCommand.getText().toString();
        SSHHandler sshHandler = new SSHHandler(sshHost);

        String output =  sshHandler.runCommand(command);

        Snackbar.make(v, output, Snackbar.LENGTH_LONG).setAction("Action", null).setAnchorView(R.id.fab).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PanelViewModel.class);
        // TODO: Use the ViewModel
    }

}