package com.richard.onlinerestuarant.request;

import com.richard.onlinerestuarant.model.Category;
import com.richard.onlinerestuarant.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private double price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;

    private List<IngredientsItem> ingredients;
}
