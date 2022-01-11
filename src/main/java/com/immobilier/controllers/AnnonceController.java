package com.immobilier.controllers;

import java.net.URI;
import java.util.ArrayList;

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

import com.immobilier.entities.Annonce;
import com.immobilier.services.interfaces.AnnonceServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/annonce") // remplir la
@RequiredArgsConstructor
public class AnnonceController {

	@Autowired
	AnnonceServices annonceService ;

	@GetMapping("/getAll")
	public ResponseEntity<ArrayList<Annonce>> getAnnonces() {
		return ResponseEntity.ok().body(annonceService.getAll());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Annonce> saveAnnonce(@RequestBody Annonce annonce ) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/annonce/save").toUriString()) ;
		return ResponseEntity.created(uri).body(annonceService.save(annonce));
	}
	
	@GetMapping("/annonceById")
	public ResponseEntity<Annonce> getAnnonce(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(annonceService.get(id));
	}
	
	@PutMapping("/updateAnnonce")
	public ResponseEntity<Boolean> updateAnnonce(@RequestParam Integer id,@RequestBody Annonce newAnnonce  ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(annonceService.update(id, newAnnonce));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> deleteAnnonce(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(annonceService.delete(id));
	}
}
