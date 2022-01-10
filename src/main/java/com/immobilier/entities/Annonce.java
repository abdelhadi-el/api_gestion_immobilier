package com.immobilier.entities;

import java.sql.Blob;
import java.sql.Date;

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
@Table(name = "annonce")
public class Annonce {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_annonce ;
	@OneToOne
	@JoinColumn(name = "id_immobilier", referencedColumnName = "id_immobilier")
	private Immobilier immobilier ; //id_immobilier
	@OneToOne
	@JoinColumn(name = "id_user_post", referencedColumnName = "id_user")
	private Utilisateur user_post ;
	@OneToOne
	@JoinColumn(name = "id_user_reserve", referencedColumnName = "id_user")
	private Utilisateur user_reserve ;
	private String titre ;
	private Blob photo ; 			// voir ce type
	private String type ;
	private Date date_annonce ;
	private String etat_validation ;
	private String etat_reservation ;

}
