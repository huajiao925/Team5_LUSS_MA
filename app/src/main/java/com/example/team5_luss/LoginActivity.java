package com.example.team5_luss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import Model.Request;
import Model.User;

public class LoginActivity extends AppCompatActivity{
    final String url = "https://10.0.2.2:44312/Login/MobileLogin"; //set up the API url you want to call
    String responseString; // result string
    User loginUser = new User();
    EditText email_Text;
    EditText psw_Text;
    Boolean login_OK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_Text=findViewById(R.id.editTextEmail);
        psw_Text=findViewById(R.id.editTextPassword);
        Button login_btn = findViewById(R.id.cirLoginButton);

        //get Shared Preferences, if exists, start Activity

        /*if (pref.contains("uName")) {
            boolean loginOK = logIn(pref.getString("email",""), pref.getString("password",""));
            if(loginOK) startMainActivity();
        }*/

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String email = email_Text.getText().toString();
                final String pwd = psw_Text.getText().toString();
                new ValidateUser().execute();
            }
        });
    }

    public class ValidateUser extends AsyncTask<String, Void, String> {
        final String email = email_Text.getText().toString();
        final String pwd = psw_Text.getText().toString();

        @Override
        protected String doInBackground(String... strings) {
            try {

                String target = url + "/" + email + "/" + pwd;
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!responseString.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                loginUser = gson.fromJson(responseString, User.class);
                login_OK = true;
                if (login_OK) {

                    final SharedPreferences pref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
                    //set Shared Preferences
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("email", loginUser.Email);
                    editor.putString("pwd", loginUser.Password);
                    editor.putString("uName", loginUser.FirstName);
                    editor.putInt("deptID", loginUser.DepartmentID);
                    editor.putString("role", loginUser.Role);
                    editor.putInt("userID", loginUser.UserID);
                    editor.apply();
                    startMainActivity();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Something Wrong.Try Again!", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void startMainActivity() {
        Intent intent;
        switch(loginUser.getRole()){
            case "store_clerk": intent = new Intent(this, DisbursementActivity.class);break;
            case "store_supervisor": intent = new Intent(this, AdjustVoucherListing.class);break;
            case "store_manager":  intent = new Intent(this, AdjustVoucherListing.class);break;
            case "dept_rep": intent = new Intent(this, CollectionPointActivity.class);break;
            case "dept_delegate":  intent = new Intent(this, RequestListActivity.class);break;
            case "dept_head":  intent = new Intent(this, RequestListActivity.class);break;
            default:
                throw new IllegalStateException("Unexpected value: " + loginUser.getRole());
        }
        startActivity(intent);
    }

    protected void onPressBack() {
        super.onStop();
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        finish();
    }
}

