package com.example.renovationcostcalculator.repositories;

import com.example.renovationcostcalculator.model.Form;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    public List<Room> findAllByFlat_Id(Long id);

    public List<Room> findAllByForm(Form form);

}
