package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.price.AdditionalWork;
import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.AdditionalWorkService;
import com.example.renovationcostcalculator.services.PriceService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/additionalWorks")
public class AdditionalWorkController {

    private final AdditionalWorkService additionalWorkService;
    private final PriceService priceService;
    private final RoomService roomService;

    public AdditionalWorkController(AdditionalWorkService additionalWorkService, PriceService priceService, RoomService roomService) {
        this.additionalWorkService = additionalWorkService;
        this.priceService = priceService;
        this.roomService = roomService;
    }


    @GetMapping()
    public String getAdditionalWorks(Model model){
        model.addAttribute("listOfAdditionalWorks", additionalWorkService.findAll());
        return "additionalWorks/viewAllAdditionalWorks";
    }

    @GetMapping("/listOfAdditionalWorks/{id}")
    public String getAdditionalWorksByRoom(@PathVariable("id") Long id, Model model){
        model.addAttribute("listOfAdditionalWorks", additionalWorkService.findAllByRoom(id));
        return "additionalWorks/viewAllAdditionalWorks";
    }


    @GetMapping("/addAdditionalWorks/{id}")
    public String addListOfAdditionalWorks(@PathVariable("id") Long id, Model model){

        model.addAttribute("prices", priceService.findAll());
        model.addAttribute("room", roomService.findById(id));
        model.addAttribute("additionalWorkList", roomService.findById(id).getAdditionalWorks());

        return "additionalWorks/addAdditionalWorks";
    }

    @Transactional
    @RequestMapping("/saveAdditionalWorks")
    public String saveListOfAdditionalWorks(@RequestParam(value = "priceIds", required = false) String[] priceIds,
                                            @RequestParam(value = "valuePr", required = false) String[] valuePr,
                               @RequestParam(value = "id") Long id,
                               RedirectAttributes redirectAttributes){
        List<String> list = Arrays.asList(valuePr).stream().filter(item -> !item.equals("")).collect(Collectors.toList());
        Room room = roomService.findById(id);
        if(!room.getAdditionalWorks().isEmpty()) {
            for (AdditionalWork additionalWork:room.getAdditionalWorks()) {
                additionalWorkService.delete(additionalWork);
            }
        }
        List<AdditionalWork> additionalWorks = new ArrayList<>();

        if (priceIds != null) {

            for (int i = 0; i <= priceIds.length - 1; i++) {

                AdditionalWork additionalWork = new AdditionalWork();
                Double valueForCalculate = 0.0;
                Price price = priceService.findByType(priceIds[i]);
                valueForCalculate = Double.parseDouble(list.get(i));
                additionalWork.setValueForCalculation(valueForCalculate);
                additionalWork.setPrice(price);
                additionalWork.setRoom(roomService.findById(id));
                additionalWorkService.save(additionalWork);
                additionalWorks.add(additionalWork);

            }
        }
        roomService.findById(id).setAdditionalWorks(additionalWorks);
        System.out.println(roomService.findById(id).getAdditionalWorks());
        redirectAttributes.addAttribute("flatId", room.getFlat().getId());
        return "redirect:/flats/viewFlat/{flatId}";
    }






}
