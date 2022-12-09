package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SampleData> movieDataList = new ArrayList<SampleData>();;
    SearchView searchView;
    EditText mID, mPassword;
    Button mIdSignInButton, mIdSignUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("");
        mID = (EditText) findViewById(R.id.id);
        mPassword = (EditText) findViewById(R.id.password);
        mIdSignInButton = (Button) findViewById(R.id.loginButton);
        mIdSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userID = mID.getText().toString();
                String userPassword = mPassword.getText().toString();
                System.out.println(userID);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                String userID = jsonResponse.getString("userID");
                                System.out.println(userPassword);
                                Intent intent = new Intent(getApplicationContext(), MainmenuActivity.class);
                                intent.putExtra("userID", userID);

                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

    }

    }