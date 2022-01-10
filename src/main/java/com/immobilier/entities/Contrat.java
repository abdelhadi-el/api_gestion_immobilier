package com.immobilier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "contrat")
public class Contrat {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_contrat ;
	
	@OneToOne 		//fetch = FetchType.EAGER, mappedBy = id_annonce
	@JoinColumn(name = "id_annonce", referencedColumnName = "id_annonce")
	private Annonce annonce ;
	
	@OneToOne
	@JoinColumn(name = "id_user_vendeur", referencedColumnName = "id_user")
	private Utilisateur user_vendeur ;
	
	@OneToOne
	@JoinColumn(name = "id_user_acheteur", referencedColumnName = "id_user")
	private Utilisateur user_acheteur ;
	
	private String objet_contrat ;
	private String type_transaction ;




}
