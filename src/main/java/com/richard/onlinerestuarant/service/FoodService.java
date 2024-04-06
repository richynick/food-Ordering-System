package com.richard.onlinerestuarant.service;

import com.richard.onlinerestuarant.model.Category;
import com.richard.onlinerestuarant.model.Food;
import com.richard.onlinerestuarant.model.Restaurant;
import com.richard.onlinerestuarant.request.CreateFoodRequest;
import com.richard.onlinerestuarant.request.CreateRestaurantRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian, boolean isNonveg,
                                        boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailability(Long foodId) throws Exception;

}
