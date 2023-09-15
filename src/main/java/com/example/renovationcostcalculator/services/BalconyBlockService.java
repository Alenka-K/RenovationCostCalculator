package com.example.renovationcostcalculator.services;

import com.example.renovationcostcalculator.model.BalconyBlock;
import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.repositories.BalconyBlockRepository;
import com.example.renovationcostcalculator.repositories.RoomWindowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalconyBlockService {

    private final BalconyBlockRepository balconyBlockRepository;

    public BalconyBlockService(BalconyBlockRepository balconyBlockRepository) {
        this.balconyBlockRepository = balconyBlockRepository;
    }


    public List<BalconyBlock> findAll() {
        return balconyBlockRepository.findAll();
    }

    public BalconyBlock findById(Long id){
        Optional<BalconyBlock> balconyBlock = balconyBlockRepository.findById(id);
        return balconyBlock.orElse(null);
    }


    public void save(BalconyBlock balconyBlock){
        balconyBlockRepository.save(balconyBlock);
    }

    public void update(Long id, BalconyBlock balconyBlockUpdate){
        balconyBlockUpdate.setId(id);
        balconyBlockRepository.save(balconyBlockUpdate);
    }

    public void delete(Long id){
        balconyBlockRepository.deleteById(id);
    }
}
