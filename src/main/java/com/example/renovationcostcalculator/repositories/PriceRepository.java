package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.price.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceRepository extends JpaRepository<Price, String> {


    void deleteByTypeOfConstructionWork(String typeOfConstructionWork);

    Price findByTypeOfConstructionWork(String typeOfConstructionWork);
}
