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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Model.CollectionPoint;
import Model.RequestDetails;
import Model.ViewModel.CustomRetrieval;

public class CollectionListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String API_URL = "https://10.0.2.2:44312/Request/GetItemByRetrievalBydept";
    String API_URL_POST = "https://10.0.2.2:44312/Request";
    CustomRetrieval[] itemList = new CustomRetrieval[]{};
    List<CustomRetrieval> items = new ArrayList<CustomRetrieval>();
    ArrayList<Integer> acceptedQty = new ArrayList<Integer> ();
    private String webServiceMessage = "Fail";
    int deptID;
    int retrievalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_lists);
        recyclerView = findViewById(R.id.collectionListView);
        //Shared Preferences:
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        deptID = pref.getInt("deptID", 0);
        Intent intent = getIntent();
        retrievalID =intent.getIntExtra("retrievalID",0);

        new GetRequestAsync().execute();
        Button confirmBtn = findViewById(R.id.confirm_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the list of acceptQty
                getAcceptedQty(CollectionAdapter.itemList);
                new GetAcceptQtyAsync().execute();
                Intent intent = new Intent(CollectionListActivity.this,CollectionPointActivity.class);
                startActivity(intent);
                recreate();
                finish();
            }
        });

    }

    private void getAcceptedQty(ArrayList<CustomRetrieval> itemList) {
        for(int i = 0; i< itemList.size(); i++){
            acceptedQty.add(itemList.get(i).getAcceptedQty());
        }
    }

    public class GetRequestAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String target = API_URL + "/" + retrievalID + "/" + deptID;
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
                itemList = gson.fromJson(inline,CustomRetrieval[].class);
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
            CollectionAdapter adapter = new CollectionAdapter(CollectionListActivity.this, (ArrayList<CustomRetrieval>) items);
            recyclerView.setLayoutManager(new LinearLayoutManager(CollectionListActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }

    public class GetAcceptQtyAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if(acceptedQty != null){

                String target = API_URL_POST + "/Mobile_GetAccptQty/" + acceptedQty + "/" + retrievalID;
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptRep_menu, true);
        menu.setGroupVisible(R.id.storeclerk_menu, false);
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
        if(item.getItemId() == R.id.dept_rep_home) {
            Intent intent = new Intent(this,CollectionPointActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
