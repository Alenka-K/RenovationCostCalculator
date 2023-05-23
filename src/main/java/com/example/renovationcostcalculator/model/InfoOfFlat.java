package com.example.renovationcostcalculator.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class InfoOfFlat {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");



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

    public String dateTimeToString() {
        return localDateTime.format(formatter);
    }

    public String  getInfo() {
        return info;
    }



    public String formatOfLocalDateTime(){

        return localDateTime.format(formatter);
    }


    @Override
    public String toString() {
        return "InfoOfFlat{" +
                ", localDateTime=" + localDateTime +
                ", info='" + info + '\'' +
                '}';


    }


}
