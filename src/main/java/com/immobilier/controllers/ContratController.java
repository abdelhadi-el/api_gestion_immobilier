package com.immobilier.controllers;

import java.net.URI;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.immobilier.entities.Contrat;
import com.immobilier.services.interfaces.ContratServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contrat") // remplir la
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class ContratController {

	@Autowired
	ContratServices contratService ;

	@GetMapping("/getAll")
	public ResponseEntity<ArrayList<Contrat>> getContrats() {
		return ResponseEntity.ok().body(contratService.getAll());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Contrat> saveContrat(@RequestBody Contrat contrat ) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/contrat/save").toUriString()) ;
		return ResponseEntity.created(uri).body(contratService.save(contrat));
	}
	
	@GetMapping("/contratById")
	public ResponseEntity<Contrat> getContrat(@RequestParam Integer id ) { // look at the annotations if it's valid
		if (id != null) {
			return ResponseEntity.ok().body(contratService.get(id));
		}else {
			return null ;
		}
	}
	
	@PutMapping("/updateContrat")
	public ResponseEntity<Boolean> updateContrat(@RequestParam Integer id,@RequestBody Contrat newContrat  ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(contratService.update(id, newContrat));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> deleteContrat(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(contratService.delete(id));
	}
}
