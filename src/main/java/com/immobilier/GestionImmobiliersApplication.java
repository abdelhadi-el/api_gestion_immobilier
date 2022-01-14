package com.immobilier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.immobilier.entities.Utilisateur;
import com.immobilier.services.interfaces.UserServices;

@SpringBootApplication
public class GestionImmobiliersApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionImmobiliersApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder() ;
	}
	
	@Bean
	CommandLineRunner run(UserServices userService) {
		return args -> {
			userService.save(new Utilisateur(1, "abdelhadi", "meaill", "passHash", "USER", "063526626", "Maroc KHouribga")) ;
			userService.save(new Utilisateur(2, "aaaa", "email", "1234", "ADMIN", "063526626", "Maroc KHouribga")) ;

		} ;
	}
}
