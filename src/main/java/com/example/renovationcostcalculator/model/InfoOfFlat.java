package com.example.renovationcostcalculator.model;

import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;


@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class InfoOfFlat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    private String info;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String  getInfo() {
        return info;
    }


    public String mapToString(){
        StringBuilder builder = new StringBuilder();
        for (Room room: flat.getRooms()) {
            builder.append(room.getName()).append(":").append("\r\n");

            for (Map.Entry<Price, Double> entry: room.getCalculateRoom().entrySet()) {
                builder.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" грн" + "\r\n");
            }
            builder.append("\r\n");
        }
        builder.append("___________________\r\n");
        builder.append("Итого: ").append(flat.getAllCost()).append(" грн");
        return builder.toString();
    }

    @Override
    public String toString() {
        return "InfoOfFlat{" +
                ", localDateTime=" + localDateTime +
                ", info='" + info + '\'' +
                '}';


    }


}
