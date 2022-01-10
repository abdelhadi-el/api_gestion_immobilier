package com.immobilier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.immobilier.entities.Contrat;


public interface ContratRepository extends JpaRepository<Contrat, Integer> {

}
