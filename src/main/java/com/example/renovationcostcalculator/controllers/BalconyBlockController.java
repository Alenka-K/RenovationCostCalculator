package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.BalconyBlock;
import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.BalconyBlockService;
import com.example.renovationcostcalculator.services.RoomService;
import com.example.renovationcostcalculator.services.RoomWindowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/balconyBlocks")
public class BalconyBlockController {

    private final BalconyBlockService balconyBlockService;
    private final RoomService roomService;


    public BalconyBlockController(BalconyBlockService balconyBlockService, RoomService roomService) {
        this.balconyBlockService = balconyBlockService;
        this.roomService = roomService;
    }

    @GetMapping()
    public String viewAllBalconyBlocks(Model model){
        model.addAttribute("balconyBlocks", balconyBlockService.findAll());
        return "balconyBlocks/viewAllBalconyBlocks";
    }

    @RequestMapping("/addBalconyBlock/{id}")
    public String addBalconyBlock(@PathVariable("id") Long id, Model model){
        BalconyBlock balconyBlock = new BalconyBlock();
        Room room = roomService.findById(id);
            balconyBlock.setRoom(roomService.findById(id));
            List<BalconyBlock> list = room.getBalconyBlocks();
                list.add(balconyBlock);
                roomService.findById(id).setBalconyBlocks(list);

        model.addAttribute("balconyBlock", balconyBlock);

        return "balconyBlocks/addBalconyBlock";
    }

    @RequestMapping("saveBalconyBlock")
    public String saveBalconyBlock(@ModelAttribute("balconyBlock") BalconyBlock balconyBlock, RedirectAttributes redirectAttributes){
        balconyBlockService.save(balconyBlock);
        Long flatId = balconyBlock.getRoom().getFlat().getId();
        redirectAttributes.addAttribute("flatId", flatId);
        return "redirect:/flats/viewFlat/{flatId}";
    }

    @RequestMapping("/deleteBalconyBlock/{id}")
    public String deleteBalconyBlock(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Long flatId = balconyBlockService.findById(id).getRoom().getFlat().getId();
        balconyBlockService.delete(id);
        redirectAttributes.addAttribute("flatId", flatId);
        return "redirect:/flats/viewFlat/{flatId}";
    }

}
