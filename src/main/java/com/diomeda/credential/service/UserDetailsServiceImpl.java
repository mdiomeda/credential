package com.diomeda.credential.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diomeda.credential.model.Rol;
import com.diomeda.credential.model.UserAccount;
import com.diomeda.credential.repository.UserRepository;
import com.diomeda.credential.security.UserPrincipal;

import lombok.extern.slf4j.Slf4j;


@Service
@Primary
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByName(username);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("Credenciales inválidas"));
        }

        log.info(user.toString());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Rol role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserPrincipal loadUserById(Long id) {
        UserAccount user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User " + id)
        );

        return UserPrincipal.create(user);
    }
}