package com.example.team5_luss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Model.CollectionPoint;

public class CollectionPointList extends DialogFragment {

    String url = "https://10.0.2.2:44312/CollectionPoint"; //set up the API url you want to call
    String responseString; // result string
    CollectionPoint[] collectionPointList = new CollectionPoint[]{};
    ArrayList<String> collectionNames = new ArrayList<>();

    int position = 0; //default selected item
    public interface SingleChoiceListner{
        void onPositiveButtonClicked(String[] list, int position);
        void onNegativeButtonClicked();
    }

    SingleChoiceListner mLisener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mLisener = (SingleChoiceListner) context;
            //call the WEB API
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String target = url;
                        trustManager.trustAllCertificates();
                        URL url = new URL(target);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setRequestMethod("GET");
                        conn.connect();
                        InputStream in = conn.getInputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                        StringBuffer response = new StringBuffer();
                        int data=bufferedInputStream.read();
                        while (data!=-1){
                            char current = (char) data;
                            response.append(current);
                            data=bufferedInputStream.read();
                        }
                        responseString = response.toString();
                        Gson gson = new Gson();
                        collectionPointList = gson.fromJson(responseString,CollectionPoint[].class);
                        for(CollectionPoint c: collectionPointList){
                            collectionNames.add(c.Location + " " + c.Description);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] cp_list = new String[collectionNames.size()];
        cp_list = collectionNames.toArray(cp_list);

        final String[] finalCp_list = cp_list;

        builder.setTitle("Select the Collection Point")
                .setSingleChoiceItems(cp_list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mLisener.onPositiveButtonClicked(finalCp_list, position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mLisener.onNegativeButtonClicked();
                    }
                });
        return builder.create();
    }
}
