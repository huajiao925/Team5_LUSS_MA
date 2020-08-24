package com.example.team5_luss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.ViewModel.CustomRequest;

public class DisbursementActivity extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/Request/get-request-by-status-mobile/4"; //set up the API url you want to call
    //String url = "https://10.0.2.2:44312/Request/get-approved-request";
    String responseString; // result string
    ListView listView;
    CustomRequest[] approvedRequests;
    //Request[] approvedRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        listView = findViewById(R.id.listViewDisbursement);
        loadApprovedRequestList();

    }

    public void loadApprovedRequestList(){
        try{
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
                        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        Gson gson = new Gson();
                        approvedRequests = gson.fromJson(responseString, CustomRequest[].class);
                        //approvedRequests = gson.fromJson(responseString, Request[].class);
                        //approvedRequests = gson.fromJson(String.valueOf(response.getJSONObject("request")), Request[].class);
                        System.out.println("Reached here");

                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                DisbursementListAdapter adapter = new DisbursementListAdapter(getApplicationContext(),R.layout.disb_req_item,approvedRequests);
                                listView.setAdapter(adapter);
                            }
                        });

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


}