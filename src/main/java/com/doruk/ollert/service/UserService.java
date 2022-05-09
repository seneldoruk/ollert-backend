package com.doruk.ollert.service;

import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.User;
import com.doruk.ollert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {
    @Autowired
    UserRepository userRepository;



    public User saveUser(User user){
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User getByUsername(String username){
        return  userRepository.findByUsername(username);
    }


    public List<Sheet> getTablesByUserID(Long id){
        User user = userRepository.findById(id).orElse(null);
        return user.getSheets();

    }

}
