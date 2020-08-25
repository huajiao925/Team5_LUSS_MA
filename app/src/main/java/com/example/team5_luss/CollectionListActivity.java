package com.example.team5_luss;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.CollectionPoint;
import Model.RequestDetails;
import Model.ViewModel.CustomRetrieval;
import Model.item;

public class CollectionListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String API_URL = "https://10.0.2.2:44312/Request/GetItemByRetrievalBydept/1/3";
    CustomRetrieval[] itemList = new CustomRetrieval[]{};
    List<CustomRetrieval> items = new ArrayList<CustomRetrieval>();

    private String webServiceMessage = "Fail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_lists);
        recyclerView = findViewById(R.id.collectionListView);
        new GetRequestAsync().execute();
    }

    public class GetRequestAsync extends AsyncTask<Void, Void, String> {

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
}
