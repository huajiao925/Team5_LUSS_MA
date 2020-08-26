package com.example.team5_luss;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.ViewModel.CustomRequest;

public class DisbursementActivity extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/Request/get-request-by-status-mobile/1"; //set up the API url you want to call
    String responseString; // result string
    CustomRequest[] approvedRequests;

    ListView listView;
    Button retrievalBtn;

    public static int DISBURSED_BY_REQUEST = 1;
    public static int DISBURSED_BY_RETRIEVAL =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        loadApprovedRequestList();
        onClickRetrievalBtn();

    }

    public void onClickRetrievalBtn() {
        retrievalBtn = findViewById(R.id.retrievalBtn);
        if (retrievalBtn != null) {
            retrievalBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(DisbursementActivity.this, RetrievalForm.class);
                    startActivityForResult(intent, DISBURSED_BY_RETRIEVAL);
                }
            });
        }
    }

    public void loadApprovedRequestList() {
        try {
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
                        int data = bufferedInputStream.read();
                        while (data != -1) {
                            char current = (char) data;
                            response.append(current);
                            data = bufferedInputStream.read();
                        }
                        responseString = response.toString();
                        Gson gson = new Gson();
                        approvedRequests = gson.fromJson(responseString, CustomRequest[].class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView = findViewById(R.id.listViewDisbursement);
                                DisbursementListAdapter adapter = new DisbursementListAdapter(getApplicationContext(), R.layout.disb_req_item, approvedRequests);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                        Intent intent = new Intent(DisbursementActivity.this, DisbursementByRequestActivity.class);
                                        intent.putExtra("requestID", approvedRequests[position].getRequestID());
                                        intent.putExtra("deptName", approvedRequests[position].getDepartmentName());
                                        intent.putExtra("deptRep", approvedRequests[position].getDepartmentRep());
                                        intent.putExtra("collectionPoint", approvedRequests[position].getCollectionPoint());
                                        //startActivity(intent);
                                        startActivityForResult(intent, DISBURSED_BY_REQUEST);
                                    }
                                });
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DISBURSED_BY_REQUEST || requestCode == DISBURSED_BY_RETRIEVAL) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(DisbursementActivity.this, "Request has been disbursed", Toast.LENGTH_LONG).show();
                loadApprovedRequestList();
            }
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
        return true;
    }



}