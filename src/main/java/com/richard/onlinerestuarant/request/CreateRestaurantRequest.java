package com.richard.onlinerestuarant.request;

import com.richard.onlinerestuarant.model.Address;
import com.richard.onlinerestuarant.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHour;
    private List<String> images;

}
