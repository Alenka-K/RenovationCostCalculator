package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.price.AdditionalWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdditionalWorkRepository extends JpaRepository<AdditionalWork, Long> {

    public List<AdditionalWork> findAllByRoom_Id(Long id);



}
