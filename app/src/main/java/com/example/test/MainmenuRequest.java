package com.example.test;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainmenuRequest extends StringRequest {

    //(PHP 파일 연동) db 불러오기
    final static private String URL="http://15.164.214.204/ex.php";

    private Map<String, String> map;

    public MainmenuRequest(String p_number, String p_name, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map = new HashMap<>();
        map.put("p_number",p_number);
        map.put("p_name",p_name);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
