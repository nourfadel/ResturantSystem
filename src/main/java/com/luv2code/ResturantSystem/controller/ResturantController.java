package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.Resturant;
import com.luv2code.ResturantSystem.service.ResturantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resturant")
public class ResturantController {

    private final ResturantService resturantService;

    @Autowired
    public ResturantController(ResturantService resturantService) {
        this.resturantService = resturantService;
    }


    @PostMapping("/addRestruant")
    public Resturant addResturant(@RequestBody Resturant newResturant){
        return resturantService.addResturant(newResturant);
    }

    @GetMapping
    public List<Resturant> getAllResturants(){
        return resturantService.getAllResturants();
    }

    @GetMapping("/{id}")
    public Resturant getResturantById(@PathVariable int id){
        return resturantService.findResturantById(id)
                .orElseThrow(() -> new RuntimeException("Resturant not found!"));

    }

}
