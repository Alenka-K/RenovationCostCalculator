package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.services.DoorService;
import com.example.renovationcostcalculator.services.L_shapedService;
import com.example.renovationcostcalculator.services.RectangleService;
import com.example.renovationcostcalculator.services.RoomWindowService;
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

    private final RectangleService rectangleService;
    private final L_shapedService l_shapedService;
    private final DoorService doorService;

    public DoorController(RectangleService rectangleService, L_shapedService l_shapedService, DoorService doorService) {
        this.doorService = doorService;
        this.rectangleService = rectangleService;
        this.l_shapedService = l_shapedService;
    }

    @GetMapping()
    public String viewAllDoors(Model model){
        model.addAttribute("doors", doorService.findAll());
        return "doors/viewAllDoors";
    }

    @RequestMapping("/addDoor/{id}/{form}")
    public String addDoor(@PathVariable("id") Long id, @PathVariable("form") String form, Model model){
        Door door = new Door();
        if (form.equals("Rectangle")){
            RectangleRoom room = rectangleService.findById(id);
            door.setRectangleRoom(rectangleService.findById(id));
            List<Door> list = room.getDoors();
            if(list.isEmpty()|| !list.contains(door)){
                list.add(door);
                rectangleService.findById(id).setDoors(list);
                System.out.println(door);
                System.out.println(rectangleService.findById(id));
            }

        }
        if (form.equals("L_shaped")){
            L_shapedRoom room = l_shapedService.findById(id);
            door.setL_shapedRoom(l_shapedService.findById(id));
            List<Door> list = room.getDoors();
            if(list.isEmpty()|| !list.contains(door)){
                list.add(door);
                l_shapedService.findById(id).setDoors(list);
            }
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
