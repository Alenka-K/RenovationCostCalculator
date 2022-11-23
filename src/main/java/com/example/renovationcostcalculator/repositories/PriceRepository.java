package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PriceRepository extends JpaRepository<Price, String> {


    void deleteByType(String type);

    List<Price> findAllByRoomsIsContaining(Room room);


    Price findByType(String type);
}
