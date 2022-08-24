package com.example.renovationcostcalculator.services;



import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.repositories.RectangleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RectangleService {

    private final RectangleRepository rectangleRepository;

    public RectangleService(RectangleRepository rectangleRepository) {
        this.rectangleRepository = rectangleRepository;
    }

    public List<RectangleRoom> findAll() {
        return rectangleRepository.findAll();
    }

    public RectangleRoom findById(Long id){
        Optional<RectangleRoom> rectangle = rectangleRepository.findById(id);
        return rectangle.orElse(null);
    }
    public List<RectangleRoom> findAllByFlat_ID(Long id) {

        return rectangleRepository.findAllByFlat_Id(id);
    }


    public void save(RectangleRoom room) {
        rectangleRepository.save(room);
    }


    public void delete(Long id){
        rectangleRepository.deleteById(id);
    }
}
