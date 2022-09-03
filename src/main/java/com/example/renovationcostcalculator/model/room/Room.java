package com.example.renovationcostcalculator.model.room;


import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.model.price.Price;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public abstract class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double height;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<RoomWindow> roomWindows;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "room")
    private List<Door> doors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flat_id")
    private Flat flat;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "room")
//    private List<Price> priceList;


    double getWallsArea(){
        double area = this.getFloorPerimeter()*(height/1000);
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
    };
    abstract double getFloorArea();
    abstract double getCeilingArea();
    abstract double getFloorPerimeter();
    public double getSlopeArea(){
        return this.getDoorSlopeArea()+this.getWindowSlopeArea();
    }
    double getWindowSlopeArea(){
        double windowSlopeArea = 0;
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                windowSlopeArea = windowSlopeArea + roomWindow.getSlopeArea();
            }
        }
        return windowSlopeArea;
    }

    double getDoorSlopeArea(){
        double doorSlopeArea = 0;
        if (!doors.isEmpty()){
            for (Door door : doors) {
                doorSlopeArea = doorSlopeArea + door.getSlopeArea();
            }
        }
        return doorSlopeArea;
    }



}
