package com.immobilier.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	AuthenticationManager authenticationManager ;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("username") ;
		String password = request.getParameter("password") ;
		System.out.println("votre mdp est " + password + " pour l'user " + userName  + "  : LOG FROM CUSROM AUTHENTIF CLASS");
		UsernamePasswordAuthenticationToken authentifToken = new UsernamePasswordAuthenticationToken(userName, password) ;
		return authenticationManager.authenticate(authentifToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("success conn \n\n");
		User user = (User) authResult.getPrincipal() ;
		Algorithm algo = Algorithm.HMAC256("secretCode".getBytes());
		String access_token = JWT.create().
						withSubject(user.getUsername()).
						withExpiresAt(new Date( System.currentTimeMillis() + 10*60*100*5 )).  // je pense que c'est 2min hhhh
						withIssuer(request.getRequestURL().toString()).
						withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).
						sign(algo);
		String refresh_token = JWT.create().
				withSubject(user.getUsername()).
				withExpiresAt(new Date( System.currentTimeMillis() + 30*60*100*5 )). //much more 
				withIssuer(request.getRequestURL().toString()).
				sign(algo);
//		response.setHeader("access_token", access_token); 
//		response.setHeader("refresh_token", refresh_token);
		Map<String, Object> tokens = new HashMap<>() ;
		tokens.put("access_token", access_token) ;
		tokens.put("refresh_token", refresh_token) ;
		tokens.put("Object", user) ;					// i changed this and the type of the HashMap object
		response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
}
}
