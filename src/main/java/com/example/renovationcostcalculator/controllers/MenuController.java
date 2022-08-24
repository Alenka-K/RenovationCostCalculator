package com.example.renovationcostcalculator.controllers;

import com.example.renovationcostcalculator.services.FlatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MenuController {

    private final FlatService flatService;

    public MenuController(FlatService flatService) {
        this.flatService = flatService;
    }

    @GetMapping("/")
    public String viewMenu() {
        return "index";
    }
}

