package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.model.utils.Count;
import com.example.renovationcostcalculator.model.utils.PriceComparator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "FLAT")
public class Flat implements Serializable {
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


    public TreeMap<Price, List<Double>> getCalculateFlat() {
        TreeMap<Price, List<Double>> costAllOfWorkOnFlat = new TreeMap<>(new PriceComparator());
        for (Room room : rooms ){
            TreeMap<Price, List<Double>> costAllOfWorkOnRoom = room.getCalculateRoom();
            Set<Price> prices = costAllOfWorkOnRoom.keySet();
            for (Price price: prices) {
                if (costAllOfWorkOnFlat.containsKey(price)) {
                    Double value = costAllOfWorkOnFlat.get(price).get(1) + costAllOfWorkOnRoom.get(price).get(1);
                    Double characteristic = costAllOfWorkOnFlat.get(price).get(0) + costAllOfWorkOnRoom.get(price).get(0);
                    costAllOfWorkOnFlat.put(price, List.of(Count.rounding(characteristic), Count.rounding(value)));
                }else {
                    costAllOfWorkOnFlat.put(price, List.of(Count.rounding(costAllOfWorkOnRoom.get(price).get(0)), Count.rounding(costAllOfWorkOnRoom.get(price).get(1))));
                }
            }
        }
        return costAllOfWorkOnFlat;
    }
    public Double getAllCost(){
        return Count.rounding(getCalculateFlat().values().stream().map(x -> x.get(1)).mapToDouble(Double::doubleValue).sum());
    }
}
