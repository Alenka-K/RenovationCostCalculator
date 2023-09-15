package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.InfoOfFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InfoOfFlatRepository extends JpaRepository<InfoOfFlat, Long> {

    List<InfoOfFlat> findAllByFlat_Id(Long id);

    InfoOfFlat findInfoOfFlatById(Long id);
}
