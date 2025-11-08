package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.Menu;
import com.luv2code.ResturantSystem.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu addMenu(Menu menu){
        menu.setCreated_at(LocalDateTime.now());
        menu.setUpdated_at(LocalDateTime.now());
        Menu savedMenu = menuRepository.save(menu);

        return savedMenu;
    }

    public List<Menu> getAllMenus(){
        return menuRepository.findAll();
    }

    public List<Menu> findMenuByResturnantId(int id){
        return menuRepository.findMenuByResturantId(id);
    }

}
