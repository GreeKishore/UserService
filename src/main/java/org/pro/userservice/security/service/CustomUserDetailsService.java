package org.pro.userservice.security.service;

import lombok.RequiredArgsConstructor;
import org.pro.userservice.entity.User;
import org.pro.userservice.repository.UserRepository;
import org.pro.userservice.security.entity.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //get thr user with the given username (in our case it is email) from the DB
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with username/email: " + username + "doesn't exist");
        }
        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}