package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.price.AdditionalWork;
import com.example.renovationcostcalculator.repositories.AdditionalWorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class AdditionalWorkService {

    private final AdditionalWorkRepository additionalWorkRepository;

    public AdditionalWorkService(AdditionalWorkRepository additionalWorkRepository) {
        this.additionalWorkRepository = additionalWorkRepository;
    }

    public List<AdditionalWork> findAll() {
        List<AdditionalWork> additionalWorks = new ArrayList<>(additionalWorkRepository.findAll());
        return additionalWorks;
    }

    public List<AdditionalWork> findAllByRoom(Long id){
        List<AdditionalWork> list = new ArrayList<>(additionalWorkRepository.findAllByRoom_Id(id));
        return list;
    }



    public void save(AdditionalWork additionalWork) {
        additionalWorkRepository.save(additionalWork);
    }


    @Transactional
    public void delete(AdditionalWork a) {
        additionalWorkRepository.delete(a);
    }
}
