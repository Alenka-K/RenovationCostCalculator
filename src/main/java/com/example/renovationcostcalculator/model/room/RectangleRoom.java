package com.example.renovationcostcalculator.model.room;



import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Form;
import com.example.renovationcostcalculator.model.utils.Count;
import lombok.*;


import jakarta.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class RectangleRoom extends Room {

    private final Form form = Form.RECTANGLE;


    private double length;
    private double width;


    @Override
    public double getFloorArea() {
        return Count.rounding(length * width/1000000);
    }

    @Override
    public double getCeilingArea() {

        return Count.rounding(length * width/1000000);
    }

    @Override
    public double getCeilingPerimeter() {
        return Count.rounding((length + width)*2/1000);
    }

    @Override
    public double getFloorPerimeter() {

        return Count.rounding((length + width)*2/1000);
    }




    @Override
    public String toString() {

        return super.toString() +
                ", length=" + length +
                ", width=" + width +
                '}';
    }


}
