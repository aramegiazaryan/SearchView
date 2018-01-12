package com.journaldev.searchview;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LanguageDialog extends DialogFragment implements View.OnClickListener {


    private ImageView imgRus;
    private ImageView imgEng;
    private View localView;


 @Nullable
 @Override
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
         localView = paramLayoutInflater.inflate(R.layout.language_dialog, null);
         setCancelable(false);
         imgRus = (ImageView) localView.findViewById(R.id.img_rus);
         imgEng = (ImageView) localView.findViewById(R.id.img_eng);
        imgEng.setOnClickListener(this);
        imgRus.setOnClickListener(this);
        return localView;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.img_eng : {
               setLangAndDismiss("en");
               break;
            }
            case R.id.img_rus : {
                setLangAndDismiss("ru");
                break;
            }


        }
    }

    private void setLangAndDismiss (String lang){
        InformationDialog dialog = new InformationDialog();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.LANG,lang);
        dialog.setArguments(bundle);
        DialogListener activity = (DialogListener) getActivity();
        activity.updateResult(lang);
        dialog.show(getFragmentManager(), null);
        dismiss();
    }


    public interface DialogListener {
        void updateResult(String lang);
    }
}
