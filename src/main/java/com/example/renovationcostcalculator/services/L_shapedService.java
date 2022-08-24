package com.example.renovationcostcalculator.services;



import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.repositories.L_shapedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class L_shapedService {

    private final L_shapedRepository l_shapedRepository;


    public L_shapedService(L_shapedRepository l_shapedRepository) {
        this.l_shapedRepository = l_shapedRepository;
    }

    public List<L_shapedRoom> findAll() {
        return l_shapedRepository.findAll();
    }

    public L_shapedRoom findById(Long id){
        Optional<L_shapedRoom> lShapedRoom = l_shapedRepository.findById(id);
        return lShapedRoom.orElse(null);
    }


    public void save(L_shapedRoom lShapedRoom) {
        l_shapedRepository.save(lShapedRoom);
    }

    public void update(Long id, L_shapedRoom updateL_shapedRoom){
        updateL_shapedRoom.setId(id);
        l_shapedRepository.save(updateL_shapedRoom);
    }

    public void delete(Long id){
        l_shapedRepository.deleteById(id);
    }
}
