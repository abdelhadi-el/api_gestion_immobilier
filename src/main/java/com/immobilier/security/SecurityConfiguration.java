package com.immobilier.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity @Configuration @RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService; 
    @Autowired // a verifier si obligatoire 
    BCryptPasswordEncoder passEncoder ;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // order matters
    	CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean()) ;
    	customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    	http.csrf().disable() ; 
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;

        http.authorizeRequests().antMatchers("/api/login" ).permitAll() ; //THAT IF WE WANT TO SKIP SECURTY IN SOME URLS
        //http.authorizeRequests().antMatchers( "/image/**" ).permitAll() ; //THAT IF WE WANT TO SKIP SECURTY IN SOME URLS

        // http.authorizeRequests().antMatchers( "/api/login/**" ).permitAll() ; //THAT IF WE WANT TO SKIP SECURTY IN SOME URLS
        http.authorizeRequests().antMatchers( "/api/authentication/**" ).permitAll() ; 
        http.authorizeRequests().antMatchers( "/api/userRole" ).permitAll() ; 

        http.authorizeRequests().antMatchers( "/api/immobilier/**" ).hasAnyAuthority("ADMIN", "USER") ; // to change
        http.authorizeRequests().antMatchers( "/api/annonce/**" ).hasAnyAuthority("ADMIN", "USER") ;
    
        http.authorizeRequests().antMatchers( "/api/contrat/getAll" ).hasAnyAuthority("ADMIN") ;
        http.authorizeRequests().antMatchers( "/api/contrat/**" ).hasAnyAuthority("ADMIN", "USER") ;
    	http.authorizeRequests()
        		.antMatchers( "/api/users/**" ).hasAnyAuthority("ADMIN") ;
//        		.antMatchers( "/api/users/**" )/api/userRole
//        		.antMatchers( HttpMethod.POST, "/api/users/save/**" ).hasAnyAuthority("USER") ;
        		//.antMatchers( HttpMethod.GET, "/users/**" ).hasAnyAuthority("USER"); // ON CONTINUE SELON CE QU'ON 

        http.authorizeRequests().anyRequest().authenticated() ; // instead of permit all, we want all to be authenticated
        http.addFilter(customAuthenticationFilter) ;
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) ;
    }

    @Bean
    @Override 
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	// TODO Auto-generated method stub
    	return super.authenticationManagerBean();
    }
    

}
