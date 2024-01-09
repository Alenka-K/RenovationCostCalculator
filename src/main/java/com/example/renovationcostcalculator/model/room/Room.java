package com.example.renovationcostcalculator.model.room;


import com.example.renovationcostcalculator.model.*;
//import com.example.renovationcostcalculator.model.price.AdditionalWork;
import com.example.renovationcostcalculator.model.price.AdditionalWork;
import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.price.Surface;
import com.example.renovationcostcalculator.model.price.Unit;
import com.example.renovationcostcalculator.model.utils.AdditionalWorkComparator;
import com.example.renovationcostcalculator.model.utils.Count;
import com.example.renovationcostcalculator.model.utils.PriceComparator;
import com.example.renovationcostcalculator.model.utils.TimeConversion;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.*;

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
    private List<BalconyBlock> balconyBlocks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<Door> doors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<Wall> walls = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_price",
            joinColumns = @JoinColumn(name = "price_type"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Price> priceSet;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private List<AdditionalWork> additionalWorks = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id")
    private Flat flat;
    


    public  double getPartitionWallsArea() {
        double area = 0.0;
        if (!walls.isEmpty()) {
            for (Wall wall : walls) {
                area = area + wall.getWallArea();
            }
        }
        return Count.rounding(area);
    }

    public double getWallsArea(){
        double area = this.getFloorPerimeter()*(height/1000);
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                area = area - roomWindow.area();
            }
        }
        if (!balconyBlocks.isEmpty()){
            for (BalconyBlock balconyBlock : balconyBlocks) area = area - balconyBlock.area();
        }
        if (!doors.isEmpty()) {
            for (Door door : doors) {
                area = area - door.area();
            }
        }
        return Count.rounding(area + this.getPartitionWallsArea());
    }

    abstract double getFloorArea();

    public double getAllFloorArea(){
        double area = this.getFloorArea();
        if (!walls.isEmpty()) {
            for (Wall wall : walls) {
                area = area - wall.getFloorAreaUnderWall();
            }
        }
        return Count.rounding(area);

    }
    abstract double getFloorPerimeter();

    abstract double getCeilingArea();
    abstract double getCeilingPerimeter();

    public double getPerimeterIncludingDoors(){
        List<Door> doors = this.getDoors();
        double temp = this.getFloorPerimeter();
        if (!doors.isEmpty()){
            for (Door door : doors) {
                temp = temp - door.getWidth()/1000;
            }
        }
        return Count.rounding(temp);
    }
    public double getWindowSlopeArea(){
        double windowSlopeArea = 0;
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                windowSlopeArea = windowSlopeArea + roomWindow.getSlopeArea();
            }
        }
        return Count.rounding(windowSlopeArea);
    }

    public double getWindowSlopePerimeter(){
        double windowSlopePerimeter = 0;
        if (!roomWindows.isEmpty()){
            for (RoomWindow roomWindow : roomWindows) {
                windowSlopePerimeter = windowSlopePerimeter + roomWindow.getSlopePerimeter();
            }
        }
        return Count.rounding(windowSlopePerimeter);
    }
    public double getDoorSlopeArea(){
        double doorSlopeArea = 0.0;
        if (!doors.isEmpty()){
            for (Door door : doors) {
                doorSlopeArea = doorSlopeArea + door.getSlopeArea();
            }
        }
        return Count.rounding(doorSlopeArea);
    }

    public double getBalconyBlockSlopeArea(){
        double balconyBlockSlopeArea = 0.0;
        if (!balconyBlocks.isEmpty()){
            for (BalconyBlock balconyBlock : balconyBlocks) {
                balconyBlockSlopeArea = balconyBlockSlopeArea + balconyBlock.getSlopeArea();
            }
        }
        return Count.rounding(balconyBlockSlopeArea);
    }

    public double getSlopeArea(){
        return Count.rounding(this.getDoorSlopeArea()+this.getWindowSlopeArea()+ this.getBalconyBlockSlopeArea());
    }


    public double getDoorPerimeter(){
        double doorPerimeter = 0.0;
        if (!doors.isEmpty()){
            for (Door door : doors) {
                doorPerimeter = doorPerimeter + door.getPerimeter();
            }
        }
        return Count.rounding(doorPerimeter);
    }


    public double getBalconyBlockSlopePerimeter(){
        double balconyBlockSlopePerimeter = 0.0;
        if (!balconyBlocks.isEmpty()){
            for (BalconyBlock balconyBlock : balconyBlocks) {
                balconyBlockSlopePerimeter = balconyBlockSlopePerimeter + balconyBlock.getSlopePerimeter();
            }
        }
        return Count.rounding(balconyBlockSlopePerimeter);
    }

    public TreeMap<Price, Double> priceAndValueForCalculation(){
        TreeMap<Price, Double> mapForCalculation = new TreeMap<>();
        for (Price price: priceSet) {
            if (price.getSurface() == Surface.WALL & price.getUnit() == Unit.м2) {
                mapForCalculation.put(price, this.getWallsArea());
            }
            if (price.getSurface() == Surface.FLOOR & price.getUnit() == Unit.мп) {
                mapForCalculation.put(price, this.getFloorPerimeter());
            }
            if (price.getSurface() == Surface.FLOOR & price.getUnit() == Unit.м2) {
                mapForCalculation.put(price, this.getAllFloorArea());
            }
            if (price.getSurface() == Surface.CEILING & price.getUnit() ==Unit.м2) {
                mapForCalculation.put(price, getCeilingArea());
            }
            if (price.getSurface() == Surface.CEILING & price.getUnit() == Unit.мп) {
                mapForCalculation.put(price, getCeilingPerimeter());
            }
            if (price.getSurface() == Surface.WINDOWSLOPE & price.getUnit() == Unit.м2) {
                mapForCalculation.put(price, getWindowSlopeArea());
            }
            if (price.getSurface() == Surface.DOORSLOPE & price.getUnit() == Unit.м2) {
                mapForCalculation.put(price, getDoorSlopeArea());
            }
            if (price.getSurface() == Surface.DOORSLOPE & price.getUnit() == Unit.мп) {
                mapForCalculation.put(price, getDoorPerimeter());
            }
            if (price.getSurface() == Surface.BALCONYBLOCK & price.getUnit() == Unit.м2) {
                mapForCalculation.put(price, getBalconyBlockSlopeArea());
            }
            if   (price.getSurface() == Surface.BALCONYBLOCK & price.getUnit() == Unit.мп) {
                mapForCalculation.put(price, getBalconyBlockSlopePerimeter());
            }
        }
        return mapForCalculation;
    }
    public TreeMap<Price, List<Double>> getCalculationOfCostOfBasicWork(){

        TreeMap<Price, List<Double>> costCalculation = new TreeMap<>(new PriceComparator());
        for (Price price: priceSet) {
            if (price.getSurface() == Surface.WALL & price.getUnit() == Unit.м2) {
                double costPerRoom = this.getWallsArea() * price.getAmount();
                costCalculation.put(price, List.of(this.getWallsArea(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.FLOOR & price.getUnit() == Unit.мп) {
                double costPerRoom = this.getFloorPerimeter() * price.getAmount();
                costCalculation.put(price, List.of(this.getFloorPerimeter(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.FLOOR & price.getUnit() == Unit.м2) {
                double costPerRoom =this.getAllFloorArea()  * price.getAmount();
                costCalculation.put(price, List.of(this.getAllFloorArea(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.CEILING & price.getUnit() == Unit.м2) {
                double costPerRoom = getCeilingArea() * price.getAmount();
                costCalculation.put(price, List.of(getCeilingArea(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.CEILING & price.getUnit() == Unit.мп) {
                double costPerRoom = getCeilingPerimeter() * price.getAmount();
                costCalculation.put(price, List.of(getCeilingPerimeter(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.WINDOWSLOPE & price.getUnit() == Unit.м2) {
                double costPerRoom = getWindowSlopeArea() * price.getAmount();
                costCalculation.put(price, List.of(getWindowSlopeArea(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.WINDOWSLOPE & price.getUnit() == Unit.мп) {
                double costPerRoom = getWindowSlopePerimeter() * price.getAmount();
                costCalculation.put(price, List.of(getWindowSlopePerimeter(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.DOORSLOPE & price.getUnit() == Unit.м2) {
                double costPerRoom = getDoorSlopeArea() * price.getAmount();
                costCalculation.put(price, List.of(getDoorSlopeArea(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.DOORSLOPE & price.getUnit() == Unit.мп) {
                double costPerRoom = getDoorPerimeter() * price.getAmount();
                costCalculation.put(price, List.of(getDoorPerimeter(),Count.rounding(costPerRoom)));
            }
            if (price.getSurface() == Surface.BALCONYBLOCK & price.getUnit() == Unit.м2) {
                double costPerRoom = getBalconyBlockSlopeArea() * price.getAmount();
                costCalculation.put(price, List.of(getBalconyBlockSlopeArea(),Count.rounding(costPerRoom)));
            }
            if   (price.getSurface() == Surface.BALCONYBLOCK & price.getUnit() == Unit.мп) {
                    double costPerRoom = getBalconyBlockSlopePerimeter() * price.getAmount();
                    costCalculation.put(price, List.of(getBalconyBlockSlopePerimeter(),Count.rounding(costPerRoom)));
            }
        }

        return costCalculation;
    }

    public TreeMap<AdditionalWork, Double> getCalculationOfCostOfAdditionalWork(){
        TreeMap<AdditionalWork, Double> costCalculation = new TreeMap<>(new AdditionalWorkComparator());
        for (AdditionalWork additionalWork : additionalWorks) {
            Double value = additionalWork.getValueForCalculation()*additionalWork.getPrice().getAmount();
            costCalculation.put(additionalWork, value);
        }
        return costCalculation;
    }

    public TreeMap<Price, List<Double>> getCalculationOfTimeSpentOnBasicWork() {
        TreeMap<Price, List<Double>> timeCalculation = new TreeMap<>(new PriceComparator());

        for (Map.Entry<Price, List<Double>> entry : getCalculationOfCostOfBasicWork().entrySet()) {
            Double value = entry.getKey().getLeadTime()*entry.getValue().get(0);
            timeCalculation.put(entry.getKey(), List.of(entry.getValue().get(0), value));
        }
        for (AdditionalWork additionalWork : additionalWorks) {
            if (timeCalculation.containsKey(additionalWork.getPrice())){
                Double volume = timeCalculation.get(additionalWork.getPrice()).get(0) + additionalWork.getValueForCalculation();
                timeCalculation.put(additionalWork.getPrice(), List.of(volume, volume*additionalWork.getPrice().getLeadTime()));
            }else{
                Double value = additionalWork.getValueForCalculation()* additionalWork.getPrice().getLeadTime();
                timeCalculation.put(additionalWork.getPrice(), List.of(additionalWork.getValueForCalculation(), value));
            }


        }

        return timeCalculation;
    }

    public Double getAllTimeOnRoom(){
        return getCalculationOfTimeSpentOnBasicWork().values().stream().map(x -> x.get(1)).mapToDouble(Double::doubleValue).sum();
    }

    public String getAllTimeOnRoomConversion(){
        return TimeConversion.conversion(getAllTimeOnRoom());
    }

    public Double getAllCostOfBasicWork(){
        return Count.rounding(getCalculationOfCostOfBasicWork().values().stream().map(x -> x.get(1)).mapToDouble(Double::doubleValue).sum());
    }

    public Double getAllCostOfAdditionalWork(){
        return Count.rounding(getCalculationOfCostOfAdditionalWork().values().stream().mapToDouble(Double::doubleValue).sum());
    }

    public Double getAllCost(){
        return getAllCostOfBasicWork()+getAllCostOfAdditionalWork();
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
