package com.immobilier.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immobilier.entities.Annonce;
import com.immobilier.exceptions.ResourceNotFoundException;
import com.immobilier.repository.AnnonceRepository;
import com.immobilier.services.interfaces.AnnonceServices;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class AnnonceServicesImpl implements AnnonceServices{
	
	@Autowired
	AnnonceRepository annonceRepository ;
	
	@Override
	public Annonce save(Annonce annonce) {
		if (annonce != null) {		
			return annonceRepository.save(annonce) ;
		}else {
			return null ;
		}
	}

	@Override
	public Annonce get(Integer id_annonce) {
		Annonce optionalResult = annonceRepository.findById(id_annonce)
				.orElseThrow((() -> new ResourceNotFoundException("L'annonce avec l'id" + id_annonce + " n'existe pas"))) ;
		return optionalResult;
		
	}

	@Override
	public Boolean update(Integer id, Annonce newAnnonce) {
		Annonce optionalResult = annonceRepository.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("L'annonce avec l'id" + id + " n'existe pas"))) ;
	//	optionalResult.setId_annonce(newAnnonce.getId_annonce());
		optionalResult.setImmobilier(newAnnonce.getImmobilier());     // peut ajouter la du traitement pour que n'existe pas 
		optionalResult.setUser_post(newAnnonce.getUser_post());
		optionalResult.setUser_reserve(newAnnonce.getUser_reserve());
		optionalResult.setTitre(newAnnonce.getTitre());
		//optionalResult.setImage_annonce(newAnnonce.getImage_annonce());
		optionalResult.setType(newAnnonce.getType());
		optionalResult.setDate_annonce(newAnnonce.getDate_annonce());
		optionalResult.setEtat_validation(newAnnonce.getEtat_validation());
		optionalResult.setEtat_reservation(newAnnonce.getEtat_reservation());


		if (annonceRepository.save(optionalResult) != null) {
			return true ;
		}else {
			return false ;
		}		
	}

	@Override
	public ArrayList<Annonce> getAll() {
		return (ArrayList<Annonce>) annonceRepository.findAll();
	}

	@Override
	public Boolean delete(Integer id) {
		annonceRepository.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("L'annonce avec l'id" + id + " n'existe pas"))) ;
		annonceRepository.deleteById(id);
		return true ;
	}

	@Override
	public List<Annonce> findByIdUser(Integer id_user_post) {
		List<Annonce> result = annonceRepository.findByIdUserPost(id_user_post) ;
		if (result != null) {
			return result;
		}else {
			return null ;
		}
	}


}
