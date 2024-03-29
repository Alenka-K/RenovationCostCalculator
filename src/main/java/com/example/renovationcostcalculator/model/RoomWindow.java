package com.example.renovationcostcalculator.model;

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
public class RoomWindow implements Opening{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double width;
    private double height;
    private double depth;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;



    @Override
    public double area() {
        return width*height/1000000;
    }

    @Override
    public double getSlopeArea() {
        double area = (depth*height*2) + (width*depth);
        return Count.rounding(area/1000000);
    }

    public double getSlopePerimeter() {
        double perimeter = (height*2) + width;
        return perimeter/1000;
    }


    @Override
    public String toString() {
        return
                " -(" + width +
                        "x" + height +
                        "x" + depth + ")\n";
    }
}
