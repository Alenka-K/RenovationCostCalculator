package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.room.RectangleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RectangleRepository extends JpaRepository<RectangleRoom, Long> {

    public List<RectangleRoom> findAllByFlat_Id(Long id);

}
