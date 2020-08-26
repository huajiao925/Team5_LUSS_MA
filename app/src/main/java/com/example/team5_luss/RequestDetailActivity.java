package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import Model.Request;
import Model.RequestDetails;

public class RequestDetailActivity extends AppCompatActivity implements View.OnClickListener {
    String API_URL ="";
    String responseString; // result string
    RequestDetails[] RequestDetail = new RequestDetails[]{};
    private String webServiceMessage = "Fail";
    RequestAdapter adapter;
    RecyclerView recyclerView;
    List<RequestDetails> requestDetailItemArrList = new ArrayList<RequestDetails>();

    TextView reqIDDetail;
    TextView reqDateDetail;
    TextView reqByDetail;
    TextView reqComment;
    int requestID;
    String comment="";
    List<RequestDetails> requestDetailList = new ArrayList<RequestDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_detail);
        recyclerView = findViewById(R.id.rvRequestItems);

        reqIDDetail = findViewById(R.id.detail_reqID);
        requestID=getIntent().getIntExtra("requestID",0);
        reqIDDetail.setText(String.valueOf(getIntent().getIntExtra("requestID",0)));

        reqDateDetail = findViewById(R.id.detail_reqDate);
        reqDateDetail.setText(getIntent().getStringExtra("requestDate"));

        reqByDetail = findViewById(R.id.detail_reqBy);
        reqByDetail.setText(getIntent().getStringExtra("requestBy"));

        String jsonString=getIntent().getStringExtra("JRequestDetailList");
        Gson gson = new Gson();
        RequestDetails[] details= gson.fromJson(jsonString, RequestDetails[].class);
        requestDetailList=CodeSetting.convertArrayToList(details);
        setUpRecyclerView();

        Button btn_accept=findViewById(R.id.btnAccept);
        btn_accept.setOnClickListener(this);

        Button btn_reject=findViewById(R.id.btnReject);
        btn_reject.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        reqComment=findViewById(R.id.editTextComment);
        comment=reqComment.getText().toString();
        switch (view.getId()) {
            case R.id.btnAccept:
                API_URL="https://10.0.2.2:44312/request/ApproveRequestByDepHeadMB/"+requestID+"/1/"+ comment;
                new GetRequestDetailAsync().execute();
                break;
            case R.id.btnReject:
                API_URL="https://10.0.2.2:44312/request/ApproveRequestByDepHeadMB/"+requestID+"/0/"+ comment;
                new GetRequestDetailAsync().execute();
                break;
            default:break;
        }
    }

    public class GetRequestDetailAsync extends AsyncTask<Void, Void, String> {

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


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ShowMessage(webServiceMessage);
            onBackPressed();
        }

    }



    private void ShowMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        if(msg.equals("Success"))
        {
            Button btnAccept=(Button)findViewById(R.id.btnAccept);
            Button btnReject=(Button)findViewById(R.id.btnReject);
            if(btnAccept!=null)
            {
                btnAccept.setVisibility(View.GONE);
            }
            if(btnReject!=null)
            {
                btnReject.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RequestListActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUpRecyclerView() {
        RequestItemAdapter adapter = new RequestItemAdapter(this, (ArrayList<RequestDetails>) requestDetailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptRep_menu, false);
        menu.setGroupVisible(R.id.storeclerk_menu, false);
        menu.setGroupVisible(R.id.deptMng_menu, true);
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
        if(item.getItemId() == R.id.dept_Mng_home) {
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