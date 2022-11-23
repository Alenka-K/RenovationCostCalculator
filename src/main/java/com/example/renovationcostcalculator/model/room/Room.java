package com.example.renovationcostcalculator.model.room;


import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.Form;
import com.example.renovationcostcalculator.model.RoomWindow;

import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.price.Surface;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "ROOM")
public abstract class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Form form;

    private String name;

    private double height;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<RoomWindow> roomWindows = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<Door> doors = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_price",
            joinColumns = @JoinColumn(name = "price_type"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Price> priceSet;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id")
    private Flat flat;
    



    public double getWallsArea(){
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

    abstract double getCeilingPerimeter();

    abstract double getFloorPerimeter();
    public double getSlopeArea(){
        return this.getDoorSlopeArea()+this.getWindowSlopeArea();
    }
    public double getWindowSlopeArea(){
        double windowSlopeArea = 0;
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                windowSlopeArea = windowSlopeArea + roomWindow.getSlopeArea();
            }
        }
        return windowSlopeArea;
    }

    public double getDoorSlopeArea(){
        double doorSlopeArea = 0;
        if (!doors.isEmpty()){
            for (Door door : doors) {
                doorSlopeArea = doorSlopeArea + door.getSlopeArea();
            }
        }
        return doorSlopeArea;
    }

    public HashMap<Price, Double> getCalculateRoom(){
        HashMap<Price, Double> costAllOfWorkOnRoom = new HashMap<>();
        for (Price price: priceSet) {
            if (price.getSurface() == Surface.WALL & price.getUnit().equals("м2")) {
                double costPerRoom = getWallsArea() * price.getAmount();
                costAllOfWorkOnRoom.put(price, costPerRoom);
            }
            if (price.getSurface() == Surface.FLOOR & price.getUnit().equals("мп")) {
                double costPerRoom = getFloorPerimeter() * price.getAmount();
                costAllOfWorkOnRoom.put(price, costPerRoom);
            }
            if (price.getSurface() == Surface.FLOOR & price.getUnit().equals("м2")) {
                double costPerRoom = getFloorArea() * price.getAmount();
                costAllOfWorkOnRoom.put(price, costPerRoom);
            }
            if (price.getSurface() == Surface.CEILING & price.getUnit().equals("м2")) {
                double costPerRoom = getCeilingArea() * price.getAmount();
                costAllOfWorkOnRoom.put(price, costPerRoom);
            }
            if (price.getSurface() == Surface.CEILING & price.getUnit().equals("мп")) {
                double costPerRoom = getCeilingPerimeter() * price.getAmount();
                costAllOfWorkOnRoom.put(price, costPerRoom);
            }
        }

        return costAllOfWorkOnRoom;
    }

    public Double getAllCost(){
        return getCalculateRoom().values().stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public String toString() {


        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", roomWindows=" + roomWindows +
                ", doors=" + doors +
                ", priceSet=" + priceSet +
                ", flat=" + flat.getId() +
                '}';
    }
}
