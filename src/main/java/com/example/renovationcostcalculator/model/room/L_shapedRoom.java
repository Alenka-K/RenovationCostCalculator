package com.example.renovationcostcalculator.model.room;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Form;
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

        return Math.ceil(((length*width) - (lengthSmall*widthSmall))/10000)/100;
    }

    @Override
    public double getCeilingArea() {

        return Math.ceil(((length*width) - (lengthSmall*widthSmall))/10000)/100;
    }

    @Override
    double getCeilingPerimeter() {
        return Math.ceil((length+width)*2/10)/100;
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

        return Math.ceil((((length+width)*2) - temp)/10)/100;
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
