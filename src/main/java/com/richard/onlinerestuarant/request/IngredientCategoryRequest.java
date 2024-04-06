package com.richard.onlinerestuarant.request;

import com.richard.onlinerestuarant.model.Restaurant;
import lombok.Data;

@Data
public class IngredientCategoryRequest {
    private String name;
    private Long restaurantId;
}
