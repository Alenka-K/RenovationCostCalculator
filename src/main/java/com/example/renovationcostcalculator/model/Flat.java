package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.price.AdditionalWork;
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
import java.util.Map;
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



    public TreeMap<Price, List<Double>> getCalculateFlat(){
        TreeMap<Price, Double> valuesForFlatCalculations = new TreeMap<>(new PriceComparator());
        for (Room room : rooms ){
            TreeMap<Price, List<Double>> costAllOfWorkOnRoom = room.getCalculationOfCostOfBasicWork();
            TreeMap<AdditionalWork, Double> costAllOfAdditionalWorkOnRoom = room.getCalculationOfCostOfAdditionalWork();
            Set<Price> prices = costAllOfWorkOnRoom.keySet();
            for (Price price: prices) {
                if (valuesForFlatCalculations.containsKey(price)) {
                    Double characteristic = valuesForFlatCalculations.get(price) + costAllOfWorkOnRoom.get(price).get(0);
                    valuesForFlatCalculations.put(price, Count.rounding(characteristic));
                }else {
                    valuesForFlatCalculations.put(price, costAllOfWorkOnRoom.get(price).get(0));
                }
            }
            for(AdditionalWork additionalWork: room.getAdditionalWorks()){
                Price price = additionalWork.getPrice();
                if(valuesForFlatCalculations.containsKey(price)){
                    Double characteristic = valuesForFlatCalculations.get(price) + additionalWork.getValueForCalculation();
                    valuesForFlatCalculations.put(price, Count.rounding(characteristic));
                }else {
                    valuesForFlatCalculations.put(price, additionalWork.getValueForCalculation());
                }
            }

        }
        TreeMap<Price, List<Double>> costAllOfWorkOnFlat = new TreeMap<>(new PriceComparator());
        for (Map.Entry<Price, Double> entry: valuesForFlatCalculations.entrySet()){
            costAllOfWorkOnFlat.put(entry.getKey(), List.of(entry.getValue(), Count.rounding(entry.getKey().getAmount() * entry.getValue())));
        }

        return costAllOfWorkOnFlat;
    }


    public Double getAllCost(){
        return Count.rounding(getCalculateFlat().values().stream().map(x -> x.get(1)).mapToDouble(Double::doubleValue).sum());
    }

    public Double getAllTime(){
        Double time = 0.0;
        for(Room room : rooms){
            time = time + room.getAllTimeOnRoom();
        }
        return time;
    }
}
