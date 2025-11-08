package com.luv2code.ResturantSystem.repository;

import com.luv2code.ResturantSystem.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    List<Menu> findMenuByResturantId(int id);
}
