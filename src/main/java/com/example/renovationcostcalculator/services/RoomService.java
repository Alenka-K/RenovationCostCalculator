package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.Form;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.repositories.RoomRepository;
import org.hibernate.result.Output;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

    public List<Room> findAllByForm(Form form) {
        return roomRepository.findAllByForm(form);
    }

    public Room findById(Long id){

        return roomRepository.findById(id).orElse(null);
    }


    public List<Room> findAllByFlat_ID(Long id) {

        return roomRepository.findAllByFlat_Id(id);
    }

    @Transactional
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Transactional
    public void delete(Long id){
        roomRepository.deleteById(id);

    }
    @Transactional
    public void update(Long id, Room updateRoom){
        updateRoom.setId(id);
        roomRepository.save(updateRoom);
    }
}
