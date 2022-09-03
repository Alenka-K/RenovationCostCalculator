package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(room);
        model.addAttribute("room", room);
        return "rooms/addRectangleRoom";
    }

    @RequestMapping("saveRectangleRoom")
    public String saveRectangleRoom(@ModelAttribute("room") RectangleRoom rectangleRoom){
        roomService.save(rectangleRoom);
        System.out.println(rectangleRoom);
        return "redirect:/flats";
    }

    @RequestMapping("/deleteRectangleRoom/{id}")
    public String deleteRectangleRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
        return "redirect:/rectangleRooms";
    }

    @RequestMapping("/addL_sapedRoom/{id}")
    public String addL_shapedRoom(@PathVariable("id") Long id, Model model){
        Room room = new L_shapedRoom();
        room.setFlat(flatService.findById(id));
        System.out.println(room);
        model.addAttribute("room", room);
        return "rooms/addL_shapedRoom";
    }

    @RequestMapping("saveL_shapedRoom")
    public String saveL_shapedRoom(@ModelAttribute("room") L_shapedRoom l_shapedRoom){
        roomService.save(l_shapedRoom);
        System.out.println(l_shapedRoom);
        return "redirect:/flats";
    }

}
