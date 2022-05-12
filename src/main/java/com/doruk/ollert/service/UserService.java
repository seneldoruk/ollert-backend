package com.doruk.ollert.service;

import com.doruk.ollert.dto.AuthDTO;
import com.doruk.ollert.entity.User;
import com.doruk.ollert.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    final UserRepository userRepository;
    final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser(AuthDTO authDTO) throws IllegalArgumentException{
        String username = authDTO.getUsername();
        String password = authDTO.getPassword();

        if(getByUsername(username)!=null){
            throw new IllegalArgumentException(username +  "is already registered");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        saveUser(user);

    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
