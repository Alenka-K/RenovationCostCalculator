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
public class RectangleRoom extends Room {

    private final String form = "Rectangle";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double length;
    private double width;
    private double height;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rectangleRoom")
    private List<RoomWindow> roomWindows;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rectangleRoom")
    private List<Door> doors;


    @Override
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
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectangleRoom that = (RectangleRoom) o;

        if (Double.compare(that.length, length) != 0) return false;
        if (Double.compare(that.width, width) != 0) return false;
        if (Double.compare(that.height, height) != 0) return false;
        if (form != null ? !form.equals(that.form) : that.form != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (flat != null ? !flat.equals(that.flat) : that.flat != null) return false;
        if (roomWindows != null ? !roomWindows.equals(that.roomWindows) : that.roomWindows != null) return false;
        return doors != null ? doors.equals(that.doors) : that.doors == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = form != null ? form.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (flat != null ? flat.hashCode() : 0);
        result = 31 * result + (roomWindows != null ? roomWindows.hashCode() : 0);
        result = 31 * result + (doors != null ? doors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RectangleRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", roomWindows=" + roomWindows +
                ", flat=" + flat.getId() +
                ", doors=" + doors +
                '}';
    }


}
