package com.example.renovationcostcalculator.model.room;



import com.example.renovationcostcalculator.model.Form;
import lombok.*;


import javax.persistence.*;


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
        return (length*width)/1000000;
    }

    @Override
    public double getCeilingArea() {
        return length*width/1000000;
    }

    @Override
    public double getFloorPerimeter() {
        return ((length*2) + (width*2))/1000;
    }




    @Override
    public String toString() {

        return super.toString() +
                ", length=" + length +
                ", width=" + width +
                '}';
    }


}
