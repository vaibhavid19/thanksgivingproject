package com.vaibhavi.thanksgivingproject.controller;

import com.vaibhavi.thanksgivingproject.Exception.ItemNotFound;
import com.vaibhavi.thanksgivingproject.entity.Item;
import com.vaibhavi.thanksgivingproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/object")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    //Added functionality for scenario 1 and 2 in this method
    @PostMapping("/create/{name}")
    public Item createItem(@RequestBody Item item, @PathVariable String name){
        if (item.getId() != 0) {
            if(itemRepository.existsById(item.getId())) {
                return this.itemRepository.save(item);
            } else {
                throw new ItemNotFound();
            }
        }
        return this.itemRepository.save(item);
    }

    // ** DO NOT USE **
    //Added functionality for scenario 2 independently in this method, if required
    // Error - Request method 'POST' not supported" - because to send id in RequestBody, PUT call is assumed
    @PutMapping("/update/{name}/{id}")
    public Item updateItem(@RequestBody Item item, @PathVariable String name, @PathVariable int id){
        if(!itemRepository.existsById(id)) {
                throw new ItemNotFound();
        }
        return this.itemRepository.save(item);
    }

    @DeleteMapping("/delete/{name}/{id}")
    public void deleteItem(@PathVariable int id, @PathVariable String name) throws Exception {
        if (!this.itemRepository.existsById(id)) {
            throw new ItemNotFound();
        }
        this.itemRepository.deleteById(id);
    }

    @GetMapping("/get/{name}/{id}")
    public Item getItemByIdAndName(@PathVariable int id, @PathVariable String name){
        Optional<Item> itemFromDb = this.itemRepository.findByItemNameAndId(id, name);
        if (!itemFromDb.isPresent()) {
            throw new ItemNotFound();
        }
        return itemFromDb.get();
    }

    @GetMapping("/get/{name}")
    public List<Item> getItemsByName(@PathVariable String name){
        return this.itemRepository.findByItemName(name);
    }

    @GetMapping("/get")
    public List<Item> getAllItems() {
        return (List<Item>) this.itemRepository.findAll();
    }
}
