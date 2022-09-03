package com.example.renovationcostcalculator.model.room;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.RoomWindow;
import lombok.*;
import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class L_shapedRoom  extends Room {
    private final String form = "L_shaped";

    private String name;

    private double length;
    private double width;
    private double lengthSmall;
    private double widthSmall;





    @Override
    public double getFloorArea() {

        return ((length*width) - (lengthSmall*widthSmall))/1000000;
    }

    @Override
    public double getCeilingArea() {

        return ((length*width) - (lengthSmall*widthSmall))/1000000;
    }

    @Override
    public double getFloorPerimeter() {

        return length+width/1000;
    }






    @Override
    public String toString() {
        return "L_shapedRoom{" +
                "form='" + form + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", lengthSmall=" + lengthSmall +
                ", widthSmall=" + widthSmall +
                '}';
    }
}
