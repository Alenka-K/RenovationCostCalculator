package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.room.FreeFormRoom;
import com.example.renovationcostcalculator.model.room.L_shapedRoom;
import com.example.renovationcostcalculator.model.room.RectangleRoom;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.services.FlatService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.transaction.Transactional;
import java.util.HashMap;

import static com.example.renovationcostcalculator.model.Form.*;


@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final FlatService flatService;


    public RoomController(RoomService roomService, FlatService flatService) {
        this.roomService = roomService;
        this.flatService = flatService;
    }

    @GetMapping()
    public String viewAllRooms(Model model){
        model.addAttribute("rooms", roomService.findAll());
        return "rooms/viewAllRooms";
    }

    @RequestMapping("/addRectangleRoom/{id}")
    public String addRectangleRoom(@PathVariable("id") Long id, Model model){
        RectangleRoom room = new RectangleRoom();
        room.setFlat(flatService.findById(id));
        model.addAttribute("room", room);
        return "rooms/addRectangleRoom";
    }

    @RequestMapping("saveRectangleRoom")
    public String saveRectangleRoom(@ModelAttribute("room") RectangleRoom rectangleRoom, RedirectAttributes redirectAttributes){
        roomService.save(rectangleRoom);
        redirectAttributes.addAttribute("id", rectangleRoom.getFlat().getId());
        return "redirect:/flats/viewFlat/{id}";
    }

    @RequestMapping("/addFreeFormRoom/{id}")
    public String addFreeFormRoomRoom(@PathVariable("id") Long id, Model model){
        FreeFormRoom room = new FreeFormRoom();
        room.setFlat(flatService.findById(id));
        model.addAttribute("room", room);
        return "rooms/addFreeFormRoom";
    }

    @RequestMapping("saveFreeFormRoom")
    public String saveFreeFormRoom(@ModelAttribute("room") FreeFormRoom freeFormRoom, String listOfWallLengths, RedirectAttributes redirectAttributes){
        roomService.save(freeFormRoom);
        System.out.println(listOfWallLengths);
        redirectAttributes.addAttribute("id", freeFormRoom.getFlat().getId());
        return "redirect:/flats/viewFlat/{id}";
    }

    @Transactional
    @RequestMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", roomService.findById(id).getFlat().getId());
        roomService.delete(id);

        return "redirect:/flats/viewFlat/{id}";
    }

    @RequestMapping("/addL_shapedRoom/{id}")
    public String addL_shapedRoom(@PathVariable("id") Long id, Model model){
        L_shapedRoom room = new L_shapedRoom();
        room.setFlat(flatService.findById(id));
        model.addAttribute("room", room);
        return "rooms/addL_shapedRoom";
    }

    @RequestMapping("saveL_shapedRoom")
    public String saveL_shapedRoom(@ModelAttribute("room") L_shapedRoom l_shapedRoom, RedirectAttributes redirectAttributes){
        roomService.save(l_shapedRoom);
        redirectAttributes.addAttribute("id", l_shapedRoom.getFlat().getId());
        return "redirect:/flats/viewFlat/{id}";
    }


    @RequestMapping("/editRoomDimensions/{id}")
    public String editRoomDimensions(@PathVariable("id") Long id, Model model){
        model.addAttribute("room", roomService.findById(id));


        return "rooms/editRoomDimensions";

    }

    @RequestMapping("/updateRoomDimensions")
    public String updateRoomDimensions(@RequestParam("id") Long id,
                                       @RequestParam("height") Double height,
                                       @RequestParam(required = false) Double length,
                                       @RequestParam(required = false) Double width,
                                       @RequestParam(required = false) Double lengthSmall,
                                       @RequestParam(required = false) Double widthSmall,
                                       @RequestParam(name = "widthOfCircumscribedRectangle", required = false) Double widthOfCircumscribedRectangle,
                                       @RequestParam(name = "lengthOfCircumscribedRectangle", required = false) Double lengthOfCircumscribedRectangle,
                                       @RequestParam(name = "listOfWallLengths", required = false) String listOfWallLengths,
                                       @RequestParam(name = "rectanglesToExtractFromCircumscribedRectangle", required = false) String rectanglesToExtractFromCircumscribedRectangle,
                                       RedirectAttributes redirectAttributes) {
        roomService.findById(id).setHeight(height);
        if(roomService.findById(id).getForm() == RECTANGLE){
             RectangleRoom rectangleRoom = (RectangleRoom) roomService.findById(id);
             rectangleRoom.setWidth(width);
             rectangleRoom.setLength(length);
             roomService.update(id,rectangleRoom);

        }
        if(roomService.findById(id).getForm() == L_SHAPED){
            L_shapedRoom l_shapedRoom = (L_shapedRoom) roomService.findById(id);
            l_shapedRoom.setWidth(width);
            l_shapedRoom.setLength(length);
            l_shapedRoom.setWidthSmall(widthSmall);
            l_shapedRoom.setLengthSmall(lengthSmall);
            roomService.update(id,l_shapedRoom);
        }
        if(roomService.findById(id).getForm() == FREE){
            FreeFormRoom freeFormRoom = (FreeFormRoom) roomService.findById(id);
            freeFormRoom.setWidthOfCircumscribedRectangle(widthOfCircumscribedRectangle);
            freeFormRoom.setLengthOfCircumscribedRectangle(lengthOfCircumscribedRectangle);
            freeFormRoom.setListOfWallLengths(listOfWallLengths);
            freeFormRoom.setRectanglesToExtractFromCircumscribedRectangle(rectanglesToExtractFromCircumscribedRectangle);
            roomService.update(id,freeFormRoom);
        }
        redirectAttributes.addAttribute("id", roomService.findById(id).getFlat().getId());
        return "redirect:/flats/viewFlat/{id}";
    }
}
