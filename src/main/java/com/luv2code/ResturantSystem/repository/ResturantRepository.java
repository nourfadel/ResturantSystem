package com.luv2code.ResturantSystem.repository;

import com.luv2code.ResturantSystem.entity.Resturant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResturantRepository extends JpaRepository<Resturant,Integer> {
}
