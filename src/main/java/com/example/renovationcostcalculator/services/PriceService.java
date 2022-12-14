package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.price.Surface;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.repositories.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> findAll() {
        Set<Price> priceSet = new HashSet<>(priceRepository.findAll());
        List<Price> list = priceSet.stream().sorted(Comparator.comparing(Price::getSurface)).collect(Collectors.toList());
        return list;
    }

    public Price findByType(String type){
        Optional<Price> price = priceRepository.findById(type);
        return price.orElse(null);
    }

    public List<Price> findAllByRoomsIsContaining(Room room) {
        List<Price> prices = priceRepository.findAllByRoomsIsContaining(room);
        return prices;
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
