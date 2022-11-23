package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.Room;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;



    @Override
    public String toString() {
        return
                "(ширина=" + width +
                ", высота=" + height +
                ", глубина=" + depth + ")";
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
