package com.example.team5_luss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText fulfillQtyEdit;
    Button confirmBtn;
    //pass back to api
    int requestID;
    int userID = 1;// inject user session
    //Date collectionTime;
    String collectionTime;
    ArrayList<Integer> fulfillQty = new ArrayList<Integer> ();
    //JSONArray fulfillQtyJson;
    //String collectionTimeJson;
    CustomRequestDetail[] requestItems;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
//        userID = pref.getInt("userID", 0);
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

        listView = findViewById(R.id.listViewReqItems);
        loadRequestItems(requestID);

        setDatePicker();

        confirmBtn = findViewById(R.id.confirm_btn);
        if(confirmBtn != null){
            confirmBtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    getFulfillQty(DisbursementByRequestItemAdapter.requestDetails);
                    //fulfillQtyJson = new JSONArray(fulfillQty);
                    collectionTime = collectionTimeEdit.getText().toString();
                   /* DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        collectionTime = formatter.parse(collectionTimeEdit.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    //convertDateToJsonString();
                    //confirmDisbursement(requestID,userID, collectionTimeJson, fulfillQtyJson);
                    confirmDisbursement(requestID,userID,collectionTime,fulfillQty);
                }
            });
        }
    }

//    public void convertDateToJsonString(){
//        DateFormat dateFormat=new SimpleDateFormat();
//        collectionTimeJson = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(collectionTime);
//    }


    private void setDatePicker(){
        //date picker
        //final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //view.setMinDate(System.currentTimeMillis()-1000);

                updateLabel();
            }
        };
        collectionTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog endDate = new DatePickerDialog(DisbursementByRequestActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                endDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                endDate.show();


            }
        });
        //end of date picker
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
}