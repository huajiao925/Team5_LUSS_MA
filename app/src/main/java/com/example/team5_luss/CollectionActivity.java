package com.example.team5_luss;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Model.CollectionPoint;

public class CollectionActivity extends AppCompatActivity implements CollectionPointList.SingleChoiceListner {

    TextView current_cp;
    String url = "https://10.0.2.2:44312/CollectionPoint"; //set up the API url you want to call
    String responseString; // result string
    CollectionPoint collectionPoint = new CollectionPoint();
    int deptID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_points);

        current_cp = findViewById(R.id.current_cp);

        Button updateCPBtn = findViewById(R.id.updateBtn);
        updateCPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment collectionPointList = new CollectionPointList();
                collectionPointList.setCancelable(false);
                collectionPointList.show(getSupportFragmentManager(), "Collection Points List");
            }
        });
        //Shared Preferences:
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        deptID = pref.getInt("deptID",0);

        //get all the collection Time
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + "/collectiontimes/" + deptID;
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
                    final String[] collectionTimes = gson.fromJson(inline, String[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final ListView lv = (ListView) findViewById(R.id.lv);
                            final List<String> collectionTimesList = new ArrayList<String>(Arrays.asList(collectionTimes));
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (CollectionActivity.this, android.R.layout.simple_list_item_1, collectionTimesList);
                            lv.setAdapter(arrayAdapter);

                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        //pass the time to next Activity
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


        //get dept's collection point
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + "/" + deptID;
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
                    collectionPoint = gson.fromJson(inline,CollectionPoint.class);
                    current_cp.setText(collectionPoint.Location + " " + collectionPoint.Description);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        current_cp.setText(list[position]);

        //save the new cpID to Department
        final int cpID = (position + 1);

        //update dept cp
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + "/" + deptID + "/" + cpID;
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onNegativeButtonClicked() {
    }

    @Override
    protected void onStop() {
        super.onStop();
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        finish();
    }
}
