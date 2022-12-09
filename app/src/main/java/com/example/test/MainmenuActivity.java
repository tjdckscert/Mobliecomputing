package com.example.test;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainmenuActivity extends AppCompatActivity {
    final static String rootURL = "http://15.164.214.204/";
    ArrayList<SampleData> DataList = new ArrayList<SampleData>();
    SearchView searchView;
    backgroundtask backgroundtask;
    int bmpcoumt=0;
    Bitmap bmp[]= new Bitmap[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getintent = getIntent();
        String userID = getintent.getStringExtra("userID");
        setTitle("Login : "+userID);
        setContentView(R.layout.activity_main);//
        ListView listView = (ListView)findViewById(R.id.listView);
        String p_number = "";
        String p_name = "";
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<SampleData> viewList = new ArrayList<>();
                    Itemparser itemparser = new Itemparser(response);
                    for (int i=0; i<itemparser.getP_number().size();i++) {
                        String imgsrc = (i+1)+".jpg";
                        backgroundtask = new backgroundtask();
                        backgroundtask.execute(rootURL+imgsrc);
                        System.out.println("Parsing : "+rootURL+imgsrc);
                    }
                    sleep(300);
                    for (int i=0; i<itemparser.getP_number().size();i++) {

                        DataList.add(new SampleData(bmp[i], itemparser.getP_number().get(i), itemparser.getP_name().get(i),itemparser.getAmount().get(i)));
                        viewList.add(new SampleData(bmp[i], itemparser.getP_number().get(i), itemparser.getP_name().get(i),itemparser.getAmount().get(i)));
                    }
                    MyAdapter adapter = new MyAdapter(getApplicationContext(), viewList);
                    listView.setAdapter(adapter);
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonArray =  json.getJSONArray("List");
                    for (int i =0; i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                    }
                } catch (JSONException e ) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        MainmenuRequest productRequest = new MainmenuRequest(p_number,p_name,listener);
        RequestQueue queue  = Volley.newRequestQueue(MainmenuActivity.this);
        queue.add(productRequest);


        final MyAdapter myAdapter = new MyAdapter(this,DataList);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Bitmap img = DataList.get(position).getPoster();
                String pid  = DataList.get(position).getName();
                String number  = DataList.get(position).getNumber();
                String amount  = DataList.get(position).getAmount();

                Log.d("Parsing","position : "+position);
                Log.d("Parsing","pid : "+pid);
                Log.d("Parsing","number : "+number);
                Log.d("Parsing","img : "+img);
                Intent i = new Intent(getApplicationContext(),InformationActivity.class);
                i.putExtra("pid", pid);
                i.putExtra("number", number);
                i.putExtra("amount", amount);
                i.putExtra("img", img);
                startActivity(i);
            }
        });
        searchView = (SearchView)findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<SampleData> filter = new ArrayList<>();
                for(int i = 0; i < DataList.size(); i++){
                    SampleData date = DataList.get(i);
                    if(date.getName().toLowerCase().contains(newText.toLowerCase())){
                        filter.add(date);
                    }
                }
                MyAdapter adapter = new MyAdapter(getApplicationContext(), filter);
                listView.setAdapter(adapter);
                return false;
            }
        });
    }
    public class backgroundtask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                System.out.println("Parsing bmpcoumt: " + bmpcoumt);
                bmp[bmpcoumt++] = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmp[bmpcoumt];
        }
    }

}