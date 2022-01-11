package com.immobilier.services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immobilier.entities.Contrat;
import com.immobilier.exceptions.ResourceNotFoundException;
import com.immobilier.repository.ContratRepository;
import com.immobilier.services.interfaces.ContratServices;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class ContratServicesImpl implements ContratServices{
	
	@Autowired
	ContratRepository contratRepo;
	
	@Override
	public Contrat save(Contrat contrat) {
		if (contrat != null) {
			return contratRepo.save(contrat) ;
		}else {
			throw new ResourceNotFoundException("les donnees du contrat entre sont non suffisants") ;
			//return null ;
		}
	}

	@Override
	public Contrat get(Integer id_contrat) {
		Contrat optionalResult = contratRepo.findById(id_contrat)
				.orElseThrow((() -> new ResourceNotFoundException("Le contrat avec l'id" + id_contrat + " n'existe pas"))) ;
		return optionalResult;
		
	}

//	@Override
	public Boolean update(Integer id, Contrat newContrat) {
		Contrat optionalResult = contratRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("Le contrat avec l'id" + id + " n'existe pas"))) ;
		//optionalResult.setId_contrat(newImmobilier.getId_immobilier()); // pas de modif de id
		optionalResult.setAnnonce(newContrat.getAnnonce());
		optionalResult.setUser_vendeur(newContrat.getUser_vendeur());
		optionalResult.setUser_acheteur(newContrat.getUser_acheteur());
		optionalResult.setObjet_contrat(newContrat.getObjet_contrat());
		optionalResult.setType_transaction(newContrat.getType_transaction());

		if (contratRepo.save(optionalResult) != null) {
			return true ;
		}else {
			return false ;
		}
		
	}

	@Override
	public ArrayList<Contrat> getAll() {
		return (ArrayList<Contrat>) contratRepo.findAll();

	}

	@Override
	public Boolean delete(Integer id) {
		contratRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("Le contrat avec l'id" + id + " n'existe pas"))) ;
		contratRepo.deleteById(id);
		return true ;
	}

}
