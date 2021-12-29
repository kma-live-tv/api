package com.nopain.livetv.security.service;

import com.nopain.livetv.repository.UserRepository;
import com.nopain.livetv.security.model.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findUserByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(username)
                );

        return new UserDetailsImpl(user);
    }
}
