package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final FlatService flatService;


    public RoomController(RoomService roomService, FlatService flatService) {
        this.roomService = roomService;
        this.flatService = flatService;
    }

    @GetMapping()
    public String viewAllRooms(Model model){
        model.addAttribute("rooms", roomService.findAll());
        return "rooms/viewAllRooms";
    }

    @RequestMapping("/addRectangleRoom/{id}")
    public String addRectangleRoom(@PathVariable("id") Long id, Model model){
        Room room = new RectangleRoom();
        room.setFlat(flatService.findById(id));
        model.addAttribute("room", room);
        return "rooms/addRectangleRoom";
    }

    @RequestMapping("saveRectangleRoom")
    public String saveRectangleRoom(@ModelAttribute("room") RectangleRoom rectangleRoom){
        roomService.save(rectangleRoom);
        return "redirect:/flats";
    }

    @Transactional
    @RequestMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        System.out.println(id);
        System.out.println(roomService.findById(id));
        roomService.delete(id);
        System.out.println(roomService.findAll());
        return "redirect:/flats";
    }

    @RequestMapping("/addL_shapedRoom/{id}")
    public String addL_shapedRoom(@PathVariable("id") Long id, Model model){
        Room room = new L_shapedRoom();
        room.setFlat(flatService.findById(id));
        model.addAttribute("room", room);
        return "rooms/addL_shapedRoom";
    }

    @RequestMapping("saveL_shapedRoom")
    public String saveL_shapedRoom(@ModelAttribute("room") L_shapedRoom l_shapedRoom){
        roomService.save(l_shapedRoom);
        return "redirect:/flats";
    }


}
