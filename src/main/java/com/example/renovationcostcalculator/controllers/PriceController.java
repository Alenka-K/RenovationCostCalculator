package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.PriceService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;
    private final RoomService roomService;

    public PriceController(PriceService priceService, RoomService roomService) {
        this.priceService = priceService;
        this.roomService = roomService;
    }


    @GetMapping()
    public String getPrices(Model model){
        model.addAttribute("prices", priceService.findAll());
        return "prices/viewAllPrices";
    }


    @GetMapping("/addPrice")
    public String addPrice(Model model){
        model.addAttribute("price", new Price());
        return "prices/addPrice";
    }

    @GetMapping("/addPriceSet/{id}")
    public String addPriceSet(@PathVariable("id") Long id, Model model){

        model.addAttribute("priceSet", priceService.findAll());
        model.addAttribute("room", roomService.findById(id));

        return "prices/addPriceSet";
    }

    @Transactional
    @RequestMapping("/savePriceSet")
    public String savePriceSet(@RequestParam(value = "priceIds", required = false) String[] priceIds, @RequestParam(value = "id")Long id){
        if(!roomService.findById(id).getPriceSet().isEmpty()) {
            for (Price price : roomService.findById(id).getPriceSet()) {
                price.getRooms().remove(roomService.findById(id));
            }
        }
        Set<Price> priceSet = new HashSet<>();
        for(String price1: priceIds){
            Price price = priceService.findByType(price1);
            priceSet.add(price);
            priceService.findByType(price1).getRooms().add(roomService.findById(id));
        }
        roomService.findById(id).setPriceSet(priceSet);

        return "redirect:/flats";
    }


    @GetMapping("/editPriceSet/{id}")
    public String editPriceSet(@PathVariable("id") Long id, Model model){
        Set<Price> priceSetRoom = roomService.findById(id).getPriceSet();
        Set<Price> priceSetAbsent = new HashSet<>();
        for (Price price: priceService.findAll()) {
            if(!priceSetRoom.contains(price)) priceSetAbsent.add(price);
        }
        model.addAttribute("priceSetAbsent", priceSetAbsent);
        model.addAttribute("priceSet", priceSetRoom);
        model.addAttribute("room", roomService.findById(id));

        return "prices/editPriceSet";
    }

    @PostMapping
    public String savePrice(@ModelAttribute("price") Price price){
        priceService.save(price);
        return "redirect:/prices";
    }

    // не доработан
    @RequestMapping("/editPrice/{type}")
    public String editPrice(@PathVariable("type") String type, Model model) {
        model.addAttribute("price", priceService.findByType(type));
        return "flats/editFlat";
    }

    // не доработан
    @RequestMapping("/updatePrice/{id}")
    public String updatePrice(@PathVariable("id") String type, @ModelAttribute("price") Price price) {
        priceService.update(type, price);
        return "redirect:/prices";
    }

    @RequestMapping("/deletePrice/{id}")
    public String deletePrice(@PathVariable("id") String type) {
        priceService.delete(type);
        return "redirect:/prices";
    }
}
