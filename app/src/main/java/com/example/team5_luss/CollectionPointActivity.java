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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Model.CollectionPoint;
import Model.Request;

public class CollectionPointActivity extends AppCompatActivity implements CollectionPointList.SingleChoiceListner {

    TextView current_cp;
    String url = "https://10.0.2.2:44312/CollectionPoint"; //set up the API url you want to call
    CollectionPoint collectionPoint = new CollectionPoint();
    CollectionPoint[] collectionPointList = new CollectionPoint[]{};
    ArrayList<String> collectionNames = new ArrayList<>();
    String[] cp_list = new String[collectionNames.size()];
    Request[] collectionTimes = new Request[]{};
    int deptID;
    private String webServiceMessage = "Fail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_points);

        current_cp = findViewById(R.id.current_cp);

        Button updateCPBtn = findViewById(R.id.updateBtn);
        updateCPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment collectionPointList = new CollectionPointList();
                Bundle bundle = new Bundle();
                bundle.putStringArray("cp_list", cp_list);
                collectionPointList.setArguments(bundle);
                collectionPointList.setCancelable(false);
                collectionPointList.show(getSupportFragmentManager(), "Collection Points List");
            }
        });
        //Shared Preferences:
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        deptID = pref.getInt("deptID",0);

        new GetCollectionPoint().execute();
        new GetCollectionPointList().execute();

        //get all the collection Time
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + "/collectiontimes/" + deptID;
                    trustManager.trustAllCertificates();
                    URL url = new URL(target);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");
                    conn.connect();
                    int responsecode = conn.getResponseCode();
                    String inline = "";
                    if(responsecode !=200){
                        throw new RuntimeException(String.valueOf(responsecode));
                    }else{
                        Scanner sc = new Scanner(url.openStream());
                        while (sc.hasNext()){
                            inline += sc.nextLine();
                        }
                    }
                    Gson gson = new Gson();
                    collectionTimes= gson.fromJson(inline, Request[].class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final ListView lv = (ListView) findViewById(R.id.lv);
                            List<String> collectionTimesList = new ArrayList<String>();
                            for(int i=0; i<collectionTimes.length; i++){
                                String date = collectionTimes[i].CollectionTime.substring(0,10);
                                collectionTimesList.add(date);
                            }
                            final List<String> f_collectionTimesList = collectionTimesList.stream().distinct().collect(Collectors.<String>toList());

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (CollectionPointActivity.this, android.R.layout.simple_list_item_1, f_collectionTimesList);
                            lv.setAdapter(arrayAdapter);

                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        //pass the time to next Activity
                                        Intent intent = new Intent(CollectionPointActivity.this,CollectionListActivity.class);
                                        intent.putExtra("retrievalID",collectionTimes[i].getRetrievalID());
                                        startActivity(intent);
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

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        current_cp.setText(list[position]);

        //save the new cpID to Department
        final int cpID = (position + 1);

        //update dept cp
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + "/" + deptID + "/" + cpID;
                    trustManager.trustAllCertificates();
                    URL url = new URL(target);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    int responsecode = conn.getResponseCode();
                    String inline = "";
                    if(responsecode !=200){
                        throw new RuntimeException(String.valueOf(responsecode));
                    }else{
                        Scanner sc = new Scanner(url.openStream());
                        while (sc.hasNext()){
                            inline += sc.nextLine();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onNegativeButtonClicked() {
    }


    public class GetCollectionPoint extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();

            try {
                String target = url + "/" + deptID;
                trustManager.trustAllCertificates();
                URL url = new URL(target);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.connect();
                int responsecode = conn.getResponseCode();
                String inline = "";
                if(responsecode !=200){
                    throw new RuntimeException(String.valueOf(responsecode));
                }else{
                    Scanner sc = new Scanner(url.openStream());
                    while (sc.hasNext()){
                        inline += sc.nextLine();
                    }
                }
                Gson gson = new Gson();
                collectionPoint = gson.fromJson(inline,CollectionPoint.class);
                current_cp.setText(collectionPoint.Location + " " + collectionPoint.Description);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public class GetCollectionPointList extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();
            try {
                String target = url;
                trustManager.trustAllCertificates();
                URL url = new URL(target);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.connect();
                int responsecode = conn.getResponseCode();
                String inline = "";
                if (responsecode != 200) {
                    throw new RuntimeException(String.valueOf(responsecode));
                } else {
                    Scanner sc = new Scanner(url.openStream());
                    while (sc.hasNext()) {
                        inline += sc.nextLine();
                    }
                }
                Gson gson = new Gson();
                collectionPointList = gson.fromJson(inline, CollectionPoint[].class);
                for (CollectionPoint c : collectionPointList) {
                    collectionNames.add(c.Location + " " + c.Description);
                }



            } catch (IOException ex) {
                ex.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            cp_list = collectionNames.toArray(cp_list);

        }
    }

    //MENU: inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.deptRep_menu, true);
        menu.setGroupVisible(R.id.storeclerk_menu, false);
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
        if(item.getItemId() == R.id.dept_rep_home) {
            Intent intent = new Intent(this,CollectionPointActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
