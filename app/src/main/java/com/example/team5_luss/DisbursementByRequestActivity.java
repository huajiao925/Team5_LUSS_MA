package com.example.team5_luss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.ViewModel.CustomRequest;
import Model.ViewModel.CustomRequestDetail;

public class DisbursementByRequestActivity extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/RequestDetails/get-by-request-mobile/"; //set up the API url you want to call
    String url_disburseByRequest = "https://10.0.2.2:44312/Request/disburse-by-request-mobile/";
    String responseString; // result string
    ListView listView;
    TextView deptNameText;
    TextView deptRepText;
    TextView collectionPointText;
    EditText collectionTimeEdit;
    Button confirmBtn;
    //pass back to api
    int requestID;
    int userID;
    String collectionTime;
    ArrayList<Integer> fulfillQty = new ArrayList<Integer> ();

    CustomRequestDetail[] requestItems;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        userID = pref.getInt("userID", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_by_request);
        Intent intent = getIntent();
        requestID = intent.getIntExtra("requestID", 0);
        String deptName = intent.getStringExtra("deptName");
        String deptRep = intent.getStringExtra("deptRep");
        String collectionPoint = intent.getStringExtra("collectionPoint");

        deptNameText = findViewById(R.id.deptName);
        deptNameText.setText(deptName);
        deptRepText = findViewById(R.id.deptRep);
        deptRepText.setText(deptRep);
        collectionPointText = findViewById(R.id.collectionPoint);
        collectionPointText.setText(collectionPoint);
        collectionTimeEdit = findViewById(R.id.collectionTime);
        confirmBtn = findViewById(R.id.confirm_btn);

        listView = findViewById(R.id.listViewReqItems);
        loadRequestItems(requestID);
        setDatePicker();
        onClickConfirmBtn();

    }

    private void onClickConfirmBtn(){
        if(confirmBtn != null){
            confirmBtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    getFulfillQty(DisbursementByRequestItemAdapter.requestDetails);
                    collectionTime = collectionTimeEdit.getText().toString();
                    if(collectionTime.length() == 0){
                        CodeSetting.warningToastMsg(DisbursementByRequestActivity.this,"Collection Date must be specified");
                    }
                    else if(!CodeSetting.isQtyNull(fulfillQty)){
                        CodeSetting.warningToastMsg(DisbursementByRequestActivity.this,"Fulfill quantity must be specified");
                    }
                    else confirmDisbursement(requestID,userID,collectionTime,fulfillQty);
                }
            });
        }
    }

    private void setDatePicker(){
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        collectionTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog endDate = new DatePickerDialog(DisbursementByRequestActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                endDate.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                endDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                endDate.show();
            }
        });
    }

    private void updateLabel(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        collectionTimeEdit.setText(sdf.format(myCalendar.getTime()));
    }


    public void getFulfillQty(CustomRequestDetail[] requestDetails){
        for(int i = 0; i< requestDetails.length; i++){
            fulfillQty.add(requestDetails[i].getFulfillQty());
        }
    }

    public void loadRequestItems(final int id) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String target = url + id;
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
                        requestItems = gson.fromJson(responseString, CustomRequestDetail[].class);
                        System.out.println("Reached here");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DisbursementByRequestItemAdapter adapter = new DisbursementByRequestItemAdapter(getApplicationContext(), R.layout.disb_by_req_item, requestItems);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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

    //private void confirmDisbursement(final int id, final int userID, final String CollectionTime, final JSONArray fulfillQty){
    private void confirmDisbursement(final int id, final int userID, final String CollectionTime, final ArrayList<Integer> fulfillQty){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url_disburseByRequest + id + "/" + userID + "/" + CollectionTime + "/" + fulfillQty ;
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
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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