package com.immobilier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "immobilier")
public class Immobilier {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_immobilier ;
	private String categorie ;
	private Double prix ;

}
