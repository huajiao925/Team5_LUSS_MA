package com.example.team5_luss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

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

    String API_URL = "https://10.0.2.2:44312/request/GetRequestMBByStatusByDept/0/";
    String responseString; // result string
    Request[] RequestList = new Request[]{};
    private String webServiceMessage = "Fail";
    RequestAdapter adapter;
    RecyclerView recyclerView;
    List<Request> requestArrList = new ArrayList<Request>();
    Context context;
    int depID;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.request_list);
        recyclerView = findViewById(R.id.rvRequests);
        SharedPreferences sharedPreferences= getSharedPreferences("user_credentials",MODE_PRIVATE);
        depID=sharedPreferences.getInt("deptID",0);

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
                URL url = new URL(API_URL+depID);
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
                    RelativeLayout DataLayout=findViewById(R.id.list_data);
                    DataLayout.setVisibility(View.VISIBLE);
                    RelativeLayout noDataLayout=findViewById(R.id.no_data);
                    noDataLayout.setVisibility(View.GONE);
                    setUpRecyclerView();
                }
                else
                {
                    RelativeLayout noDataLayout=findViewById(R.id.no_data);
                    noDataLayout.setVisibility(View.VISIBLE);
                    RelativeLayout DataLayout=findViewById(R.id.list_data);
                    DataLayout.setVisibility(View.GONE);

                }
            }
        }

    }

    private void setUpRecyclerView() {
        RequestAdapter adapter = new RequestAdapter(this, (ArrayList<Request>) requestArrList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Shared Preferences:
        SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        role = pref.getString("role",null);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptRep_menu, false);
        menu.setGroupVisible(R.id.storeclerk_menu, false);
        menu.setGroupVisible(R.id.storeMng_menu, false);

        if(role.equals("dept_delegate")){
            menu.setGroupVisible(R.id.deptdlgt_menu, true);
            menu.setGroupVisible(R.id.deptMng_menu, false);
        }
        else if(role.equals("dept_head")){
            menu.setGroupVisible(R.id.deptdlgt_menu, false);
            menu.setGroupVisible(R.id.deptMng_menu, true);
        }
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
        if(item.getItemId() == R.id.dept_Mng_home || item.getItemId() == R.id.dept_dlgt_home) {
            Intent intent = new Intent(this,RequestListActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.dept_delegate) {
            Intent intent = new Intent(this,DelegateActivity.class);
            startActivity(intent);
        }
        return true;
    }

}