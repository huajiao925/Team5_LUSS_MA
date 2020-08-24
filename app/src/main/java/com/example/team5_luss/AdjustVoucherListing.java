package com.example.team5_luss;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.CollectionPoint;
import Model.CustomAdjustmentVoucher;

public class AdjustVoucherListing extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/AdjustmentList/mobile/pendingDown"; //set up the API url you want to call
    String responseString; // result string
    CustomAdjustmentVoucher[] vouchers;// listing of vouchers

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_voucher_list);

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
                    vouchers = gson.fromJson(responseString,CustomAdjustmentVoucher[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AdjustVoucherAdapter adapter = new AdjustVoucherAdapter(AdjustVoucherListing.this,R.layout.adjust_voucher_list,vouchers);
                            ListView listView = findViewById(R.id.adjustBoardListing);
                            listView.setAdapter(adapter);
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }





}
