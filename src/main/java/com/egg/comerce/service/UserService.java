package com.egg.comerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.egg.comerce.exception.UnfilledFormException;
import com.egg.comerce.model.User;
import com.egg.comerce.enums.Rol;
import com.egg.comerce.repository.UserRepository;

import jakarta.transaction.Transactional;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createManofactuer(String name,String surname,String email,String password) throws UnfilledFormException{   
        InputValidation(name,surname,email,password);
        User user = new User();
        user.setname(name);
        user.setsurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRol(Rol.USER);
        userRepository.save(user);
    }

    @Transactional
    public void UpdateManofactuer(Long userId,String name,String surname,String email,String password) throws UnfilledFormException{   
        InputValidation(name,surname,email,password);
        Optional <User> response = userRepository.findById(userId);
        if(response.isPresent()){
        User user= response.get();
        user.setname(name);
        user.setsurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
       }
       else {
        throw new UnfilledFormException("user doesn't exist");
       }
    }
    @Transactional
    public void DeleteManofactuer(Long manofacturerId) throws UnfilledFormException{   
        Optional <User> response = userRepository.findById(manofacturerId);
        if(response.isPresent()){
        userRepository.delete(response.get());
       }
       else {
        throw new UnfilledFormException("user doesn't exist");
       }
    }

    public void InputValidation(String name,String surname,String email,String password)throws UnfilledFormException{
        if (name == null || name.isEmpty()) {
            throw new UnfilledFormException("Name is empty.");
        }  
        if (surname == null || surname.isEmpty()) {
            throw new UnfilledFormException("Surname is empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new UnfilledFormException("password is empty.");
        }   
        if (email == null || email.isEmpty()) {
            throw new UnfilledFormException("email is empty.");
        }   
    }
}
