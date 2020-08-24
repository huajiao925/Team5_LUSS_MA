package com.example.team5_luss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RequestDetailActivity extends AppCompatActivity {

    TextView reqIDDetail;
    TextView reqDateDetail;
    TextView reqByDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_detail);

        reqIDDetail = findViewById(R.id.detail_reqID);
        reqIDDetail.setText(getIntent().getStringExtra("requestID"));

        reqDateDetail = findViewById(R.id.detail_reqID);
        reqDateDetail.setText(getIntent().getStringExtra("requestDate"));

        reqByDetail = findViewById(R.id.detail_reqID);
        reqByDetail.setText(getIntent().getStringExtra("requestBy"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}