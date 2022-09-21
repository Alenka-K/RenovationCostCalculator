package com.example.renovationcostcalculator.model.room;

import com.example.renovationcostcalculator.model.Form;
import lombok.*;
import javax.persistence.*;


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
        return super.toString() +
                "form='" + form + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", lengthSmall=" + lengthSmall +
                ", widthSmall=" + widthSmall +
                '}';
    }
}
