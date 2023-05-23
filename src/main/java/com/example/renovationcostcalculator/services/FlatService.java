package com.example.renovationcostcalculator.services;



import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.repositories.FlatRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FlatService {

    private final FlatRepository flatRepository;

    public FlatService(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }

    public List<Flat> findAll() {
        return flatRepository.findAll();
    }

    public Flat findById(Long id){
        return flatRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Flat flat){
        flatRepository.save(flat);
    }

    @Transactional
    public void update(Long id, Flat updateFlat){
        updateFlat.setId(id);
        flatRepository.save(updateFlat);
    }

    @Transactional
    public void delete(Long id){

        flatRepository.deleteById(id);
    }
}
