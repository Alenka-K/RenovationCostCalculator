package com.example.renovationcostcalculator.model.room;



import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.RoomWindow;
import lombok.*;


import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RectangleRoom extends Room {

    private final String form = "Rectangle";

    private String name;
    private double length;
    private double width;



    @Override
    public double getFloorArea() {
        return (length*width)/1000000;
    }

    @Override
    public double getCeilingArea() {
        return length*width/1000000;
    }

    @Override
    public double getFloorPerimeter() {
        return ((length*2) + (width*2))/1000;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectangleRoom that = (RectangleRoom) o;

        if (Double.compare(that.length, length) != 0) return false;
        if (Double.compare(that.width, width) != 0) return false;
        if (form != null ? !form.equals(that.form) : that.form != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = form != null ? form.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "RectangleRoom{" +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", width=" + width +
                '}';
    }


}
