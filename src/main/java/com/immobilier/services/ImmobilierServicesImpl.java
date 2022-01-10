package com.immobilier.services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immobilier.entities.Immobilier;
import com.immobilier.exceptions.ResourceNotFoundException;
import com.immobilier.repository.ImmobilierRepository;
import com.immobilier.services.interfaces.ImmobilierServices;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class ImmobilierServicesImpl implements ImmobilierServices{

	@Autowired
	ImmobilierRepository immobilierRepo ;

	
	@Override
	public Immobilier save(Immobilier immobilier) {
		if (immobilier != null) {
			return immobilierRepo.save(immobilier) ;
		}else {
			return null ;
		}
	}

	@Override
	public Immobilier get(Integer id_Immobilier) {
		Immobilier optionalResult = immobilierRepo.findById(id_Immobilier)
				.orElseThrow((() -> new ResourceNotFoundException("L'immobilier avec l'id" + id_Immobilier + " n'existe pas"))) ;
		if (optionalResult != null ) {
			return optionalResult;
		}else {
			return null ;
		}
	}

	@Override
	public Boolean update(Integer id, Immobilier newImmobilier) {
		Immobilier optionalResult = immobilierRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("L'immobilier avec l'id" + id + " n'existe pas"))) ;
		if (optionalResult != null) {
			optionalResult.setId_immobilier(newImmobilier.getId_immobilier());
			optionalResult.setCategorie(newImmobilier.getCategorie());
			optionalResult.setPrix(newImmobilier.getPrix());
			
			if (immobilierRepo.save(optionalResult) != null) {
				return true ;
			}else {
				return false ;
			}
			
		}else {
			return false ;
		}
	}

	@Override
	public ArrayList<Immobilier> getAll() {
		return (ArrayList<Immobilier>) immobilierRepo.findAll();
	}

	@Override
	public Boolean delete(Integer id) {
		Immobilier immobilier = immobilierRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("L'immobilier avec l'id" + id + " n'existe pas"))) ;
		if (immobilier != null) {
			immobilierRepo.deleteById(id);
			return true ;
		}else {
			return false ;
		}
	}

}
