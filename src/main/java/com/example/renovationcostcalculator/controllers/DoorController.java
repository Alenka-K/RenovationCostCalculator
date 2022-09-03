package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.DoorService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("doors")
public class DoorController {

    private final RoomService roomService;
    private final DoorService doorService;

    public DoorController(RoomService roomService, DoorService doorService) {
        this.roomService = roomService;
        this.doorService = doorService;
    }

    @GetMapping()
    public String viewAllDoors(Model model){
        model.addAttribute("doors", doorService.findAll());
        return "doors/viewAllDoors";
    }

    @RequestMapping("/addDoor/{id}/{form}")
    public String addDoor(@PathVariable("id") Long id, @PathVariable("form") String form, Model model){
        Door door = new Door();
            Room room = roomService.findById(id);
            door.setRoom(roomService.findById(id));
            List<Door> list = room.getDoors();
            if(list.isEmpty()|| !list.contains(door)){
                list.add(door);
                roomService.findById(id).setDoors(list);
                System.out.println(door);
                System.out.println(roomService.findById(id));
            }

        model.addAttribute("door", door);
        return "doors/addDoor";
    }

    @RequestMapping("saveDoor")
    public String saveDoor(@ModelAttribute("door") Door door){
        doorService.save(door);
        System.out.println(door);
        return "redirect:/flats";
    }
}
