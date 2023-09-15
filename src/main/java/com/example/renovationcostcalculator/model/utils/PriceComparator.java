package com.example.renovationcostcalculator.model.utils;

import com.example.renovationcostcalculator.model.price.Price;

import java.util.Comparator;

public class PriceComparator implements Comparator<Price> {

        @Override
        public int compare(Price o1, Price o2) {

            int flag = o1.getSurface().compareTo(o2.getSurface());
            if(flag == 0) flag = o1.getType().toLowerCase().compareTo(o2.getType().toLowerCase());

            return flag;
        }


}
