package com.journaldev.searchview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class InformationDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View localView = inflater.inflate(R.layout.information_dialog, null);
        setCancelable(false);
        Button btnOK = localView.findViewById(R.id.btn_ok);
        TextView tvInfo =  localView.findViewById(R.id.tv_info);
        String lang = getArguments().getString(MainActivity.LANG);
        if(lang.equals("ru")){
            tvInfo.setText("Я и ты");
        }
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return localView;
    }
}
