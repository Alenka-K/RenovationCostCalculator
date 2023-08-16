package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.Wall;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.DoorService;
import com.example.renovationcostcalculator.services.RoomService;
import com.example.renovationcostcalculator.services.WallService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("walls")
public class WallController {

    private final RoomService roomService;
    private final WallService wallService;

    public WallController(RoomService roomService, WallService wallService) {
        this.roomService = roomService;
        this.wallService = wallService;
    }

    @GetMapping()
    public String viewAllWalls(Model model){
        model.addAttribute("walls", wallService.findAll());
        return "walls/viewAllWalls";
    }

    @RequestMapping("/addWall/{id}")
    public String addWall(@PathVariable("id") Long id, Model model){
        Wall wall= new Wall();
            Room room = roomService.findById(id);
            wall.setRoom(roomService.findById(id));
            List<Wall> list = room.getWalls();
            if(list.isEmpty()|| !list.contains(wall)){
                list.add(wall);
                roomService.findById(id).setWalls(list);

            }

        model.addAttribute("wall", wall);
        return "walls/addWall";
    }

    @RequestMapping("saveWall")
    public String saveWall(@ModelAttribute("wall") Wall wall, RedirectAttributes redirectAttributes){
        wallService.save(wall);
        Long flatId = wall.getRoom().getFlat().getId();
        redirectAttributes.addAttribute("flatId", flatId);
        return "redirect:/flats/viewFlat/{flatId}";
    }

    @RequestMapping("/editWall/{id}")
    public String editWall(@PathVariable("id") Long id, Model model) {
        model.addAttribute("wall", wallService.findById(id));
        model.addAttribute("room", wallService.findById(id).getRoom());
        return "walls/editWall";
    }


    @RequestMapping("/updateWall/{id}")
    public String updateWall(@PathVariable("id") Long id, @ModelAttribute("wall") Wall wall, Long roomId, RedirectAttributes redirectAttributes) {
        wall.setRoom(roomService.findById(roomId));
        wallService.update(id, wall);
        redirectAttributes.addAttribute("flatId", roomService.findById(roomId).getFlat().getId());

        return "redirect:/flats/viewFlat/{flatId}";
    }
}
