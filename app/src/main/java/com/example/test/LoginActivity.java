package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText mID, mPassword;
    Button mIdSignInButton, mIdSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mID = (EditText) findViewById(R.id.id);
        mPassword = (EditText) findViewById(R.id.password);

        // Button
        mIdSignInButton = (Button) findViewById(R.id.loginButton); // sign in button

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

                                String userPassword = jsonResponse.getString("userPassword");
                                System.out.println(userPassword);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                // 로그인 하면서 사용자 정보 넘기기
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
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
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

    }
}
