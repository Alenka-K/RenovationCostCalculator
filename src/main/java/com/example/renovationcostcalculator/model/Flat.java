package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "FLAT")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    private String customerPhone;


    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "flat")
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


    public TreeMap<Price, Double> getCalculateFlat() {
        TreeMap<Price, Double> costAllOfWorkOnFlat = new TreeMap<>();
        for (Room room : rooms ){
            TreeMap<Price, Double> costAllOfWorkOnRoom = room.getCalculateRoom();
            Set<Price> prices = costAllOfWorkOnRoom.keySet();
            for (Price price: prices) {
                if (costAllOfWorkOnFlat.containsKey(price)) {
                    Double value = costAllOfWorkOnFlat.get(price) + costAllOfWorkOnRoom.get(price);
                    costAllOfWorkOnFlat.put(price, value);
                }else {
                    costAllOfWorkOnFlat.put(price, costAllOfWorkOnRoom.get(price));
                }
            }
        }
        return costAllOfWorkOnFlat;
    }
    public Double getAllCost(){
        return getCalculateFlat().values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
