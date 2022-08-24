package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.RectangleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rectangleRooms")
public class RectangleController {

    private final RectangleService rectangleService;
    private final FlatService flatService;


    public RectangleController(RectangleService rectangleService, FlatService flatService) {
        this.rectangleService = rectangleService;


        this.flatService = flatService;
    }

    @GetMapping()
    public String viewAllRectangleRooms(Model model){
        model.addAttribute("rectangleRooms", rectangleService.findAll());
        return "rectangleRooms/viewAllRectangleRooms";
    }

    @RequestMapping("/addRectangleRoom/{id}")
    public String addRectangleRoom(@PathVariable("id") Long id, Model model){
        RectangleRoom rectangleRoom = new RectangleRoom();
        rectangleRoom.setFlat(flatService.findById(id));
        System.out.println(rectangleRoom);
        model.addAttribute("room", rectangleRoom);
        return "rectangleRooms/addRectangleRoom";
    }

    @RequestMapping("saveRectangleRoom")
    public String saveRectangleRoom(@ModelAttribute("room") RectangleRoom rectangleRoom){
        rectangleService.save(rectangleRoom);
        System.out.println(rectangleRoom);
        return "redirect:/flats";
    }

    @RequestMapping("/deleteRectangleRoom/{id}")
    public String deleteRectangleRoom(@PathVariable("id") Long id) {
        rectangleService.delete(id);
        return "redirect:/rectangleRooms";
    }

}
