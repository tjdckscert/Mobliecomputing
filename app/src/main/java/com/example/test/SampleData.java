package com.example.test;

import android.graphics.Bitmap;

public class SampleData {
    private Bitmap poster;
    private String number;
    private String name;private String amount;

    public SampleData(Bitmap poster, String number, String name, String amount){
        this.poster = poster;
        this.number = number;
        this.name = name;
        this.amount=amount;
    }

        public Bitmap getPoster(){ return this.poster; }
        public String getNumber()
    {
        return this.number;
    }
        public String getName() { return this.name; }
        public String getAmount() { return amount; }
}