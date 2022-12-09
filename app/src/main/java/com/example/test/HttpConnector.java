package com.example.test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnector extends Thread{
    @Override
    public void run() {
        try {
            URL url = new URL("http://15.164.214.204/ex.php");
            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
            if (conn !=null){
                conn.setConnectTimeout(1000);
                conn.setRequestMethod("GET");
                System.out.println("Paring200" + conn.getResponseCode());
                int resCode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;
                if (resCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while (true){
                        line=  reader.readLine();
                        Log.d("Parsing","line : "+line);
                    }
                }

                Log.d("Parsing","rescode : "+resCode);
                Log.d("Parsing","HTTP_OK : "+HTTP_OK);
                System.out.println("Parsing");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
