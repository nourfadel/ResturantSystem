package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.Menu;
import com.luv2code.ResturantSystem.entity.Resturant;
import com.luv2code.ResturantSystem.repository.MenuRepository;
import com.luv2code.ResturantSystem.repository.ResturantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ResturantRepository resturantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, ResturantRepository resturantRepository) {
        this.menuRepository = menuRepository;
        this.resturantRepository = resturantRepository;
    }

    public Menu addMenu(Menu menu){

        Resturant existingResturant = resturantRepository.findById(menu.getResturant().getId())
                .orElseThrow(() -> new RuntimeException("Resturant not found"));

        menu.setResturant(existingResturant);
        menu.setUpdated_at(LocalDateTime.now());
        menu.setCreated_at(LocalDateTime.now());

        Menu savedMenu = menuRepository.save(menu);
        savedMenu.setResturant(existingResturant);

        return savedMenu;
    }

    public List<Menu> getAllMenus(){
        return menuRepository.findAll();
    }

    public List<Menu> findMenuByResturnantId(int id){
        return menuRepository.findMenuByResturantId(id);
    }

}
