package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.ViewModel.CustomItem;

public class ItemDetails extends AppCompatActivity {

    int itemId;
    private int ON_ACTION_RETURN = 1;
    String url = "https://10.0.2.2:44312/ItemList/mobile/GetItemByID/";
    String responseString;
    String userRole;
    Button btnAdjust;
    TextView itemCode;
    TextView category;
    TextView description;
    TextView location;
    TextView uom;
    TextView inStockQty;
    TextView reorderLevel;
    TextView reorderQty;
    CustomItem item;
    String role;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        userRole = pref.getString("role","");

        final Intent intent = getIntent();
        itemId = intent.getIntExtra("itemId",0);
        loadItemDetails(itemId);

        if(userRole.equals("store_clerk")){
            btnAdjust = findViewById(R.id.adjustBtn);
            if(btnAdjust!=null){
                btnAdjust.setVisibility(View.VISIBLE);
                btnAdjust.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        createAdjustment(itemId);

                    }
                });
            }
        } else if(userRole.equals("store_manager") || userRole.equals("store_supervisor")){
            btnAdjust = findViewById(R.id.adjustBtn);
            if(btnAdjust!=null){
                btnAdjust.setVisibility(View.INVISIBLE);
            }
        }



        itemCode = findViewById(R.id.item_code);
        category = findViewById(R.id.catName);
        description = findViewById(R.id.item_name);
        location = findViewById(R.id.item_location);
        uom = findViewById(R.id.item_uom);
        inStockQty = findViewById(R.id.quantity);
        reorderLevel = findViewById(R.id.reorder_lvl);
        reorderQty = findViewById(R.id.reorder_qty);
    }

    protected void loadItemDetails(final int itemId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + itemId;
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
                    item = gson.fromJson(responseString, CustomItem.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            itemCode.setText(item.getItemCode());
                            category.setText(item.getCategoryName());
                            description.setText(item.getItemName());
                            location.setText(item.getLocation());
                            uom.setText(item.getUOM());
                            inStockQty.setText("" + item.getInStockQty());
                            reorderLevel.setText("" + item.getReOrderLevel());
                            reorderQty.setText("" + item.getReOrderQty());

                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void createAdjustment(int itemId){
        Intent intent = new Intent(ItemDetails.this, AdjustVoucherCreate.class );
        intent.putExtra("itemId", itemId);
        startActivityForResult(intent, ON_ACTION_RETURN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ON_ACTION_RETURN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(ItemDetails.this,"Adjustment Voucher has been submitted", Toast.LENGTH_LONG).show();
                loadItemDetails(itemId);
            }
        }
    }

    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Shared Preferences:
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        role = pref.getString("role",null);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptRep_menu, false);
        menu.setGroupVisible(R.id.deptMng_menu, false);
        menu.setGroupVisible(R.id.deptdlgt_menu, false);
        if(role.equals("store_manager") || role.equals("store_supervisor")){
            menu.setGroupVisible(R.id.storeMng_menu, true);
            menu.setGroupVisible(R.id.storeclerk_menu, false);
        }
        else if(role.equals("store_clerk")){
            menu.setGroupVisible(R.id.storeMng_menu, false);
            menu.setGroupVisible(R.id.storeclerk_menu, true);
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
