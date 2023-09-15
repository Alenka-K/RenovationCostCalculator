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
@Getter
@Setter
@EqualsAndHashCode
public class L_shapedRoom  extends Room {

    private final Form form = Form.L_SHAPED;

    private double length;
    private double width;
    private double lengthSmall;
    private double widthSmall;





    @Override
    public double getFloorArea() {
        double area = ((length*width) - (lengthSmall*widthSmall))/1000000;
        return Count.rounding(area);
    }

    @Override
    public double getCeilingArea() {
        double area = ((length*width) - (lengthSmall*widthSmall))/1000000;
        return Count.rounding(area);
    }

    @Override
    double getCeilingPerimeter() {
        return Count.rounding((length + width)*2/1000);
    }


    @Override
    public double getFloorPerimeter() {

        return Count.rounding((length + width)*2/1000);
    }






    @Override
    public String toString() {
        return super.toString() +
                "form='" + form + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", lengthSmall=" + lengthSmall +
                ", widthSmall=" + widthSmall +
                '}';
    }
}
