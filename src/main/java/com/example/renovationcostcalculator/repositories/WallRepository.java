package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Wall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallRepository extends JpaRepository<Wall, Long> {
}
