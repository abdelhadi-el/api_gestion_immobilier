package com.immobilier.services.interfaces;

import java.util.ArrayList;

import com.immobilier.entities.Contrat;

public interface ContratServices {

	Contrat save(Contrat contrat) ;
	Contrat get(Integer id_contrat) ;
	Boolean update(Integer id,Contrat newContrat) ;
	ArrayList<Contrat> getAll() ;
	Boolean delete(Integer id) ;


}
