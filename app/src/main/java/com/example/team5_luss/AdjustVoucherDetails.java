package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import Model.CustomAdjustmentVoucher;

public class AdjustVoucherDetails extends AppCompatActivity {
    int adjustmentId;
    int userID ; // tobe updated with sessionid
    String url = "https://10.0.2.2:44312/AdjustmentList/mobile/"; //set up the API url you want to call
    String url_approval = "https://10.0.2.2:44312/AdjustmentList/ApprovedAdjustmentVoucher/";
    String responseString; // result string
    Button btnApprove;
    Button btnReject;
    EditText txtComment;
    TextView txtDateIssue;
    TextView txtVoucherNo;
    TextView txtItemCode;
    TextView txtCategory;
    TextView txtDescription;
    TextView txtUOM;
    TextView txtQuantity;
    TextView txtTotalValue;
    TextView txtReason;
    CustomAdjustmentVoucher voucher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_voucher_details);

        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        userID = pref.getInt("userID",0);

        Intent intent = getIntent();
        adjustmentId =intent.getIntExtra("adjustmentId",0);

        loadAdjustmentDetails(adjustmentId);

        btnReject = findViewById(R.id.reject);
        if(btnReject!=null){
            btnReject.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    reject(adjustmentId, txtComment.getText().toString());
                }
            });
        }
        btnApprove = findViewById(R.id.approve);
        if(btnApprove!=null){
            btnApprove.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    approve(adjustmentId, txtComment.getText().toString());
                }
            });
        }

        txtDateIssue = findViewById(R.id.dateIssue);
        txtVoucherNo = findViewById(R.id.voucherNo);
        txtItemCode = findViewById(R.id.itemCode);
        txtCategory = findViewById(R.id.category);
        txtDescription = findViewById(R.id.description);
        txtUOM = findViewById(R.id.UOM);
        txtQuantity = findViewById(R.id.quantity);
        txtTotalValue = findViewById(R.id.totalValue);
        txtReason = findViewById(R.id.reason);
        txtComment = findViewById(R.id.comment);
    }

    public void loadAdjustmentDetails(final int adjustmentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + adjustmentId;
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
                    Gson gson = new Gson();
                    voucher = gson.fromJson(responseString, CustomAdjustmentVoucher.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtDateIssue.setText(CodeSetting.convertDateString(voucher.getIssuedDate()));
                            txtItemCode.setText(voucher.getItemCode());
                            txtVoucherNo.setText(voucher.getVoucherNo());
                            txtCategory.setText(voucher.getCategoryName());
                            txtDescription.setText(voucher.getItemName());
                            txtUOM.setText(voucher.getUOM());
                            txtQuantity.setText("" + voucher.getAdjustQty());
                            txtTotalValue.setText("" + voucher.getTotalCost());
                            txtReason.setText(voucher.getReason());

                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void reject(final int adjustmentId, final String txtComment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url_approval + adjustmentId + "/" +txtComment + "/" + userID + "/Rejected";
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

                    //Toast.makeText(AdjustVoucherDetails.this, "Adjustment has been rejected", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("authorise","rejected");
                    setResult(RESULT_OK,intent);
                    finish();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void approve(final int adjustmentId, final String txtComment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url_approval + adjustmentId + "/" +txtComment + "/" + userID + "/Approved";
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

                    //Toast.makeText(AdjustVoucherDetails.this, "Adjustment has been approved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("authorise","approved");
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
        menu.setGroupVisible(R.id.storeclerk_menu, false);
        menu.setGroupVisible(R.id.deptMng_menu, false);
        menu.setGroupVisible(R.id.storeMng_menu, true);
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
        if(item.getItemId() == R.id.store_Mng_item) {
            Intent intent = new Intent(this,ItemListing.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.store_Mng_home) {
            Intent intent = new Intent(this,AdjustVoucherListing.class);
            startActivity(intent);
        }
        return true;
    }

}
