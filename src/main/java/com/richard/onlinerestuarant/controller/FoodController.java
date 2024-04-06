package com.richard.onlinerestuarant.controller;

import com.richard.onlinerestuarant.model.Food;
import com.richard.onlinerestuarant.model.Restaurant;
import com.richard.onlinerestuarant.model.User;
import com.richard.onlinerestuarant.request.CreateFoodRequest;
import com.richard.onlinerestuarant.service.FoodService;
import com.richard.onlinerestuarant.service.RestaurantService;
import com.richard.onlinerestuarant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.searchFood(name);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian, @RequestParam boolean seasonal
                                                        ,@RequestParam boolean nonveg,
                                                        @PathVariable Long restaurantId,
                                                        @RequestParam(required = false) String food_category,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.getRestaurantFood(restaurantId,vegetarian,seasonal,nonveg,food_category);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
