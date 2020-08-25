package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.CustomAdjustmentVoucher;
import Model.ViewModel.CustomItem;

public class ItemListing extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/ItemList/mobile";//set up the API url you want to call
    String responseString; // result string
    CustomItem[] items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        loadItemList();
    }

    private void  loadItemList(){
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
                    items = gson.fromJson(responseString, CustomItem[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ItemListAdapter adapter = new ItemListAdapter(ItemListing.this,R.layout.item_list,items);
                            ListView listView = findViewById(R.id.inventoryListing);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    System.out.println(items[i].getItemID());
                                    goToDetailsPage(items[i].getItemID());
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
    }

    public void goToDetailsPage(int itemID){
        Intent intent = new Intent(ItemListing.this,ItemDetails.class);
        intent.putExtra("itemId",itemID);
        startActivity(intent);
    }

}
