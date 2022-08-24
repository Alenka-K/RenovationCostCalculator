package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface L_shapedRepository extends JpaRepository<L_shapedRoom, Long> {
}
