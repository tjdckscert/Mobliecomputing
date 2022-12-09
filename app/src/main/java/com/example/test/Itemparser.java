package com.example.test;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Itemparser {
    ArrayList<String> imgroot = new ArrayList<String>();
    ArrayList<String> p_name = new ArrayList<String>();
    ArrayList<String> p_number = new ArrayList<String>();



    ArrayList<String> amount = new ArrayList<String>();

    public ArrayList<String> getImgroot() {
        return imgroot;
    }

    public ArrayList<String> getAmount() { return amount; }

    public ArrayList<String> getP_name() {
        return p_name;
    }

    public ArrayList<String> getP_number() {
        return p_number;
    }

    public Itemparser(String resultjson){
        try{
            JSONObject jsonObject = new JSONObject(resultjson);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject(resultjson);
            jsonArray = jsonObject.getJSONArray("List");
            for (int i=0; i<jsonArray.length();i++){
                jsonObject1 = jsonArray.getJSONObject(i);
                String number  = jsonObject1.getString("p_name");
                p_number.add(jsonObject1.getString("p_number"));
                p_name.add(jsonObject1.getString("p_name"));
                amount.add(jsonObject1.getString("amount"));
                Log.d("Parsing","number : "+number);
            }
        }
        catch (JSONException e){
            Log.e("error", "json exception");
            e.printStackTrace();
        }
    }
}
