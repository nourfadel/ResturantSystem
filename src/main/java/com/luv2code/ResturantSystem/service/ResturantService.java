package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.Resturant;
import com.luv2code.ResturantSystem.repository.ResturantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResturantService {

    private ResturantRepository resturantRepository;

    @Autowired
    public ResturantService(ResturantRepository resturantRepository) {
        this.resturantRepository = resturantRepository;
    }

    public Resturant addResturant(Resturant resturant){
        resturant.setCreated_at(LocalDateTime.now());
        resturant.setUpdated_at(LocalDateTime.now());
        Resturant savedResturant = resturantRepository.save(resturant);

        return savedResturant;
    }

    public List<Resturant> getAllResturants(){
        return resturantRepository.findAll();
    }

    public Optional<Resturant> findResturantById(int id){
        return resturantRepository.findById(id);
    }

}
