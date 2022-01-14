package com.immobilier.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.immobilier.entities.Annonce;
import com.immobilier.entities.Utilisateur;
import com.immobilier.services.interfaces.AnnonceServices;
import com.immobilier.services.interfaces.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userRole") // remplir la
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class UserRoleController {

	@Autowired
	AnnonceServices annonceServices ;
	@Autowired
	UserServices userServices ;
	
	@GetMapping("/annoncesByIdUser")		// public end_point ==> for "USER" AND "ADMIN Role // get annonce relative to poster
	public ResponseEntity<List<Annonce>> getAnnonce(@RequestParam Integer id ) {
		List<Annonce> annonces = annonceServices.findByIdUser(id) ;
		if (annonces != null) {
			return ResponseEntity.ok().body(annonces);
		}else {
			return null ;
		}
	}
	
	@GetMapping("/reserveAnnonce")		// public end_point ==> for "USER" AND "ADMIN Role
	public ResponseEntity<Boolean> reserveAnnonce(@RequestParam Integer id ) {
		//getting user connected
		Utilisateur authenticatedUser = null ;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    authenticatedUser =	userServices.get(currentUserName) ;
		}		
		if (authenticatedUser != null) {
			Annonce annonce = annonceServices.get(id) ;
			annonce.setEtat_reservation("réservé") ;
			annonce.setUser_reserve(authenticatedUser) ;
			annonceServices.save(annonce) ;
			return ResponseEntity.ok().body(true);
		}else {
			return ((BodyBuilder) ResponseEntity.notFound()).body(false);

		}
	}
}
