package com.keb.club_pila.config.services;

import com.keb.club_pila.model.entity.user.User;
import com.keb.club_pila.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: "+username);
        Optional<User> userEntity=userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
