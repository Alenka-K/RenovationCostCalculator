package com.example.renovationcostcalculator.model.utils;

public class Count {


    public static double rounding(double x){
        double temp =  Math.round(x*100);
        return temp/100;
    }
}
