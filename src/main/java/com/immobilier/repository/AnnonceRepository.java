package com.immobilier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.immobilier.entities.Annonce;


public interface AnnonceRepository extends JpaRepository<Annonce, Integer>{

	 @Query(value="SELECT * FROM annonce WHERE id_user_post=:id_user_post", nativeQuery=true)
	List<Annonce> findByIdUserPost(Integer id_user_post);
	
}
