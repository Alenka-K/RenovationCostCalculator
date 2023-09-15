package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.model.utils.Count;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Wall {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double length;
    private double height;
    private double depth;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public double getWallArea(){
       double area = length * height *2;
       return Count.rounding(area/1000000);
    }

    public double getFloorAreaUnderWall() {

        return Count.rounding((depth * length)/1000000);
    }

    @Override
    public String toString() {
        return "Wall{" +
                ", length=" + length +
                ", height=" + height +
                ", depth=" + depth +
                ", room=" + room +
                '}';
    }
}
