package com.immobilier.services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.immobilier.entities.Contrat;
import com.immobilier.entities.Immobilier;
import com.immobilier.exceptions.ResourceNotFoundException;
import com.immobilier.repository.AnnonceRepository;
import com.immobilier.repository.ContratRepository;
import com.immobilier.repository.UtilisateurRepository;
import com.immobilier.services.interfaces.ContratServices;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class ContratServicesImpl implements ContratServices{
	
	AnnonceRepository annonceRepo;
	UtilisateurRepository userRepo;
	ContratRepository contratRepo;
	
	@Override
	public Contrat save(Contrat contrat) {
		if (contrat != null) {
			return contratRepo.save(contrat) ;
		}else {
			return null ;
		}
	}

	@Override
	public Contrat get(Integer id_contrat) {
		Contrat optionalResult = contratRepo.findById(id_contrat)
				.orElseThrow((() -> new ResourceNotFoundException("Le contrat avec l'id" + id_contrat + " n'existe pas"))) ;
		if (optionalResult != null ) {
			return optionalResult;
		}else {
			return null ;
		}
	}

	@Override
	public Boolean update(Integer id, Contrat newContrat) {
		Contrat optionalResult = contratRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("Le contrat avec l'id" + id + " n'existe pas"))) ;
		if (optionalResult != null) {
			optionalResult.setId_immobilier(newImmobilier.getId_immobilier());
			optionalResult.setCategorie(newImmobilier.getCategorie());
			optionalResult.setPrix(newImmobilier.getPrix());
			
			if (contratRepo.save(optionalResult) != null) {
				return true ;
			}else {
				return false ;
			}
			
		}else {
			return false ;
		}
	}

	@Override
	public ArrayList<Contrat> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
