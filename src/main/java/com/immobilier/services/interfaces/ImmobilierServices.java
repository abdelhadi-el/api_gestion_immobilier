package com.immobilier.services.interfaces;

import java.util.ArrayList;

import com.immobilier.entities.Immobilier;

public interface ImmobilierServices {

	Immobilier save(Immobilier immobilier) ;
	Immobilier get(Integer id_Immobilier) ;
	Boolean update(Integer id,Immobilier newImmobilier) ;
	ArrayList<Immobilier> getAll() ;
	Boolean delete(Integer id) ;


}
