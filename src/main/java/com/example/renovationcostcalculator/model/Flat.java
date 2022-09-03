package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
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
    private List<Room> rooms;


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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flat flat = (Flat) o;

        if (id != null ? !id.equals(flat.id) : flat.id != null) return false;
        if (address != null ? !address.equals(flat.address) : flat.address != null) return false;
        return rooms != null ? rooms.equals(flat.rooms) : flat.rooms == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
