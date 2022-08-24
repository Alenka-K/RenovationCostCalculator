package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.RoomWindow;
import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.services.L_shapedService;
import com.example.renovationcostcalculator.services.RectangleService;
import com.example.renovationcostcalculator.services.RoomWindowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/roomWindows")
public class WindowController {

    private final RoomWindowService roomWindowService;
    private final RectangleService rectangleService;
    private final L_shapedService l_shapedService;

    public WindowController(RoomWindowService roomWindowService, RectangleService rectangleService, L_shapedService l_shapedService) {
        this.roomWindowService = roomWindowService;
        this.rectangleService = rectangleService;
        this.l_shapedService = l_shapedService;
    }

    @GetMapping()
    public String viewAllWindow(Model model){
        model.addAttribute("roomWindows", roomWindowService.findAll());
        return "roomWindows/viewAllWindow";
    }

    @RequestMapping("/addWindow/{id}/{form}")
    public String addWindow(@PathVariable("id") Long id, @PathVariable("form") String form, Model model){
        RoomWindow roomWindow = new RoomWindow();
        if (form.equals("Rectangle")){
            RectangleRoom room = rectangleService.findById(id);
            roomWindow.setRectangleRoom(rectangleService.findById(id));
            List<RoomWindow> list = room.getRoomWindows();
            if(list.isEmpty()|| !list.contains(roomWindow)){
                list.add(roomWindow);
                rectangleService.findById(id).setRoomWindows(list);
                System.out.println(roomWindow);
                System.out.println(rectangleService.findById(id));
            }

        }
        if (form.equals("L_shaped")){
            L_shapedRoom room = l_shapedService.findById(id);
            roomWindow.setL_shapedRoom(l_shapedService.findById(id));
            List<RoomWindow> list = room.getRoomWindows();
            if(list.isEmpty()|| !list.contains(roomWindow)){
                list.add(roomWindow);
                l_shapedService.findById(id).setRoomWindows(list);
            }
        }
        model.addAttribute("roomWindow", roomWindow);
        return "roomWindows/addWindow";
    }

    @RequestMapping("saveWindow")
    public String saveWindow(@ModelAttribute("roomWindow") RoomWindow roomWindow){
        roomWindowService.save(roomWindow);
        System.out.println(roomWindow);
        return "redirect:/flats";
    }

}
