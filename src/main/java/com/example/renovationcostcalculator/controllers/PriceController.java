package com.example.renovationcostcalculator.controllers;


import com.example.renovationcostcalculator.model.price.Price;
import com.example.renovationcostcalculator.model.room.Room;
import com.example.renovationcostcalculator.model.utils.PriceComparator;
import com.example.renovationcostcalculator.services.PriceService;
import com.example.renovationcostcalculator.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


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



    @GetMapping("/downloadPrices")
    public String downloadPrices(Model model){
        model.addAttribute("prices", priceService.findAll());
        return "prices/viewAllPrices";
    }

    @RequestMapping("/changePrices")
    public String changePrices() {
        return "prices/changePrices";
    }

    @RequestMapping("/saveChangePrices")
    public String saveChangePrices(@RequestParam("percent") int percent) {
        Set<Price> priceSet = priceService.findAll();
        for (Price price: priceSet){
            int temp = price.getAmount();
            temp = temp + (temp * percent)/100;
            price.setAmount(temp);
            priceService.update(price.getType(),price);
        }
        return "redirect:/prices";
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
    public String savePriceSet(@RequestParam(value = "priceIds", required = false) String[] priceIds,
                               @RequestParam(value = "id") Long id,
                               RedirectAttributes redirectAttributes){
        Room room = roomService.findById(id);
        if(!room.getPriceSet().isEmpty()) {
            for (Price price : room.getPriceSet()) {
                price.getRooms().remove(room);
            }
        }
        Set<Price> priceSet = new HashSet<>();
        if (priceIds != null){
            for(String price1: priceIds){
                Price price = priceService.findByType(price1);
                priceSet.add(price);
                priceService.findByType(price1).getRooms().add(roomService.findById(id));
            }
        }

        roomService.findById(id).setPriceSet(priceSet);
        redirectAttributes.addAttribute("flatId", room.getFlat().getId());
        return "redirect:/flats/viewFlat/{flatId}";
    }


    @GetMapping("/editPriceSet/{id}")
    public String editPriceSet(@PathVariable("id") Long id, Model model){
        Set<Price> priceSetRoom = new TreeSet<>(new PriceComparator());
        priceSetRoom.addAll(roomService.findById(id).getPriceSet());
        Set<Price> priceSetAbsent = new TreeSet<>(new PriceComparator());
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
        return "prices/editPrice";
    }

    // не доработан
    @RequestMapping("/updatePrice/{type}")
    public String updatePrice(@PathVariable("type") String type, @ModelAttribute("price") Price price) {
        priceService.update(type, price);
        return "redirect:/prices";
    }

    @RequestMapping("/deletePrice/{id}")
    public String deletePrice(@PathVariable("id") String type) {
        priceService.delete(type);
        return "redirect:/prices";
    }
}
