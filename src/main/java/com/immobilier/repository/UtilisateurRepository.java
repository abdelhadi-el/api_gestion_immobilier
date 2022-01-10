package com.immobilier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.immobilier.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>{

	Utilisateur findByUserName(String username);

}
