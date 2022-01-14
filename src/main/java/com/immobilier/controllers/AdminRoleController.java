package com.immobilier.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.immobilier.entities.Annonce;
import com.immobilier.services.interfaces.AnnonceServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/adminRole") // remplir la
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AdminRoleController {
	
	@Autowired
	AnnonceServices annonceServices ;
	
	@GetMapping("/validateAnnonce")		// end_point ==> "ADMIN Role
	public ResponseEntity<Boolean> validateAnnonce(@RequestParam Integer id ) {
	
			Annonce annonce = annonceServices.get(id) ;
			if (annonce != null) {
				annonce.setEtat_validation("validé") ;
				annonceServices.save(annonce) ;
				return ResponseEntity.ok().body(true);
			}else {
				return ((BodyBuilder) ResponseEntity.notFound()).body(false);
			}
		
	}
}
