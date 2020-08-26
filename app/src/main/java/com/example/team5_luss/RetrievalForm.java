package com.example.team5_luss;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import Model.CustomAdjustmentVoucher;
import Model.Retrieval;
import Model.ViewModel.CustomRequestDetail;
import Model.ViewModel.CustomRetrieval;

public class RetrievalForm extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/Retrieval/mobile/byStatus/Approved"; //set up the API url you want to call
    String url_disburseByRetrieval = "https://10.0.2.2:44312/Retrieval/mobile/allocateQty";
    String responseString;
    String target;

    CustomRetrieval[] retrievals;
    ArrayList<Integer> retrievedQty = new ArrayList<Integer>();
    int retrievalID;
    int userID = 1;// inject user session

    ListView listView;
    EditText collectionTimeEdit;
    String collectionTime;
    Button btnConfirm;
    Button adjustBtn;

    public static int ADJUST_STOCK = 1;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieval_form);

        listView = findViewById(R.id.listViewRetrievedItem);
        collectionTimeEdit = findViewById(R.id.collectionTime_retrieval);

        loadRetrievalList();
        setDatePicker();
        onClickConfirmBtn();

    }

    public void onClickConfirmBtn(){
        btnConfirm = findViewById(R.id.confirmBtn_retrieval);
        if(btnConfirm != null) {
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getRetrievedQty(RetrievalAdapter.retrievals);
                    collectionTime = collectionTimeEdit.getText().toString();
                    confirmDisbursementByRetrieval(retrievedQty, retrievalID, collectionTime, userID);
                }
            });
        }
    }

    //TODO: LINK ADJUST BUTTON TO ADJUST VOUCHER
    /*public void onClickAdjustBtn(){
        adjustBtn = findViewById(R.id.adjustBtn);
        Intent intent = new Intent(this, AdjustVoucherCreate.class);
        startActivityForResult(intent, ADJUST_STOCK);
    }*/

    public void getRetrievedQty(CustomRetrieval[] customRetrievals){
        for(int i = 0; i< customRetrievals.length; i++){
            retrievedQty.add(customRetrievals[i].getRequestedQty());
        }
    }

    public void setDatePicker(){
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
                new DatePickerDialog(RetrievalForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //end of date picker
    }

    private void updateLabel(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        collectionTimeEdit.setText(sdf.format(myCalendar.getTime()));
    }

    public void loadRetrievalList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    target = url;
                    trustManager.trustAllCertificates();
                    URL url = new URL(target);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");
                    conn.connect();
                    final InputStream in = conn.getInputStream();
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
                    retrievals = gson.fromJson(responseString, CustomRetrieval[].class);

                    if(retrievals!=null){
                        retrievalID = retrievals[0].getRetrievalID();
                        System.out.println("Reached Here");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(retrievals != null){
                                RetrievalAdapter adapter = new RetrievalAdapter(RetrievalForm.this,R.layout.retrieved_items,retrievals);
                                listView.setAdapter(adapter);
                            }else{
                                Toast.makeText(RetrievalForm.this, "There is not requests to be disbursed", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void confirmDisbursementByRetrieval(final ArrayList<Integer> retrievedQty, final int retrievalId, final String collectionDate, final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url_disburseByRetrieval +"/"+ retrievedQty + "/" + retrievalId + "/" + collectionDate + "/" + id ;
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
