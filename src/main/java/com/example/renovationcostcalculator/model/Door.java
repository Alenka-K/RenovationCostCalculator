package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
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
        return "Двери " +
                ": ширина" + width +
                ", высота=" + height +
                ", глубина=" + depth +".";
    }

    @Override
    public double area() {
        return width*height/100000;
    }

    @Override
    public double getSlopeArea() {
        return ((depth*height*2) + (width*depth))/100000;
    }

}
