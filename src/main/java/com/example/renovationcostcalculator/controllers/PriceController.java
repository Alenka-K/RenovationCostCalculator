package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.services.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
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

    @PostMapping
    public String savePrice(@ModelAttribute("price") Price price){
        priceService.save(price);
        return "redirect:/prices";
    }

    // не доработан
    @RequestMapping("/editPrice/{typeOfConstructionWork}")
    public String editPrice(@PathVariable("typeOfConstructionWork") String typeOfConstructionWork, Model model) {
        model.addAttribute("price", priceService.findByTypeOfConstructionWork(typeOfConstructionWork));
        return "flats/editFlat";
    }

    // не доработан
    @RequestMapping("/updatePrice/{id}")
    public String updatePrice(@PathVariable("id") String typeOfConstructionWork, @ModelAttribute("price") Price price) {
        priceService.update(typeOfConstructionWork, price);
        return "redirect:/prices";
    }

    @RequestMapping("/deletePrice/{id}")
    public String deletePrice(@PathVariable("id") String typeOfConstructionWork) {
        priceService.delete(typeOfConstructionWork);
        return "redirect:/prices";
    }
}
