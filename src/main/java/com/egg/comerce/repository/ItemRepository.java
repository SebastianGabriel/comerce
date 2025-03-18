package com.egg.comerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.egg.comerce.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT MAX(i.code) FROM Item i")
    Integer findMaxCode();
}


