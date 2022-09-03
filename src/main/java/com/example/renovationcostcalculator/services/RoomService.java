package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class RoomService {

    private final RoomRepository roomRepository;


    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id){
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null);
    }
    public List<Room> findAllByFlat_ID(Long id) {

        return roomRepository.findAllByFlat_Id(id);
    }


    public void save(Room room) {
        roomRepository.save(room);
    }


    public void delete(Long id){
        roomRepository.deleteById(id);
    }
}
