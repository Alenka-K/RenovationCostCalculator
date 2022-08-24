package com.example.renovationcostcalculator.model.price;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Price {

    @Id
    String typeOfConstructionWork;

    int price;

    String unit;
}
