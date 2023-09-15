package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.model.utils.Count;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BalconyBlock implements Opening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double width;
    private double height;
    private double widthSmall;
    private double heightSmall;
    private double depth;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;





    @Override
    public double area() {
        double area = ((heightSmall*widthSmall) + height*(width-widthSmall))/1000000;
        return area;
    }

    @Override
    public double getSlopeArea() {
        double area = ((depth*height*2) + (width*depth))/1000000;
        return Count.rounding(area);
    }

    public double getSlopePerimeter(){
        return (height+width+height)/1000;
    }

    public double getBalconyBlockDoorAreaByFloor(){
        double area = (depth*(width-widthSmall))/1000000;
        return Count.rounding(area);
    }


    @Override
    public String toString() {
        return
                " -(" + width +
                        "x" + height +
                        "x" + heightSmall +
                        "x" + widthSmall +
                        "x" + depth + ")\n";
    }
}
