package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.repositories.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    public Price findByTypeOfConstructionWork(String typeOfConstructionWork){
        Optional<Price> price = priceRepository.findById(typeOfConstructionWork);
        return price.orElse(null);
    }


    public void save(Price price){
        priceRepository.save(price);
    }

    public void update(String typeOfConstructionWork, Price updatePrice){
        updatePrice.setTypeOfConstructionWork(typeOfConstructionWork);
        priceRepository.save(updatePrice);
    }

    @Transactional
    public void delete(String typeOfConstructionWork){
        priceRepository.deleteByTypeOfConstructionWork(typeOfConstructionWork);
    }
}
