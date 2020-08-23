package com.example.team5_luss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.stream.Stream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {


    String url = "https://10.0.2.2:44312/CollectionPoint"; //set up the API url you want to call
    String responseString; // result string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn = findViewById(R.id.dummy);
        if (btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //call the WEB API
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
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }
    }
}