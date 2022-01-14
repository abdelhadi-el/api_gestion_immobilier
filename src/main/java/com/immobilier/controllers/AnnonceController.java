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

import com.immobilier.entities.Annonce;
import com.immobilier.services.interfaces.AnnonceServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/annonce") // remplir la
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AnnonceController {

	@Autowired
	AnnonceServices annonceService ;

	@GetMapping("/getAll")    // public end_point ==> for "USER" AND "ADMIN" Role
	public ResponseEntity<ArrayList<Annonce>> getAnnonces() {
		return ResponseEntity.ok().body(annonceService.getAll());
	}
	
	@PostMapping("/save")	// public end_point ==> for "USER" AND "ADMIN Role
	public ResponseEntity<Annonce> saveAnnonce(@RequestBody Annonce annonce) {
		
//		ImageUploadController imgLogic = new ImageUploadController() ;
//		try {
//			imgLogic.uplaodImage(image, annonce.getId_annonce()) ;
//		} catch (IOException e) {
//			System.out.println("Facing problemes while uploading");
//			e.printStackTrace();
//		}
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/annonce/save").toUriString()) ;
		return ResponseEntity.created(uri).body(annonceService.save(annonce));
	}
	
	@GetMapping("/annonceById")		// public end_point ==> for "USER" AND "ADMIN Role
	public ResponseEntity<Annonce> getAnnonce(@RequestParam Integer id ) { // look at the annotations if it's valid
//		HashMap<String, Object> resultBody = new HashMap<>() ;
//		ImageUploadController imgLogic = new ImageUploadController() ;
//		try {
		//	Annonce annonce = annonceService.get(id) ;
//			resultBody.put("annonceObject", annonce) ;
//			resultBody.put("image_annonce", imgLogic.getImage(annonce.getId_annonce())) ;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (id != null) {
			return ResponseEntity.ok().body(annonceService.get(id));
		}else {
			return null ;
		}
	}
	
	@PutMapping("/updateAnnonce") // public end_point ==> for "USER" that posted the annonce AND "ADMIN Role
	public ResponseEntity<Boolean> updateAnnonce(@RequestParam Integer id,@RequestBody Annonce newAnnonce  ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(annonceService.update(id, newAnnonce));
	}
	
	@DeleteMapping("/delete")	// public end_point ==> for "USER" that posted the annonce AND "ADMIN Role
	public ResponseEntity<Boolean> deleteAnnonce(@RequestParam Integer id ) { // look at the annotations if it's valid
		return ResponseEntity.ok().body(annonceService.delete(id));
	}
}
