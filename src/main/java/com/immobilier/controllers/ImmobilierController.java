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

import com.immobilier.entities.Immobilier;
import com.immobilier.services.interfaces.ImmobilierServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/immobilier") // remplir la
@RequiredArgsConstructor
public class ImmobilierController {

	@Autowired
	ImmobilierServices immobilierService ;

	@GetMapping("/getAll")
	public ResponseEntity<List<Immobilier>> getAllImmobiliers() {
		return ResponseEntity.ok().body(immobilierService.getAll());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Immobilier> saveImmobilier(@RequestBody Immobilier immobilier ) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/immobilier/save").toUriString()) ;
		return ResponseEntity.created(uri).body(immobilierService.save(immobilier));
	}
	
	@GetMapping("/userById")
	public ResponseEntity<Immobilier> getImmobilier(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(immobilierService.get(id));
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<Boolean> updateImmobilier(@RequestParam Integer id,@RequestBody Immobilier newImmobilier  ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(immobilierService.update(id, newImmobilier));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> updateImmobilier(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(immobilierService.delete(id));
	}
}
