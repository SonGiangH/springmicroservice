package com.shopme.authservice.service;

import com.shopme.authservice.entity.Account;
import com.shopme.authservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get account by username from database repo
        Account account = accountRepo.findByUsername(username);

        // if account == null push ra exception "no user found"
        if (account == null) {
            throw new UsernameNotFoundException("No user found");
        }

        // tao empty list of roles name authorities
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // get all roles of account push vao list authorities
        account.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role)));

        // return ve 1 User duoi dang Spring security type
        return new User(account.getUsername(), account.getPassword(), authorities);
    }
}
