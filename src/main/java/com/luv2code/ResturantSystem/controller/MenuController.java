package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.Menu;
import com.luv2code.ResturantSystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    @PostMapping("/addMenu")
    public Menu addMenu(@RequestBody Menu newMenu){
        return menuService.addMenu(newMenu);
    }

    @GetMapping
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }


    // get menu for each restaurant by id
    @GetMapping("/resturant/{id}")
    public List<Menu> getResturantMenus(@PathVariable int id){
        return menuService.findMenuByResturnantId(id);
    }


}
