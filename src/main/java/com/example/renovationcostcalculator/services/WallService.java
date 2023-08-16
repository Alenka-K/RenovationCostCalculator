package com.example.renovationcostcalculator.services;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Wall;
import com.example.renovationcostcalculator.repositories.DoorRepository;
import com.example.renovationcostcalculator.repositories.WallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WallService {

    private final WallRepository wallRepository;


    public WallService(WallRepository wallRepository) {
        this.wallRepository = wallRepository;

    }

    public List<Wall> findAll() {
        return wallRepository.findAll();
    }

    public Wall findById(Long id){
        Optional<Wall> wall = wallRepository.findById(id);
        return wall.orElse(null);
    }


    public void save(Wall wall){
        wallRepository.save(wall);
    }

    public void update(Long id, Wall updateWall){
        updateWall.setId(id);
        wallRepository.save(updateWall);
    }

    public void delete(Long id){
        wallRepository.deleteById(id);
    }
}
