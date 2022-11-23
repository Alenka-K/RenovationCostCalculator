package com.example.renovationcostcalculator.model.room;



import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Form;
import lombok.*;


import javax.persistence.*;
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
        return (length*width)/1000000;
    }

    @Override
    public double getCeilingArea() {

        return (length*width)/1000000;
    }

    @Override
    public double getCeilingPerimeter() {
        return (length+width)*2 /1000;
    }

    @Override
    public double getFloorPerimeter() {
        List<Door> doors = super.getDoors();
        double temp = 0;
        if (!doors.isEmpty()){
            for (Door door : doors) {
                temp = temp + door.getWidth();
            }
        }
        return (((length*2) + (width*2)) - temp)/1000;
    }




    @Override
    public String toString() {

        return super.toString() +
                ", length=" + length +
                ", width=" + width +
                '}';
    }


}
