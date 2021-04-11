package com.keb.club_pila.config.UserDetails;

import com.keb.club_pila.model.entity.user.User;
import com.keb.club_pila.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        //Optional<User> foundUser=;
            return userRepository.findUserByUsername(username)
                    .map(user->
                    createUser(username, user)).orElseThrow(()->new UsernameNotFoundException(username+": DB에서 찾을 수 없음."));
    }
    private org.springframework.security.core.userdetails.User createUser(String username, User user){
      List<GrantedAuthority> grantedAuthority= new ArrayList<>();
               grantedAuthority.add(new SimpleGrantedAuthority(user.getRole().toString()));
        System.out.println("grantedAuthoriti!!!:: "+ grantedAuthority);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthority);
    }
}
