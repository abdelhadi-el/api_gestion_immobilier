package com.immobilier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.immobilier.entities.Immobilier;

public interface ImmobilierRepository extends JpaRepository<Immobilier, Integer> {

}
