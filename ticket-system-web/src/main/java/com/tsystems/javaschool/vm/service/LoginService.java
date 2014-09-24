package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.UserDAO;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
    private static final Logger LOG = Logger.getLogger(LoginService.class);
    @Autowired
    private UserDAO userDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDAO.findByLogin(username.toLowerCase());
            List<SimpleGrantedAuthority> authorities =
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole().getTitle()));
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
        } catch (EntityNotFoundException e) {
            UsernameNotFoundException e1 = new UsernameNotFoundException("User " + username + " not found");
            e1.initCause(e);
            throw e1;
        }
    }
}
