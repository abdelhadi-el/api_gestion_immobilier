package com.immobilier.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println( request.getServletPath()  + " calling PATH" );
		if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/authentication/token/refresh")
					|| request.getServletPath().equals("/image/uploadImage") ) { // just try to login
				System.out.println("passed by here");
			filterChain.doFilter(request, response) ;
		}else {
			
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ;
			if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) { 
				// in the front end and after being connected, we have the acces_token, so each time
				// we want to send a request we will add a "text "(stable = final) in the starting of each request
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algo = Algorithm.HMAC256("secretCode".getBytes()); // should be same secret as the on in the customFilter algo
					JWTVerifier verifier = JWT.require(algo).build();
					DecodedJWT decodedJWT = verifier.verify(token) ;
					String userName = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorithies = new ArrayList<>();
					for (int i = 0; i < roles.length; i++) {
						authorithies.add(new SimpleGrantedAuthority(roles[i])) ;
					}
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null,authorithies);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken) ;	
					filterChain.doFilter(request, response);
				}catch (Exception e) {
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
					filterChain.doFilter(request, response);
				}
		}
	}

}
