package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.model.utils.Count;
import lombok.*;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Door implements Opening{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double width;
    private double height;
    private double depth;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;



    @Override
    public String toString() {
        return
                " -(" + height +
                "x" + width +
                "x" + depth +")\n";
    }

    @Override
    public double area() {
        return Count.rounding(width*height/1000000);
    }

    @Override
    public double getSlopeArea() {
        double slopeArea = ((depth*height*2) + (width*depth))/1000000;
        return Count.rounding(slopeArea);
    }

    public double getPerimeter(){
        double perimeter = ((height*2)+width)/1000;
        return Count.rounding(perimeter);
    }

    public double getDoorAreaByFloor(){return Count.rounding(width*depth/1000000);}

}
