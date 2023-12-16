package com.example.renovationcostcalculator.model.price;


import com.example.renovationcostcalculator.model.room.Room;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "PRICE")
public class Price {

    @Id
    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    private Surface surface;


    private int amount;

    @Enumerated(EnumType.STRING)
    private Unit unit;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "priceSet")
    private Set<Room> rooms;




    @Override
    public String toString() {
        return type + " (" +
                amount + "грн/" + unit + ")" ;
    }

}
