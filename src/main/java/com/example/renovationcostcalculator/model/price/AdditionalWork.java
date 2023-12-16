package com.example.renovationcostcalculator.model.price;

import com.example.renovationcostcalculator.model.room.Room;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "AdditionalWork")
public class AdditionalWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type")
    private Price price;


    @Column(name = "valueForCalculation")
    private Double valueForCalculation;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    @Override
    public String toString() {
        return "AdditionalWork{" +
                "id=" + id + price.getType() +
                ", room=" + room.getId() +
                ", значение для расчета" + valueForCalculation +
                '}';
    }
}


