package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.repositories.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Set<Price> findAll() {
        Set<Price> priceSet = new HashSet<>();
        for ( Price price : priceRepository.findAll()) {
            priceSet.add(price);
        }
        return priceSet;
    }

    public Price findByType(String type){
        Optional<Price> price = priceRepository.findById(type);
        return price.orElse(null);
    }

    @Transactional
    public void save(Price price){
        priceRepository.save(price);
    }


    @Transactional
    public void update(String type, Price updatePrice){
        updatePrice.setType(type);
        priceRepository.save(updatePrice);
    }

    @Transactional
    public void delete(String type){
        priceRepository.deleteByType(type);
    }
}
