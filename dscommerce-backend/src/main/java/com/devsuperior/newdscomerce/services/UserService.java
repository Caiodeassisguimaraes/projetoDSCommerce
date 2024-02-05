package com.devsuperior.newdscomerce.services;

import com.devsuperior.newdscomerce.dto.UserDto;
import com.devsuperior.newdscomerce.entities.User;
import com.devsuperior.newdscomerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return user;
    }

    protected User authenticated(){
        try{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return repository.findByEmail(userName);
        }
        catch(Exception e){
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @Transactional (readOnly = true)
    public UserDto getMe(){
        User entity = authenticated();
        return new UserDto(entity);
    }

}
