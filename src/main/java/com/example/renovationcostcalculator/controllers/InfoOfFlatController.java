package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.InfoOfFlat;
import com.example.renovationcostcalculator.model.utils.ViewInfoOfFlat;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.InfoOfFlatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;



@Controller
@RequestMapping
public class InfoOfFlatController {

    private final InfoOfFlatService infoOfFlatService;

    private final FlatService flatService;

    public InfoOfFlatController(InfoOfFlatService infoOfFlatService, FlatService flatService) {
        this.infoOfFlatService = infoOfFlatService;
        this.flatService = flatService;
    }

    @GetMapping("/saveInfoOfFlat/{id}")
    public String saveInfoOfFlat(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        InfoOfFlat info =  new InfoOfFlat();
        info.setLocalDateTime(LocalDateTime.now());
        info.setFlat(flatService.findById(id));
        info.setInfo(ViewInfoOfFlat.infoOfFlatToString(flatService.findById(id)));
        infoOfFlatService.save(info);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/flats/viewFlat/{id}";
    }

    @GetMapping("/viewInfoOfFlat/{id}")
    public String viewInfoOfFlat(@PathVariable("id") Long id, Model model) {
        model.addAttribute("listInfoOfFlat", infoOfFlatService.findAllByFlatId(id));
        model.addAttribute("flat", flatService.findById(id));
        return "viewInfoOfFlat";
    }

    @GetMapping("/viewInfo/{id}")
    public String viewInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flat", flatService.findById(id));
        model.addAttribute("time", ViewInfoOfFlat.formatOfLocalDateTime(LocalDateTime.now()));
        model.addAttribute("info", ViewInfoOfFlat.infoOfFlatToString(flatService.findById(id)));

        return "viewInfo";
    }


}
