package com.example.team5_luss;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import Model.CollectionPoint;
import Model.Request;

public class RequestListActivity extends AppCompatActivity {



    String API_URL = "https://10.0.2.2:44312/request/GetRequestMBByStatusByDept/0/1";
    String responseString; // result string
    Request[] RequestList = new Request[]{};
    private String webServiceMessage = "Fail";
    RequestAdapter adapter;
    RecyclerView recyclerView;
    List<Request> requestArrList = new ArrayList<Request>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.request_list);
        recyclerView = findViewById(R.id.rvRequests);
        new GetRequestAsync().execute();
    }

    public class GetRequestAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();

            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
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
                RequestList = gson.fromJson(responseString, Request[].class);


            } catch (IOException ex) {
                ex.printStackTrace();
            }
           webServiceMessage = "Success";
            return webServiceMessage;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (webServiceMessage.equals("Success")) {
                requestArrList.clear();
                requestArrList=CodeSetting.convertArrayToList(RequestList);

                if (requestArrList.size() != 0) {
                    setUpRecyclerView();
                }
            }
        }

    }

    private void setUpRecyclerView() {
        RequestAdapter adapter = new RequestAdapter(this, (ArrayList<Request>) requestArrList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}