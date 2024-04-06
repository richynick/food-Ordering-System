package com.richard.onlinerestuarant.controller;

import com.richard.onlinerestuarant.model.IngredientCategory;
import com.richard.onlinerestuarant.model.IngredientsItem;
import com.richard.onlinerestuarant.request.IngredientCategoryRequest;
import com.richard.onlinerestuarant.request.IngredientRequest;
import com.richard.onlinerestuarant.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest request
            ) throws Exception {
        IngredientCategory item= ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest request
    ) throws Exception {
        IngredientsItem item= ingredientsService.createIngredientItem(request.getRestaurantId(),request.getName(),request.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStoke(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item= ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> item= ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> item= ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
