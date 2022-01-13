package com.immobilier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.immobilier.entities.ImageModel;


public interface ImageRepository extends JpaRepository<ImageModel, Long> {
	Optional<ImageModel> findByName(String name);
	
	@Query(value = "FROM ImageModel where id_annonce = ?1")
	Optional<ImageModel> findByIdAnnonce(Integer id_annonce);
	
//	 @Query(value="select * from author a where a.first_name= :firstName", nativeQuery=true)
//	    List<Author> getAuthorsByFirstName(String firstName);

}
