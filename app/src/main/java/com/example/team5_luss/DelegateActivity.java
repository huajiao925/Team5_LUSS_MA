package com.example.team5_luss;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import Model.DelegatedManager;
import Model.Request;

public class DelegateActivity extends AppCompatActivity {

    String API_URL = "https://10.0.2.2:44312/Delegate/";
    String responseString; // result string
    DelegatedManager delegatedManager=new DelegatedManager();
    private String webServiceMessage = "Fail";
    RequestAdapter adapter;
    RecyclerView recyclerView;
    List<Request> requestArrList = new ArrayList<Request>();
    Context context;
    int depID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.request_list);
        recyclerView = findViewById(R.id.rvRequests);
        SharedPreferences sharedPreferences= getSharedPreferences("user_credentials",MODE_PRIVATE);
        depID=sharedPreferences.getInt("deptID",0);
        new GetCurrentDelegateAsync().execute();
    }

    public class GetCurrentDelegateAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();
            depID=1;  //To Delete
            API_URL+="getCurrentDelegate/"+depID;
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
                delegatedManager = gson.fromJson(responseString, DelegatedManager.class);


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
             if(delegatedManager!=null)
             {
                 RelativeLayout activeView=findViewById(R.id.active_delegate);
                 activeView.setVisibility(View.VISIBLE);

                 TextView name=findViewById(R.id.dlg_name);
                 if(name!=null)
                 {
                     name.setText(delegatedManager.User.FirstName+" "+delegatedManager.User.LastName);
                 }
                 TextView fromDate=findViewById(R.id.dlg_fromDate);
                 if(fromDate!=null)
                 {
                     fromDate.setText(CodeSetting.convertDateString(delegatedManager.FromDate));
                 }
                 TextView ToDate=findViewById(R.id.dlg_toDate);
                 if(ToDate!=null)
                 {
                     ToDate.setText(CodeSetting.convertDateString(delegatedManager.ToDate));
                 }
             }
             else
             {

                 RelativeLayout activeView=findViewById(R.id.active_delegate);
                 activeView.setVisibility(View.VISIBLE);
                 RelativeLayout emptyView=findViewById(R.id.empty_delegate);
                 emptyView.setVisibility(View.GONE);
             }
            }
        }

    }



}