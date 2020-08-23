package com.example.team5_luss;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CollectionActivity extends AppCompatActivity implements CollectionPointList.SingleChoiceListner {

    TextView currentCP;
    String url = "https://10.0.2.2:44312/CollectionPoint"; //set up the API url you want to call
    String responseString; // result string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_points);

        currentCP = findViewById(R.id.current_cp_description);

        Button updateCPBtn = findViewById(R.id.updateBtn);
        updateCPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment collectionPointList = new CollectionPointList();
                collectionPointList.setCancelable(false);
                collectionPointList.show(getSupportFragmentManager(), "Collection Points List");
            }
        });

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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        currentCP.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
    }
}
