package com.vaibhavi.thanksgivingproject.controller;

import com.vaibhavi.thanksgivingproject.entity.Item;
import com.vaibhavi.thanksgivingproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/object")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/create/item/{className}")
    public Item addItem(@RequestBody Item item, @PathVariable String className){
        return this.itemRepository.save(item);
    }

//    Scenario: Create where object is in data store.
//    Given An object of type  "item" with the format { "id": 1, "name": "sword" }, that is in the data store.
//    When a POST call is made to /object/create/{className}
//    Then The object that has type=="item" and "id"==1 will be overwritten in the data store and a 200 is returned.
//    All objects have an "id" field that holds their key value.

//    Scenario: Delete item that IS in the data store.
//    Given An object of type  "item" with the format { "id": 1, "name": "sword" }
//    When a POST call is made to /object/delete/{className}/{id}
//    Then The object is deleted from the data store and a 200 is returned.

//    Scenario: Delete item that is NOT in the data store.
//    Given An object of type  "item" with the format { "id": 1, "name": "sword" }
//    When a POST call is made to /object/delete/{className}/{id}
//    Then The object is not found and the service returns 404.

//    Scenario: Get a specific item.
//    Given An object of type  "item" with the format { "id": 1, "name": "sword" } is in the data store.
//    When a GET call is made to /object/get/{className}/{id}
//    Then The object is returned in json format along with a 200 code.

//    Scenario: Get a specific item but it is not in the data store.
//    Given An object of type  "item" with the format { "id": 1, "name": "sword" } is not in the data store.
//    When a GET call is made to /object/get/{className}/{id}
//    Then The service returns 404.

//    Scenario: Get all items of a specific type.
//    Given Several objects of type  "item" are in the data store.
//    When a GET call is made to /object/get/{className}
//    Then The service returns a JSON array of all the items that match the className (could be an empty array) and a 200.

//    Scenario: Get all items in the registry.
//    Given Several objects are in the data store.
//    When a GET call is made to /object/get/
//    Then The service returns a JSON array of all the items in the data store(could be an empty array) and a 200.


    /*@DeleteMapping("/api/review/delete")
    public void deleteReviewById(@RequestParam long reviewerId, @RequestParam long reviewId){
        Review review = this.repository.findByReviewerAndReviewId(reviewerId, reviewId);
        if(review!=null){
            this.repository.deleteById(review.getReviewId());
        } else {
            throw new RuntimeException();
        }
    }

    @PutMapping("/api/review/update")
    public Review updateReview(@RequestParam long movieId, @RequestParam long reviewerId, @RequestBody String reviewText){
        Review review = this.repository.findReviewByMovieAndReviewerId(movieId, reviewerId);
        return this.repository.save(review);
    }

    @GetMapping("/api/movies/all")
    public Iterable<Movie> getAll() {
        return this.movieRepository.findAll();
    }*/


}
