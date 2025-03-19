package com.egg.comerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.egg.comerce.exception.UnfilledFormException;
import com.egg.comerce.model.Manufacturer;
import com.egg.comerce.repository.ManufacturerRepository;

import jakarta.transaction.Transactional;

public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Transactional
    public void createManofactuer(String name) throws UnfilledFormException{   
        InputValidation(name);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public void UpdateManofactuer(Long manofacturerId,String name) throws UnfilledFormException{   
        InputValidation(name);
        Optional <Manufacturer> response = manufacturerRepository.findById(manofacturerId);
        if(response.isPresent()){
        Manufacturer manufacturer= response.get();
        manufacturer.setName(name);
        manufacturerRepository.save(manufacturer);
       }
       else {
        throw new UnfilledFormException("Manufacturer doesn't exist");
       }
    }
    @Transactional
    public void DeleteManofactuer(Long manofacturerId) throws UnfilledFormException{   
        Optional <Manufacturer> response = manufacturerRepository.findById(manofacturerId);
        if(response.isPresent()){
        manufacturerRepository.delete(response.get());
       }
       else {
        throw new UnfilledFormException("Manufacturer doesn't exist");
       }
    }

    public void InputValidation(String name)throws UnfilledFormException{
        if (name == null || name.isEmpty()) {
            throw new UnfilledFormException("Name is empty.");
        }     
    }

}
