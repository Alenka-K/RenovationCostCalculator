package com.example.renovationcostcalculator.model.room;




public abstract class Room {


    abstract double getWallsArea();
    abstract double getFloorArea();
    abstract double getCeilingArea();
    abstract double getFloorPerimeter();
    public double getSlopeArea(){
        return this.getDoorSlopeArea()+this.getWindowSlopeArea();
    }
    abstract double getWindowSlopeArea();
    abstract double getDoorSlopeArea();



}
