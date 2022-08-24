package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FLAT")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "flat")
    private List<RectangleRoom> rectangleRooms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "flat")
    private List<L_shapedRoom> l_shapedRooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RectangleRoom> getRectangleRooms() {
        return rectangleRooms;
    }

    public void setRectangleRooms(List<RectangleRoom> rectangleRooms) {
        this.rectangleRooms = rectangleRooms;
    }

    public List<L_shapedRoom> getL_shapedRooms() {
        return l_shapedRooms;
    }

    public void setL_shapedRooms(List<L_shapedRoom> l_shapedRooms) {
        this.l_shapedRooms = l_shapedRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flat flat = (Flat) o;

        if (id != null ? !id.equals(flat.id) : flat.id != null) return false;
        if (address != null ? !address.equals(flat.address) : flat.address != null) return false;
        if (rectangleRooms != null ? !rectangleRooms.equals(flat.rectangleRooms) : flat.rectangleRooms != null)
            return false;
        return l_shapedRooms != null ? l_shapedRooms.equals(flat.l_shapedRooms) : flat.l_shapedRooms == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (rectangleRooms != null ? rectangleRooms.hashCode() : 0);
        result = 31 * result + (l_shapedRooms != null ? l_shapedRooms.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", rectangleRooms=" + rectangleRooms +
                ", l_shapedRooms=" + l_shapedRooms +
                '}';
    }
}
