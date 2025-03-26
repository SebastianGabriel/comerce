package com.egg.comerce.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.comerce.repository.*;
import com.egg.comerce.exception.UnfilledFormException;
import com.egg.comerce.model.Item;
import com.egg.comerce.model.Manufacturer;
import jakarta.transaction.Transactional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Transactional
    public void createItem(String description,String name,Long manufacturerId) throws UnfilledFormException{   
        InputValidation(description,name,manufacturerId);
        Manufacturer manufacturer= manufacturerRepository.getReferenceById(manufacturerId); 
        if (manufacturer==null){
            throw new UnfilledFormException("Referenced manofacturer doesn't exist");
        }
        Item item = new Item();
        item.setDescription(description);
        item.setName(name);
        item.setManufacturer(manufacturer);

        Integer itemCode = (itemRepository.findMaxCode())+1;
        item.setItemCode(itemCode);
        itemRepository.save(item);
    }
    
    @Transactional
    public void updateItem(Long itemId,String description,String name,Long manufacturerId) throws UnfilledFormException{    
        InputValidation(description,name,manufacturerId);
        Optional<Item> response =itemRepository.findById(itemId);
        if(response.isPresent()){
        Item item = response.get();
        Manufacturer manufacturer= manufacturerRepository.getReferenceById(manufacturerId); 
        if (manufacturer==null){
            throw new UnfilledFormException("Referenced manofacturer doesn't exist");
        }
        item.setDescription(description);
        item.setName(name);
        item.setManufacturer(manufacturer);

        Integer itemCode = (itemRepository.findMaxCode())+1;
        item.setItemCode(itemCode);
        itemRepository.save(item);
    }
    else{
        throw new UnfilledFormException("The item doesn't exist");
    }
    }

    @Transactional
    public void delete(Long itemId) throws UnfilledFormException{
        Optional<Item> response = itemRepository.findById(itemId);
        if (response.isPresent()) {
            itemRepository.delete(response.get());
        } else {
            throw new UnfilledFormException("The item doesn't exist");
        }
    }
    
    
    public void InputValidation(String description,String name,Long manufacturerId)throws UnfilledFormException{
        if (description == null || description.isEmpty()) {
            throw new UnfilledFormException("Description is empty.");
        }
        if (name == null || name.isEmpty()) {
            throw new UnfilledFormException("Name is empty.");
        }
        if (manufacturerId == null) {
            throw new UnfilledFormException("ManufacturerId is empty.");
        }
    }

}
