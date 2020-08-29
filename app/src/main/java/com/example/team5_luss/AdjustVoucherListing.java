package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.CollectionPoint;
import Model.CustomAdjustmentVoucher;

public class AdjustVoucherListing extends AppCompatActivity {

    String url_down = "https://10.0.2.2:44312/AdjustmentList/mobile/pendingDown";
    String url_up = "https://10.0.2.2:44312/AdjustmentList/mobile/pendingUp"; //set up the API url you want to call
    String responseString; // result string
    CustomAdjustmentVoucher[] vouchers;// listing of vouchers
    private int ON_ACTION_RETURN = 1;
    String userRole;
    String target;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_voucher_list);

        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        userRole = pref.getString("role","");

        loadAdjustmentList();
    }

    private void loadAdjustmentList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(userRole.equals("store_supervisor")){
                         target = url_down;
                    } else if(userRole.equals("store_manager")){
                        target = url_up;
                    }

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
                    vouchers = gson.fromJson(responseString,CustomAdjustmentVoucher[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AdjustVoucherAdapter adapter = new AdjustVoucherAdapter(AdjustVoucherListing.this,R.layout.adjust_voucher_list,vouchers);
                            ListView listView = findViewById(R.id.adjustBoardListing);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    System.out.println(vouchers[i].getAdjustmentID());
                                    goToDetailsPage(vouchers[i].getAdjustmentID());
                                }
                            });
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void goToDetailsPage(int id){
        Intent intent = new Intent(AdjustVoucherListing.this,AdjustVoucherDetails.class);
        intent.putExtra("adjustmentId",id);
        startActivityForResult(intent,ON_ACTION_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ON_ACTION_RETURN) {
            if (resultCode == RESULT_OK) {
                if(data.getStringExtra("authorise").equals("rejected")){
                    Toast.makeText(AdjustVoucherListing.this, "Adjustment has been rejected", Toast.LENGTH_LONG).show();
                }else if(data.getStringExtra("authorise").equals("approved")){
                    Toast.makeText(AdjustVoucherListing.this, "Adjustment has been approved", Toast.LENGTH_LONG).show();
                }

                loadAdjustmentList();
            }
        }
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
            menu.setGroupVisible(R.id.deptdlgt_menu, false);
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
