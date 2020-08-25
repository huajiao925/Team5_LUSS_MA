package com.example.team5_luss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.ViewModel.CustomRequest;
import Model.ViewModel.CustomRequestDetail;

public class DisbursementByRequestActivity extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/RequestDetails/get-by-request-mobile/"; //set up the API url you want to call
    String responseString; // result string
    ListView listView;
    TextView deptNameText;
    TextView deptRepText;
    TextView collectionPointText;
    CustomRequestDetail[] requestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_by_request);
        Intent intent = getIntent();
        int requestId = intent.getIntExtra("requestID", 0);
        String deptName = intent.getStringExtra("deptName");
        String deptRep = intent.getStringExtra("deptRep");
        String collectionPoint = intent.getStringExtra("collectionPoint");

        deptNameText = findViewById(R.id.deptName);
        deptNameText.setText(deptName);
        deptRepText = findViewById(R.id.deptRep);
        deptRepText.setText(deptRep);
        collectionPointText = findViewById(R.id.collectionPoint);
        collectionPointText.setText(collectionPoint);

        listView = findViewById(R.id.listViewReqItems);

        loadRequestItems(requestId);
    }

    public void loadRequestItems(final int id) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String target = url + id;
                        trustManager.trustAllCertificates();
                        URL url = new URL(target);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setRequestMethod("GET");
                        conn.connect();
                        InputStream in = conn.getInputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                        StringBuffer response = new StringBuffer();
                        int data = bufferedInputStream.read();
                        while (data != -1) {
                            char current = (char) data;
                            response.append(current);
                            data = bufferedInputStream.read();
                        }
                        responseString = response.toString();
                        Gson gson = new Gson();
                        requestItems = gson.fromJson(responseString, CustomRequestDetail[].class);
                        System.out.println("Reached here");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DisbursementByRequestItemAdapter adapter = new DisbursementByRequestItemAdapter(getApplicationContext(), R.layout.disb_by_req_item, requestItems);
                                listView.setAdapter(adapter);


                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}