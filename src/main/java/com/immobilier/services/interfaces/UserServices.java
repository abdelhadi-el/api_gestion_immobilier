package com.immobilier.services.interfaces;

import java.util.ArrayList;

import com.immobilier.entities.Utilisateur;

public interface UserServices {

	Utilisateur save(Utilisateur user) ;
	Utilisateur get(Integer id_user) ;
	Utilisateur get(String userName) ;
	Boolean update(Integer id,Utilisateur newUser) ;
	ArrayList<Utilisateur> getAll() ;
	Boolean delete(Integer id) ;
	


}
