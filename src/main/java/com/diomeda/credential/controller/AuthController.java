package com.diomeda.credential.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diomeda.credential.model.Person;
import com.diomeda.credential.model.Rol;
import com.diomeda.credential.model.UserAccount;
import com.diomeda.credential.repository.PersonRepository;
import com.diomeda.credential.repository.RolRepository;
import com.diomeda.credential.repository.UserRepository;
import com.diomeda.credential.security.JwtTokenProvider;
import com.diomeda.credential.security.UserPrincipal;
import com.diomeda.credential.service.UserDetailsServiceImpl;
import com.diomeda.credential.util.CustomErrorType;
import com.diomeda.credential.wrapper.JwtAuthenticationResponse;
import com.diomeda.credential.wrapper.LoginRequest;
import com.diomeda.credential.wrapper.SignUpRequest;

/**
 * 
 * @author mdiomeda
 *
 */
@RestController
@RequestMapping("/api")
public class AuthController extends BaseController{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping(value = "/auth/signin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest) {
        UserPrincipal userPrincipal = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserAccount user = userRepository.findByName(loginRequest.getUsername());
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
    }

    @PreAuthorize("hasAuthority('superadmin')")
    @PostMapping(value = "/auth/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> registerUser(@Valid SignUpRequest signUpRequest) throws Exception {
        if(userRepository.findByName(signUpRequest.getUsername()) != null) {
            return new ResponseEntity<>(new CustomErrorType(getMessage("auth.usernametaken")),
                    HttpStatus.BAD_REQUEST);
        }

        Person person = personRepository.findById(signUpRequest.getPersonId()).orElse(null);
        if (person == null){
            return new ResponseEntity<>(new CustomErrorType(getMessage("auth.person.error", signUpRequest.getPersonId())),
                    HttpStatus.BAD_REQUEST);
        }

        List<Rol> userRol = rolRepository.findByName(signUpRequest.getRol());
        if (userRol.isEmpty())
            throw new Exception(getMessage("auth.role.error"));

        // Creating user's account
        UserAccount user = new UserAccount();
        user.setName(signUpRequest.getUsername());
        user.setStatus(signUpRequest.getStatus());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPerson(person);

        user.setRoles(Collections.singleton(userRol.get(0)));
        UserAccount result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/{username}")
                .buildAndExpand(result.getName()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return  new ResponseEntity<>(result, headers, HttpStatus.CREATED);

    }
}
