package com.example.team5_luss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import Model.User;

public class LoginActivity extends AppCompatActivity{
    final String url = "https://10.0.2.2:44312/Login/CheckLogin"; //set up the API url you want to call
    String responseString; // result string
    User loginUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email_Text=(EditText) findViewById(R.id.editTextEmail);
        final EditText psw_Text=(EditText) findViewById(R.id.editTextPassword);
        Button login_btn = findViewById(R.id.cirLoginButton);

        //get Shared Preferences, if exists, start Activity
        final SharedPreferences pref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        if (pref.contains("uName")) {
            boolean loginOK = logIn(pref.getString("email",""), pref.getString("password",""));
            if(loginOK) startMainActivity();
        }


        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (logIn(email_Text.getText().toString(), psw_Text.getText().toString())) {
                    //set Shared Preferences
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("email", loginUser.Email);
                    editor.putString("pwd", loginUser.Password);
                    editor.putString("uName", loginUser.FirstName);
                    editor.putInt("deptID", loginUser.DepartmentID);
                    editor.putString("role", loginUser.Role);
                    //editor.putInt("cpID", loginUser.Department.CollectionPointID);
                    editor.apply(); //or editor.apply() async for large data
                    startMainActivity();
                }
            }
        });
    }


    private boolean logIn(final String email, final String password){
        //Encrypt password
        final String hpwd = Encrypt.getMd5(password);

        //call the WEB API
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = url + "/" + email + "/" + "ICy5YqxZB1uWSwcVLSNLcA==";
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Gson gson = new Gson();
        loginUser = gson.fromJson(responseString, User.class);

        if(responseString != "" && loginUser != null){
            Toast.makeText(this,"Login Successful!",Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(this,"Something Wrong.Try Again!",Toast.LENGTH_LONG).show();
            return false;
        }

    }


    private void startMainActivity() {
        Intent intent = new Intent(this, CollectionPointActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStop() {
        super.onStop();
        final SharedPreferences pref = getSharedPreferences("user_credentials",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        finish();
    }
}

