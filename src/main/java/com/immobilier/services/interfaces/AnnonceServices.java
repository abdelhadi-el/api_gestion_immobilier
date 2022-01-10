package com.immobilier.services.interfaces;

import java.util.ArrayList;

import com.immobilier.entities.Annonce;

public interface AnnonceServices {

	Annonce save(Annonce annonce) ;
	Annonce get(Integer id_annonce) ;
	Boolean update(Integer id,Annonce newAnnonce) ;
	ArrayList<Annonce> getAll() ;
	Boolean delete(Integer id) ;


}
