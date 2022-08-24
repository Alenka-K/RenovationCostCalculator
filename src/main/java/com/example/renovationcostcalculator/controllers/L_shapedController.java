package com.example.renovationcostcalculator.controllers;

import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.L_shapedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lShapedRooms")
public class L_shapedController {

    private final L_shapedService l_shapedService;
    private final FlatService flatService;



    public L_shapedController(L_shapedService l_shapedService, FlatService flatService) {
        this.l_shapedService = l_shapedService;

        this.flatService = flatService;
    }

    @GetMapping()
    public String viewAllL_shapedRooms(Model model){
        model.addAttribute("lShapedRooms", l_shapedService.findAll());
        return "lShapedRooms/viewAllL_shapedRooms";
    }

    @RequestMapping("/addL_shapedRoom/{id}")
    public String addL_shapedRoom(@PathVariable("id") Long id, Model model){
        L_shapedRoom lShapedRoom = new L_shapedRoom();
        lShapedRoom.setFlat(flatService.findById(id));
        model.addAttribute("room", lShapedRoom);
        return "lShapedRooms/addL_shapedRoom";
    }

    @RequestMapping("saveL_shapedRoom")
    public String saveL_shapedRoom(@ModelAttribute("lShapedRoom") L_shapedRoom lShapedRoom){
        l_shapedService.save(lShapedRoom);

        return "redirect:/flats";
    }

    @RequestMapping("/deleteL_shapedRoom/{id}")
    public String deleteL_shapedRoom(@PathVariable("id") Long id) {
        l_shapedService.delete(id);
        return "redirect:/flats";
    }
}
