package com.luv2code.ResturantSystem;

import com.luv2code.ResturantSystem.entity.User;
import com.luv2code.ResturantSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class ResturantSystemApplication {
//	@Autowired
//	private UserRepository userRepository;

	public static void main(String[] args) {

		SpringApplication.run(ResturantSystemApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user = new User();
//		user.setUsername("Nour");
//		user.setPassword("123456");
//		user.setEmail("nourfade@gmail.com");
//		user.setCreated_at(LocalDateTime.now());
//		user.setUpdated_at(LocalDateTime.now());
//
//		userRepository.save(user);
//		System.out.println("Users: " + userRepository.findAll());
//
//	}
}
