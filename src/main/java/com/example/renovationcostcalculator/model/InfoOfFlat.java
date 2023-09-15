package com.example.renovationcostcalculator.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class InfoOfFlat implements Serializable {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @Lob
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

    public void writeToFile(){
        File file = new File(this.flat.getAddress());
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

// Помещаем текст внутрь файла
        pw.println(this);
        System.out.println(this);
        pw.close();

    }


    @Override
    public String toString() {
        return "InfoOfFlat{" +
                ", localDateTime=" + localDateTime +
                ", info: \n " + info + '\'' +
                '}';


    }


}
