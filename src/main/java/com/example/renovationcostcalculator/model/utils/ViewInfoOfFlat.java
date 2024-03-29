package com.example.renovationcostcalculator.model.utils;

import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ViewInfoOfFlat {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public static String formatOfLocalDateTime(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }


    public static String infoOfFlatToString(Flat flat){
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Price, List<Double>> entry: flat.getCalculateFlat().entrySet()) {
                builder.append("  ").append(entry.getKey()).append(": ").append(entry.getValue().get(0)).append("*").append(entry.getKey().getAmount()).append("=").append(entry.getValue().get(1)).append(" грн" + "\r\n");
            }
            builder.append("\r\n");
        builder.append("___________________\r\n");
        builder.append("Итого: ").append(flat.getAllCost()).append(" грн");
        return builder.toString();
    }
}
