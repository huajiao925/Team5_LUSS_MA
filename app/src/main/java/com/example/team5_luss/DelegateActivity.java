package com.example.team5_luss;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import Model.DelegatedManager;
import Model.Request;
import Model.User;

public class DelegateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    String API_URL = "https://10.0.2.2:44312/Delegate/";
    String responseString; // result string
    DelegatedManager delegatedManager=new DelegatedManager();
    private String webServiceMessage = "Fail";
    RequestAdapter adapter;
    RecyclerView recyclerView;
    List<Request> requestArrList = new ArrayList<Request>();
    Context context;
    int depID=0;

    private ArrayList<String> arrayList = new ArrayList<>();
    Hashtable<String, String> my_dict = new Hashtable<String, String>();
    private SearchableSpinner spinner;

    Button btnFromDatePicker, btnToDatePicker,btnCheckDelegate,btnConfirmDelegate,btnDeleteDelegate;
    EditText txtFromDate, txtToDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String selectID;int currentDelegateID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.delegate);
        recyclerView = findViewById(R.id.rvRequests);
        SharedPreferences sharedPreferences= getSharedPreferences("user_credentials",MODE_PRIVATE);
        depID=sharedPreferences.getInt("deptID",0);


        btnFromDatePicker=(Button)findViewById(R.id.btn_date);
        btnToDatePicker=(Button)findViewById(R.id.btn_end_date);
        btnCheckDelegate=(Button)findViewById(R.id.check_button);
        btnConfirmDelegate=(Button)findViewById(R.id.confirm_button);
        btnDeleteDelegate=(Button)findViewById(R.id.deleteDelegateBtn);

        txtFromDate=(EditText)findViewById(R.id.fromDate);
        txtToDate=(EditText)findViewById(R.id.End_Date);

        btnFromDatePicker.setOnClickListener(this);
        btnToDatePicker.setOnClickListener(this);
        btnCheckDelegate.setOnClickListener(this);
        btnConfirmDelegate.setOnClickListener(this);
        btnDeleteDelegate.setOnClickListener(this);
        new GetCurrentDelegateAsync().execute();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItemText = parent.getItemAtPosition(position).toString();
        selectID=my_dict.get(selectedItemText);
        Toast.makeText(context, " You select >> " + selectedItemText+" "+selectID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void AddDataToArrayList(List<User> allUsers) {
        arrayList.clear();
        my_dict.clear();
        for (User u : allUsers) {
            my_dict.put(u.FirstName + " " + u.LastName,String.valueOf(u.UserID));
            arrayList.add(u.FirstName + " " + u.LastName);
        }
    }
    private void findViews()
    {
        spinner = (SearchableSpinner) findViewById(R.id.spinner);
        //For set Title to Spinner
        spinner.setTitle("Select Use");
        setDataToAdapter(arrayList);
    }
    private void setDataToAdapter(ArrayList<String> arrayList)
    {
        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayList);
        // Specify layout to be used when list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id. deleteDelegateBtn:
                  new DeleteDelegateAsync().execute();
                break;

            case R.id.btn_date:
                ShowFromDatePicker();
                break;
            case R.id.btn_end_date:
                ShowToDatePicker();
                break;
            case R.id.check_button:
                if(selectID.isEmpty() || txtFromDate.getText().equals("") || txtToDate.getText().equals("")) {
                    Toast.makeText(context, "Plz choose data first!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new CheckDelegateAsync().execute();
                }
                break;
            case R.id.confirm_button:
                if(selectID.isEmpty() || txtFromDate.getText().equals("")  || txtToDate.getText().equals("")) {
                    Toast.makeText(context, "Plz choose data first!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new SaveDelegateAsync().execute();
                }

                break;
            default:break;
        }
    }
    public  void  ShowFromDatePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String strDay="";String strMonth="";
                        if(dayOfMonth<10)
                        {
                            strDay="0"+dayOfMonth;
                        }
                        else
                        {
                            strDay= String.valueOf(dayOfMonth);
                        }
                        monthOfYear=monthOfYear+1;
                        if(monthOfYear<10){

                            strMonth="0"+monthOfYear;
                        }
                        else
                        {
                            strMonth= String.valueOf(monthOfYear);
                        }

                        txtFromDate.setText(strDay + "-" + strMonth + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public  void  ShowToDatePicker(){
        // Get Current Date
        final Calendar c_end = Calendar.getInstance();
        mYear = c_end.get(Calendar.YEAR);
        mMonth = c_end.get(Calendar.MONTH);
        mDay = c_end.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog endDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String strDay="";String strMonth="";
                        if(dayOfMonth<10)
                        {
                            strDay="0"+dayOfMonth;
                        }
                        else
                        {
                            strDay= String.valueOf(dayOfMonth);
                        }
                        monthOfYear=monthOfYear+1;
                        if(monthOfYear<10){

                            strMonth="0"+monthOfYear;
                        }
                        else
                        {
                            strMonth= String.valueOf(monthOfYear);
                        }
                        txtToDate.setText(strDay + "-" + strMonth + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        endDatePickerDialog.show();
    }
    public class GetCurrentDelegateAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();
            depID = 1;  //To Delete
            API_URL += "getCurrentDelegate/" + depID;
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
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
                delegatedManager = gson.fromJson(responseString, DelegatedManager.class);


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            webServiceMessage = "Success";
            return webServiceMessage;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (webServiceMessage.equals("Success")) {
                AddDataToArrayList(CodeSetting.convertArrayToList(delegatedManager.Users));
                findViews();
                ShowData();
            }
        }

    }
    public  void ShowData()
    {
        if(delegatedManager!=null) {
            if (delegatedManager.DelegatedManagerID != 0) {
                currentDelegateID = delegatedManager.getDelegatedManagerID();
                RelativeLayout activeView = findViewById(R.id.active_delegate);
                activeView.setVisibility(View.VISIBLE);

                RelativeLayout emptyView = findViewById(R.id.empty_delegate);
                emptyView.setVisibility(View.GONE);

                btnDeleteDelegate.setVisibility(View.VISIBLE);
                TextView name = findViewById(R.id.dlg_name);
                if (name != null) {
                    name.setText(delegatedManager.User.FirstName + " " + delegatedManager.User.LastName);
                }
                TextView fromDate = findViewById(R.id.dlg_fromDate);
                if (fromDate != null) {
                    fromDate.setText(CodeSetting.convertDateString(delegatedManager.FromDate));
                }
                TextView ToDate = findViewById(R.id.dlg_toDate);
                if (ToDate != null) {
                    ToDate.setText(CodeSetting.convertDateString(delegatedManager.ToDate));
                }
            } else {

                RelativeLayout activeView = findViewById(R.id.active_delegate);
                activeView.setVisibility(View.GONE);
                RelativeLayout emptyView = findViewById(R.id.empty_delegate);
                emptyView.setVisibility(View.VISIBLE);
                btnDeleteDelegate.setVisibility(View.GONE);
            }
        }
    }

    public class CheckDelegateAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();
            depID = 1;  //To Delete
            String check_API_URL = "https://10.0.2.2:44312/Delegate/isActiveDelegateByUserID/" + selectID+"/"+txtFromDate.getText()+"/"+txtToDate.getText();
            try {
                URL url = new URL(check_API_URL);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
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
            } catch (IOException ex) {
                ex.printStackTrace();
             //   Toast.makeText(context, "Connection Error!", Toast.LENGTH_SHORT).show();
            }
            webServiceMessage = "Success";
            return webServiceMessage;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(responseString.equals("false"))
            {
                btnConfirmDelegate.setVisibility(View.VISIBLE);
                btnCheckDelegate.setVisibility(View.GONE);
                Toast.makeText(context, "Available for this date!", Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(context, "Already assigned for this date!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public class SaveDelegateAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();
            depID = 1;  //To Delete
            String save_API_URL = "https://10.0.2.2:44312/Delegate/SaveDelegatedManagerMB/" + selectID+"/"+txtFromDate.getText()+"/"+txtToDate.getText();

            try {
                URL url = new URL(save_API_URL);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
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
                if(!responseString.isEmpty()) {
                    Gson gson = new Gson();
                    delegatedManager = gson.fromJson(responseString, DelegatedManager.class);
                }
                else {
                    delegatedManager = new DelegatedManager();
                    webServiceMessage = "fail";
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return webServiceMessage;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (webServiceMessage.equals("Success")) {
                txtFromDate.setText("");
                txtToDate.setText("");
                btnConfirmDelegate.setVisibility(View.GONE);
                btnCheckDelegate.setVisibility(View.VISIBLE);
                 ShowData();
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(context, "Fail Try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class DeleteDelegateAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(Void... voids) {

            trustManager.trustAllCertificates();
            depID = 1;  //To Delete
            String delete_API_URL = "https://10.0.2.2:44312/Delegate/DeleteDelegate/" + currentDelegateID;

            try {
                URL url = new URL(delete_API_URL);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
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
                if(responseString.isEmpty()) {
                    Gson gson = new Gson();
                    delegatedManager = gson.fromJson(responseString, DelegatedManager.class);
                }
                else
                {
                    delegatedManager=new DelegatedManager();
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
            if (webServiceMessage.equals("Success")) {
                btnCheckDelegate.setVisibility(View.VISIBLE);
                selectID = "";
                txtFromDate.setText("");
                txtToDate.setText("");
                Toast.makeText(context, "Remove Successfully!", Toast.LENGTH_SHORT).show();
                ShowData();
            }
            else {

                Toast.makeText(context, "Fail!Try Again.", Toast.LENGTH_SHORT).show();
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
