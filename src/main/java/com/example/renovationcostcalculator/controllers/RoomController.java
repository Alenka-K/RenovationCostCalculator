package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.transaction.Transactional;

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
    public String saveRectangleRoom(@ModelAttribute("room") RectangleRoom rectangleRoom, RedirectAttributes redirectAttributes){
        roomService.save(rectangleRoom);
        redirectAttributes.addAttribute("id", rectangleRoom.getFlat().getId());
        return "redirect:/flats/viewFlat/{id}";
    }

    @Transactional
    @RequestMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", roomService.findById(id).getFlat().getId());
        roomService.delete(id);

        return "redirect:/flats/viewFlat/{id}";
    }

    @RequestMapping("/addL_shapedRoom/{id}")
    public String addL_shapedRoom(@PathVariable("id") Long id, Model model){
        Room room = new L_shapedRoom();
        room.setFlat(flatService.findById(id));
        model.addAttribute("room", room);
        return "rooms/addL_shapedRoom";
    }

    @RequestMapping("saveL_shapedRoom")
    public String saveL_shapedRoom(@ModelAttribute("room") L_shapedRoom l_shapedRoom, RedirectAttributes redirectAttributes){
        roomService.save(l_shapedRoom);
        redirectAttributes.addAttribute("id", l_shapedRoom.getFlat().getId());
        return "redirect:/flats/viewFlat/{id}";
    }


}
