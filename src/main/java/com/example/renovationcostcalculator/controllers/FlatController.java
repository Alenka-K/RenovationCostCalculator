package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.Flat;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.FlatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/flats")
public class FlatController  {

    private final FlatService flatService;



    public FlatController(FlatService flatService) {
        this.flatService = flatService;

    }

    @GetMapping()
    public String getFlats(Model model){
        model.addAttribute("flats", flatService.findAll());
        return "flats/viewAllFlats";
    }

    @GetMapping("viewFlat/{id}")
    public String viewFlat(@PathVariable("id") Long id, Model model){
        model.addAttribute("flat", flatService.findById(id));
        return "flats/viewFlat";
    }

    @GetMapping("/addFlat")
    public String addFlat(Model model){
        model.addAttribute("flat", new Flat());
        return "flats/addFlat";
    }

    @PostMapping
    public String saveFlat(@ModelAttribute("flat") Flat flat){
        flatService.save(flat);
        return "redirect:/flats";
    }

    // не доработан
    @RequestMapping("/editFlat/{id}")
    public String editFlat(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flat", flatService.findById(id));
        return "flats/editFlat";
    }

    // не доработан
    @RequestMapping("/updateFlat/{id}")
    public String updateFlat(@PathVariable("id") Long id, @ModelAttribute("flat") Flat flat) {
        flatService.update(id, flat);
        return "redirect:/flats";
    }

    @RequestMapping("/deleteFlat/{id}")
    public String deleteFlat(@PathVariable("id") Long id) {
        flatService.delete(id);
        return "redirect:/flats";
    }
    @RequestMapping("/calculateFlat/{id}")
    public String calculateFlat(@PathVariable("id") Long id) {
        Flat flat = flatService.findById(id);
        for (Room room : flat.getRooms()) {

        }
        return "redirect:/flats";
    }

}
