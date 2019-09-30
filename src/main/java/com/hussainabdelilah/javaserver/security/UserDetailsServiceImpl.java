package com.hussainabdelilah.javaserver.security;

import com.hussainabdelilah.javaserver.models.User;
import com.hussainabdelilah.javaserver.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.findByUsername(email);
        if (user == null) throw new UsernameNotFoundException("invalid user");

        List<GrantedAuthority> authorities = new ArrayList<>();
		/*user.getRoles().forEach(r ->
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()))
		);*/

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
