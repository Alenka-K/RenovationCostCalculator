package com.example.renovationcostcalculator.services;

import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.repositories.RoomWindowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomWindowService {

    private final RoomWindowRepository windowRepository;

    public RoomWindowService(RoomWindowRepository windowRepository) {
        this.windowRepository = windowRepository;
    }


    public List<RoomWindow> findAll() {
        return windowRepository.findAll();
    }

    public RoomWindow findById(Long id){
        Optional<RoomWindow> roomWindow = windowRepository.findById(id);
        return roomWindow.orElse(null);
    }


    public void save(RoomWindow roomWindow){
        windowRepository.save(roomWindow);
    }

    public void update(Long id, RoomWindow roomWindowUpdate){
        roomWindowUpdate.setId(id);
        windowRepository.save(roomWindowUpdate);
    }

    public void delete(Long id){
        windowRepository.deleteById(id);
    }
}
