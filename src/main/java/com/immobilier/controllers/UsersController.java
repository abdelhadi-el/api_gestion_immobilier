package com.immobilier.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.immobilier.entities.Utilisateur;
import com.immobilier.services.interfaces.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users") // remplir la
@RequiredArgsConstructor
public class UsersController {
	@Autowired
	UserServices userService ;

	@GetMapping("/getAll")
	public ResponseEntity<List<Utilisateur>> getUsers() {
		return ResponseEntity.ok().body(userService.getAll());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Utilisateur> saveUser(@RequestBody Utilisateur user ) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("users/save").toUriString()) ;
		return ResponseEntity.created(uri).body(userService.save(user));
	}
	
	@GetMapping("/userById")
	public ResponseEntity<Utilisateur> getUser(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(userService.get(id));
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<Boolean> updateUser(@RequestParam Integer id,@RequestBody Utilisateur newUser  ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(userService.update(id, newUser));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> updateUser(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(userService.delete(id));
	}
	
	
}
