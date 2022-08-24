package com.example.renovationcostcalculator.services;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.repositories.DoorRepository;
import com.example.renovationcostcalculator.repositories.FlatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DoorService {

    private final DoorRepository doorRepository;


    public DoorService(DoorRepository doorRepository) {
        this.doorRepository = doorRepository;
    }

    public List<Door> findAll() {
        return doorRepository.findAll();
    }

    public Door findById(Long id){
        Optional<Door> door = doorRepository.findById(id);
        return door.orElse(null);
    }


    public void save(Door door){
        doorRepository.save(door);
    }

    public void update(Long id, Door updateDoor){
        updateDoor.setId(id);
        doorRepository.save(updateDoor);
    }

    public void delete(Long id){
        doorRepository.deleteById(id);
    }
}
