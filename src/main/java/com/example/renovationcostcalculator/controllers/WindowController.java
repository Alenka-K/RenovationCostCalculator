package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.RoomService;
import com.example.renovationcostcalculator.services.RoomWindowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/roomWindows")
public class WindowController {

    private final RoomWindowService roomWindowService;
    private final RoomService roomService;


    public WindowController(RoomWindowService roomWindowService, RoomService roomService) {
        this.roomWindowService = roomWindowService;
        this.roomService = roomService;
    }

    @GetMapping()
    public String viewAllWindow(Model model){
        model.addAttribute("roomWindows", roomWindowService.findAll());
        return "roomWindows/viewAllWindow";
    }

    @RequestMapping("/addWindow/{id}")
    public String addWindow(@PathVariable("id") Long id, Model model){
        RoomWindow roomWindow = new RoomWindow();
           Room room = roomService.findById(id);
            roomWindow.setRoom(roomService.findById(id));
            List<RoomWindow> list = room.getRoomWindows();
                list.add(roomWindow);
                roomService.findById(id).setRoomWindows(list);

        model.addAttribute("roomWindow", roomWindow);
        return "roomWindows/addWindow";
    }

    @RequestMapping("saveWindow")
    public String saveWindow(@ModelAttribute("roomWindow") RoomWindow roomWindow){
        roomWindowService.save(roomWindow);
        return "redirect:/flats";
    }

}
