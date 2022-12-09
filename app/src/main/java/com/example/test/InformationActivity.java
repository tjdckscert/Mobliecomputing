package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InformationActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemview);

        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        String number = intent.getStringExtra("number");
        String nowamount = intent.getStringExtra("amount");
        Bitmap img = (Bitmap)intent.getParcelableExtra("img");
        setTitle("Information : "+pid);
        TextView nametxt = findViewById(R.id.name);
        nametxt.setText(pid);
        TextView p_numbertxt = findViewById(R.id.p_number);
        p_numbertxt.setText(number);
        TextView amounttxt = findViewById(R.id.nowamount);
        amounttxt.setText(nowamount);
        RadioGroup rdgGroup = findViewById( R.id.radioGroup );
        EditText amount = findViewById( R.id.amount );
        ImageView imageView = findViewById( R.id.img );
        imageView.setImageBitmap(img);

        Button insertbtn = (Button) findViewById(R.id.insert) ;
        insertbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rdoButton = findViewById(rdgGroup.getCheckedRadioButtonId());
                String strPgmId = rdoButton.getText().toString().toUpperCase();//라이도 버튼 값 가져오는 메소드
                Log.d("Parsing", "p_number : " + p_numbertxt.getText().toString());
                Log.d("Parsing", "strPgmId : " + strPgmId);
                Log.d("Parsing", "amount : " + amount.getText().toString());
                String p_number = p_numbertxt.getText().toString();
                String changeamount = amount.getText().toString();
                String tmpamount = amounttxt.getText().toString();
                String finalamount;
                if (amount.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "수량을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (strPgmId.equals("출고"))
                        finalamount = Integer.toString(Integer.parseInt(tmpamount) - Integer.parseInt(changeamount));
                    else
                        finalamount = Integer.toString(Integer.parseInt(changeamount) + Integer.parseInt(tmpamount));

                    if (Integer.parseInt(finalamount) < 0)
                        Toast.makeText(getApplicationContext(), "남은 수량은 0보다 작을수 없습니다.", Toast.LENGTH_SHORT).show();
                    else {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Boolean success = jsonResponse.getBoolean("success");
                                    System.out.println(success);
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "입/출고 신청에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        amounttxt.setText(finalamount);
                                        Log.d("Parsing", "amount : " + finalamount);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "입/출고 신청에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        WarehousingRequest warehousingRequest = new WarehousingRequest(finalamount, p_number, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(InformationActivity.this);
                        queue.add(warehousingRequest);
                    }
                }
            }


        });
    }
}
