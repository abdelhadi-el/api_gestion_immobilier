package com.immobilier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.immobilier.entities.Annonce;


public interface AnnonceRepository extends JpaRepository<Annonce, Integer>{

}
