package com.example.test;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WarehousingRequest extends StringRequest {

    final static private String URL = "http://15.164.214.204/warehousing.php"; // "http:// 퍼블릭 DSN 주소/Login.php";
    private Map<String, String> parameters;

    public WarehousingRequest(String amount, String p_number, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        Log.d("Parsing","amount : "+amount);
        Log.d("Parsing","p_number : "+p_number);
        parameters.put("amount", amount);
        parameters.put("pnumber", p_number);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}

