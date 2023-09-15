package com.example.renovationcostcalculator.repositories;



import com.example.renovationcostcalculator.model.BalconyBlock;
import com.example.renovationcostcalculator.model.RoomWindow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalconyBlockRepository extends JpaRepository<BalconyBlock, Long> {
}
