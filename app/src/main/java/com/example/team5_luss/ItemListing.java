package com.example.team5_luss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import Model.CustomAdjustmentVoucher;
import Model.Item;
import Model.ViewModel.CustomItem;

public class ItemListing extends AppCompatActivity {

    String url = "https://10.0.2.2:44312/ItemList/mobile";//set up the API url you want to call
    String responseString; // result string
    CustomItem[] items;

    List<CustomItem> inventory = new ArrayList<CustomItem>();
    SearchView searchView;
    ListView listView;
    String role;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        searchView = (SearchView) findViewById(R.id.searchView);

        loadItemList();
    }


    public void setSearchView(final ItemListAdapter adapter){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                /*if(inventory.contains(query.toLowerCase())){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(ItemListing.this, "No Match found", Toast.LENGTH_LONG).show();
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toLowerCase());
                return true;
            }
        });
    }




    private void  loadItemList(){
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
                    int data=bufferedInputStream.read();
                    while (data!=-1){
                        char current = (char) data;
                        response.append(current);
                        data=bufferedInputStream.read();
                    }
                    responseString = response.toString();
                    Gson gson = new Gson();
                    items = gson.fromJson(responseString, CustomItem[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //List<CustomItem> i = CodeSetting.convertArrayToList(items);
                            Collections.addAll(inventory,items);
                            ItemListAdapter adapter = new ItemListAdapter(ItemListing.this,R.layout.item_list,inventory);
                            listView = findViewById(R.id.inventoryListing);
                            listView.setAdapter(adapter);
                            listView.setTextFilterEnabled(true);
                            setSearchView(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    System.out.println(items[i].getItemID());
                                    goToDetailsPage(items[i].getItemID());
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

    public void goToDetailsPage(int itemID){
        Intent intent = new Intent(ItemListing.this,ItemDetails.class);
        intent.putExtra("itemId",itemID);
        startActivity(intent);
    }

    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Shared Preferences:
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        role = pref.getString("role",null);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptMng_menu, false);
        menu.setGroupVisible(R.id.deptRep_menu, false);
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
        if(item.getItemId() == R.id.store_Mng_home) {
            Intent intent = new Intent(this,AdjustVoucherListing.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.delivery) {
            Intent intent = new Intent(this,DeliveryListActivity.class);
            startActivity(intent);
        }
        return true;
    }


    /*private void setSearchView(){
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(s);
        }
        return true;
    }*/
}
