package com.example.renovationcostcalculator.model.room;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.RoomWindow;
import lombok.*;
import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class L_shapedRoom  extends Room {
    private final String form = "L_shaped";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double height;
    private double length;
    private double width;
    private double lengthSmall;
    private double widthSmall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "l_shapedRoom")
    private List<RoomWindow> roomWindows;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "l_shapedRoom")
    private List<Door> doors;

    public double getWallsArea(){
        double area = this.getFloorPerimeter()*height/1000;
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                area = area - roomWindow.area();
            }
        }
        if (!doors.isEmpty()){
            for (Door door : doors) {
                area = area - door.area();
            }
        }
        return area;
    }

    @Override
    public double getFloorArea() {

        return ((length*width) - (lengthSmall*widthSmall))/1000000;
    }

    @Override
    public double getCeilingArea() {

        return ((length*width) - (lengthSmall*widthSmall))/1000000;
    }

    @Override
    public double getFloorPerimeter() {

        return length+width/1000;
    }


    @Override
    public double getWindowSlopeArea() {
        double windowSlopeArea = 0;
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                windowSlopeArea = windowSlopeArea + roomWindow.getSlopeArea();
            }
        }
        return windowSlopeArea;
    }

    @Override
    public double getDoorSlopeArea() {
        double doorSlopeArea = 0;
        if (!doors.isEmpty()){
            for (Door door : doors) {
                doorSlopeArea = doorSlopeArea + door.getSlopeArea();
            }
        }
        return doorSlopeArea;
    }

    @Override
    public String toString() {
        return "L_shapedRoom{" +
                "form='" + form + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", lengthSmall=" + lengthSmall +
                ", widthSmall=" + widthSmall +
                ", flat=" + flat.getId() +
                ", roomWindows=" + roomWindows +
                ", doors=" + doors +
                '}';
    }
}
