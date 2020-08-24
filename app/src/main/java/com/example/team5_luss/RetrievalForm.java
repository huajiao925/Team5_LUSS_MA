package com.example.team5_luss;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.CustomAdjustmentVoucher;
import Model.ViewModel.CustomRetrieval;

public class RetrievalForm extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/Retrieval/mobile/byStatus/Approved"; //set up the API url you want to call
    String responseString;
    CustomRetrieval[] retrievals;

    Button btnConfirm;
    TextView txtItemCode;
    TextView txtDescription;
    TextView txtLocation;
    TextView txtUOM;
    TextView txtInStockQty;
    TextView txtRequestedQty;
    EditText txtRetrievedQty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieval_form);

        /*btnConfirm = findViewById(R.id.confirm_btn);
        if(btnConfirm!=null){
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitRetrival(List<int>Integer.parseInt(txtRetrievedQty.getText().toString()),int retrievalID, String collectionDate, int id ){

                    }
                }
            });
        }*/

        loadRetrievalList();
    }

    public void loadRetrievalList(){
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
                    retrievals = gson.fromJson(responseString, CustomRetrieval[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RetrievalAdapter adapter = new RetrievalAdapter(RetrievalForm.this,R.layout.retrieval_form,retrievals);
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
