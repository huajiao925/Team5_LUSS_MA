package com.example.team5_luss;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.ViewModel.CustomRequest;

public class DisbursementActivity extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/Request/get-request-by-status-mobile/1"; //set up the API url you want to call
    //String url = "https://10.0.2.2:44312/Request/get-approved-request";
    String responseString; // result string
    ListView listView;
    CustomRequest[] approvedRequests;
    public static int DISBURSED = 1;
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
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                        Toast.makeText(getApplicationContext(), "RequestID "+(approvedRequests[position].getRequestID()), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DisbursementActivity.this,DisbursementByRequestActivity.class);
                                        intent.putExtra("requestID",approvedRequests[position].getRequestID());
                                        intent.putExtra("deptName",approvedRequests[position].getDepartmentName());
                                        intent.putExtra("deptRep",approvedRequests[position].getDepartmentRep());
                                        intent.putExtra("collectionPoint",approvedRequests[position].getCollectionPoint());
                                        //startActivity(intent);
                                        startActivityForResult(intent,DISBURSED);
                                    }



                                        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DISBURSED) {
            if (resultCode == RESULT_OK) {
                loadApprovedRequestList();
            }
        }
    }

}