package com.example.renovationcostcalculator.model.price;


import com.example.renovationcostcalculator.model.room.Room;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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


    private int amount;

    private String unit;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "price_room",
            joinColumns = @JoinColumn(name = "price_type"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Room> rooms;

    @Override
    public String toString() {
        return type + " (" +
                amount + "грн/" + unit + ")" + "\r\n";
    }
}
