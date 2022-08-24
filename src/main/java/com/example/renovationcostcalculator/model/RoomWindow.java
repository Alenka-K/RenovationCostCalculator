package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import lombok.*;

import javax.persistence.*;

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


    @ManyToOne
    @JoinColumn(name = "l_shaped_room_id")
    private L_shapedRoom l_shapedRoom;

    @ManyToOne
    @JoinColumn(name = "rectangle_room_id")
    private RectangleRoom rectangleRoom;


    @Override
    public String toString() {
        return "Окно " + id +
                ", ширина=" + width +
                ", высота=" + height +
                ", глубина=" + depth;
    }

    @Override
    public double area() {
        return width*height/1000000;
    }

    @Override
    public double getSlopeArea() {
        double area = (depth*height*2) + (width*depth);
        return area/1000000;
    }
}
