package com.example.renovationcostcalculator.model.price;


import com.example.renovationcostcalculator.model.room.Room;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "PRICE")
public class Price implements Comparable<Price>{

    @Id
    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    private Surface surface;


    private int amount;

    private String unit;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "priceSet")
    private Set<Room> rooms;

    @Override
    public String toString() {
        return type + " (" +
                amount + "грн/" + unit + ")" + "\r\n";
    }

    @Override
    public int compareTo(Price o) {
        return o.getType().compareTo(type);

    }
}
