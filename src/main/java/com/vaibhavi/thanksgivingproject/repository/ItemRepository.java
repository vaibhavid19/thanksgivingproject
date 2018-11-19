package com.vaibhavi.thanksgivingproject.repository;

import com.vaibhavi.thanksgivingproject.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query("from Item i where i.name=:name")
    public List<Item> findByItemName(@Param("name") String name);

    @Query("from Item i where i.id=:id AND i.name=:name")
    public Optional<Item> findByItemNameAndId(@Param("id") int id, @Param("name") String name);
}
