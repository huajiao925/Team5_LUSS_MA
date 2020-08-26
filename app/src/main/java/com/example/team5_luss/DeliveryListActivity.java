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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Request;
import Model.ViewModel.CustomRequest;
import Model.ViewModel.CustomRetrieval;

public class DeliveryListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private String webServiceMessage = "Fail";
    String API_URL = "https://10.0.2.2:44312/Request/get-request-by-status-mobile/5";
    CustomRequest[] itemList = new CustomRequest[]{};
    List<CustomRequest> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_list);
        recyclerView = findViewById(R.id.rvList);
        new GetRetrievalListAsync().execute();
    }

    private class GetRetrievalListAsync extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String target = API_URL;
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
                itemList = gson.fromJson(inline,CustomRequest[].class);
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
            DeliveryListAdapter adapter = new DeliveryListAdapter(DeliveryListActivity.this, (ArrayList<CustomRequest>) items);
            recyclerView.setLayoutManager(new LinearLayoutManager(DeliveryListActivity.this));
            recyclerView.setAdapter(adapter);
        }
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
