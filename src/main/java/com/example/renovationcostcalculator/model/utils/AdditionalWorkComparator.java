package com.example.renovationcostcalculator.model.utils;

import com.example.renovationcostcalculator.model.price.AdditionalWork;

import java.util.Comparator;

public class AdditionalWorkComparator implements Comparator<AdditionalWork> {
    @Override
    public int compare(AdditionalWork o1, AdditionalWork o2) {
        int flag = o1.getId().compareTo(o2.getId());
        return flag;
    }
}
