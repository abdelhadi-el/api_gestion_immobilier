// 
// Decompiled by Procyon v0.5.36
// 

package com.immobilier.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.immobilier.entities.Utilisateur;
import com.immobilier.services.interfaces.UserServices;

@RestController
@RequestMapping("/api/authentication") // remplir la
public class AauthenticationController
{
	@Autowired
	UserServices userServices ;
    
	@SuppressWarnings("unchecked")
	@PostMapping("/register") // peut etre change
    public ResponseEntity<Utilisateur> register(@RequestBody Utilisateur user) {
		if (user != null) {
			Utilisateur userSaved = userServices.save(user) ;
			URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authentication/register").toUriString()) ;
			return ResponseEntity.created(uri).body(userSaved);
		}else {
			return (ResponseEntity<Utilisateur>) ResponseEntity.status(HttpStatus.NO_CONTENT); // body(userSaved);
		}
			
		}
	
//    
//    @PostMapping("/login")
//    public String login() {
//    	System.out.println("entred Login");
//        if (this.isAuthenticated()) {
//            return "redirect:/index";
//        }
//        return "login";
//    }

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ;
		if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) { 
			// in the front end and after being connected, we have the acces_token, so each time
			// we want to send a request we will add a "text "(stable = final) in the starting of each request
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algo = Algorithm.HMAC256("secretCode".getBytes()); // should be same secret as the on in the customFilter algo
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token) ;
				String userName = decodedJWT.getSubject();
				Utilisateur user = userServices.get(userName) ;
				Collection<String> roles =new ArrayList<>() ; 
				roles.add( user.getRole() ) ;
				
				String access_token = JWT.create().
						withSubject(user.getUserName()).
						withExpiresAt(new Date( System.currentTimeMillis() + 10*60*100 )).
						withIssuer(request.getRequestURL().toString()).
						withClaim("roles", roles.stream().collect(Collectors.toList())).  // look at this maybe wrong
						sign(algo);
			
		//		response.setHeader("access_token", access_token); 
		//		response.setHeader("refresh_token", refresh_token);
				Map<String, String> tokens = new HashMap<>() ;
				tokens.put("access_token", access_token) ;
				tokens.put("refresh_token", refresh_token) ;
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
			}catch (Exception e) {
				//System.out.println("/refreshtoken / firstException");
				 response.setHeader("error", e.getMessage());
				 // response.sendError(HttpStatus.FORBIDDEN.value());
				 response.setStatus(HttpStatus.FORBIDDEN.value()) ;
				 Map<String, String> error = new HashMap<String, String>() ;
				 error.put("error", e.getMessage()) ;
				 response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				 new ObjectMapper().writeValue(response.getOutputStream(), error);
					
			}
			}else {
				System.out.println("no authorization field");
				throw new RuntimeException("refresh token is missing") ; // this should be changed to a MissingRefreshTokenException a creer ulterieurement
			}

	}
//    // a changer
//    private boolean isAuthenticated() {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null && !AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass()) && authentication.isAuthenticated();
//    }
}
