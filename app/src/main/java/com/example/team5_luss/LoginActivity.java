package com.example.team5_luss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

/*        Button login_btn = findViewById(R.id.cirLoginButton);
        if (login_btn != null) {
            login_btn.setOnClickListener((View.OnClickListener) this);
        }*/
    }


    @Override
    public void onClick(View view) {
        //Check For login
        EditText email_Text=(EditText) findViewById(R.id.editTextEmail);
        String email=email_Text.getText().toString();
        EditText psw_Text=(EditText) findViewById(R.id.editTextPassword);
        String password=psw_Text.getText().toString();
        if(email.equals("ws") && password.equals("123")) {
            Toast success=Toast.makeText(this,"Login Successful!",Toast.LENGTH_LONG);
            success.show();
            //startActivity(new Intent(this, CollectionActivity.class));
            startActivity(new Intent(this, CollectionActivity.class));
        }
        else
        {
            Toast fail=Toast.makeText(this,"Something Wrong.Try Again!",Toast.LENGTH_LONG);
            fail.show();

        }

    }
}

