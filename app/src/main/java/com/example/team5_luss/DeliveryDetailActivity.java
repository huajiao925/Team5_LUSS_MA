package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.ViewModel.CustomRequest;
import Model.ViewModel.CustomRequestDetail;
import Model.ViewModel.CustomRetrieval;

public class DeliveryDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private String webServiceMessage = "Fail";
    String API_URL = "https://10.0.2.2:44312/RequestDetails";
    String API_URL_ACTION = "https://10.0.2.2:44312/Request";
    CustomRequestDetail[] itemList = new CustomRequestDetail[]{};
    List<CustomRequestDetail> items = new ArrayList<>();
    int userID;
    int requestID;
    Button notTally;
    Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_details);
        recyclerView = findViewById(R.id.rvRequests);
        //Shared Preferences:
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        userID = pref.getInt("userID", 0);
        Intent intent = getIntent();
        requestID = intent.getIntExtra("requestID",0);
        new GetRequestDetailsAsync().execute();

        notTally = findViewById(R.id.notTally);
        complete = findViewById(R.id.complete);
        notTally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DenyDelivery().execute();
               /* if(webServiceMessage == "Success"){
                    Intent intent1 = new Intent(DeliveryDetailActivity.this, DeliveryListActivity.class);
                    startActivity(intent1);
                    finish();
                }*/
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CompleteDelivery().execute();
                Intent intent1 = new Intent(DeliveryDetailActivity.this, DeliveryListActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    private class GetRequestDetailsAsync extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String target = API_URL + "/get-by-request-mobile/" + requestID;
                trustManager.trustAllCertificates();
                URL url = new URL(target);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.connect();
                int responsecode = conn.getResponseCode();
                String inline = "";
                if (responsecode != 200) {
                    throw new RuntimeException(String.valueOf(responsecode));
                } else {
                    Scanner sc = new Scanner(url.openStream());
                    while (sc.hasNext()) {
                        inline += sc.nextLine();
                    }
                }
                Gson gson = new Gson();
                itemList = gson.fromJson(inline,CustomRequestDetail[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (webServiceMessage.equals("Success")) {
                items.clear();
                items = CodeSetting.convertArrayToList(itemList);
                if (items.size() != 0) {
                    setUpRecyclerView();
                }
            }
        }

        private void setUpRecyclerView() {
            DeliveryDetailAdapter adapter = new DeliveryDetailAdapter(DeliveryDetailActivity.this, (ArrayList<CustomRequestDetail>) items);
            recyclerView.setLayoutManager(new LinearLayoutManager(DeliveryDetailActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }

    private class DenyDelivery extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String target = API_URL_ACTION + "/deny/" + requestID;
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
                String x = response.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(webServiceMessage == "Success"){
                Intent intent1 = new Intent(DeliveryDetailActivity.this, DeliveryListActivity.class);
                startActivity(intent1);
                finish();
            }

        }
    }

    private class CompleteDelivery extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String target = API_URL_ACTION + "/complete/" + requestID + "/" + userID;
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
                String x = response.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(DeliveryDetailActivity.this, DeliveryListActivity.class);
        startActivity(intent1);
        finish();
    }

    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptRep_menu, false);
        menu.setGroupVisible(R.id.storeclerk_menu, true);
        menu.setGroupVisible(R.id.deptMng_menu, false);
        menu.setGroupVisible(R.id.storeMng_menu, false);
        return true;
    }

    //MENU: handle selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.logout) {
            final SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            finish();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.store_item) {
            Intent intent = new Intent(this,ItemListing.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.store_home) {
            Intent intent = new Intent(this,DisbursementActivity.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.delivery) {
            Intent intent = new Intent(this,DeliveryListActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
