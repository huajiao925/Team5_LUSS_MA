package com.example.team5_luss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import Model.CollectionPoint;

public class MainActivity extends AppCompatActivity {

    public void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        System.out.println("test");

        Button btn = findViewById(R.id.CollectionList);
        if (btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //URL url = null;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                    try {
                        String target = "https://10.0.2.2:44312/CollectionPoint";
                        //String target = "https://www.google.com";

                        trustAllCertificates();
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

                        String responseString = response.toString();
                        System.out.println(responseString);

                        /*Type listType = new TypeToken<ArrayList<CollectionPoint>>(){}.getType();
                        List<CollectionPoint> collectionPoints = new Gson().fromJson(responseString,listType);
                        System.out.println(collectionPoints);*/

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<CollectionPoint>>(){}.getType();
                        List<CollectionPoint> collectionPoints = (List<CollectionPoint>)gson.fromJson(response.toString(),type);
                        System.out.println(collectionPoints);


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