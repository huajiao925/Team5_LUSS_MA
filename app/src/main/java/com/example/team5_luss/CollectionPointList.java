package com.example.team5_luss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import Model.CollectionPoint;
import Model.User;

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
        mLisener = (SingleChoiceListner) context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                    int responsecode = conn.getResponseCode();
                    String inline = "";
                    if(responsecode !=200){
                        throw new RuntimeException(String.valueOf(responsecode));
                    }else{
                        Scanner sc = new Scanner(url.openStream());
                        while (sc.hasNext()){
                            inline += sc.nextLine();
                        }
                    }
                    Gson gson = new Gson();
                    collectionPointList = gson.fromJson(inline,CollectionPoint[].class);
                    for(CollectionPoint c: collectionPointList) {
                        collectionNames.add(c.Location + " " + c.Description);
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();




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
