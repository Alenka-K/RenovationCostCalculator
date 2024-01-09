package com.example.renovationcostcalculator.model.utils;

public class TimeConversion {

    public static String conversion(double time){

        String timeConversion;
        if (time >= 60) {
            if (time >= 1440){
                double temp = Count.rounding(time/1440);
                System.out.println(temp);
                double days = Math.floor(temp);
                System.out.println(days);
                double hours = Count.rounding((temp - days) * 24);
                System.out.println(hours);

                timeConversion = days + " (дней) " + hours + " (часов)";

            }else {
                timeConversion = time/60 + " (часов)";
            }
        }else {
            timeConversion = time + " (мин)";
        }
        return timeConversion;
    }
}
