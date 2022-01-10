package com.immobilier.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.immobilier.entities.Utilisateur;
import com.immobilier.exceptions.ResourceNotFoundException;
import com.immobilier.repository.UtilisateurRepository;
import com.immobilier.services.interfaces.UserServices;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class UserServicesImpl implements UserServices, UserDetailsService{

	@Autowired
	UtilisateurRepository userRepo;
	@Autowired
	PasswordEncoder passEncoder ;
	
	@Override
	public Utilisateur save(Utilisateur user) {
		if (user != null) {
			user.setPassword(passEncoder.encode(user.getPassword()));
			return userRepo.save(user) ;
		}else {
			return null ;
		}
	}
		

	@Override
	public Utilisateur get(Integer id_user) {
		Utilisateur optionalResult = userRepo.findById(id_user)
				.orElseThrow((() -> new ResourceNotFoundException("Utilisateur avec l'id" + id_user + " n'existe pas"))) ;
		if (optionalResult != null ) {
			return optionalResult;
		}else {
			return null ;
		}
	}

	@Override
	public Boolean update(Integer id, Utilisateur newUser) {
		Utilisateur user = userRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("Utilisateur avec l'id" + id + " n'existe pas"))) ;
		if (user != null) {
			user.setUserName(newUser.getUserName());
			user.setPassword( passEncoder.encode( newUser.getPassword() ) );
			user.setRole(newUser.getRole());
			user.setTelephone(newUser.getTelephone());
			user.setAdresse(newUser.getAdresse());
			user.setEmail(newUser.getEmail());
			
			if (userRepo.save(user) != null) {
				return true ;
			}else {
				return false ;
			}
			
		}else {
			return false ;
		}
	}

	@Override
	public ArrayList<Utilisateur> getAll() {
		return (ArrayList<Utilisateur>) userRepo.findAll();
	}

	@Override
	public Boolean delete(Integer id) {

		Utilisateur user = userRepo.findById(id)
				.orElseThrow((() -> new ResourceNotFoundException("Utilisateur avec l'id" + id + " n'existe pas"))) ;
		if (user != null) {
			userRepo.deleteById(id);
			return true ;
		}else {
			return false ;
		}
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur user = userRepo.findByUserName(username) ;
		if (user == null) {
			System.out.println("Utilisateur n'existe pas.");
			return null;
		}else {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>() ;
			authorities.add(new SimpleGrantedAuthority(user.getRole())) ;
			return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(), authorities );
		}
	}

	//getting by username
	@Override
	public Utilisateur get(String userName) {
		Utilisateur user = userRepo.findByUserName(userName) ;
		if (user == null) {
			System.out.println("Utilisateur n'existe pas.");
			return null;
		}else {
			return user ;
		}
	}

}
