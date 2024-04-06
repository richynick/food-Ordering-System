package com.richard.onlinerestuarant.request;

import com.richard.onlinerestuarant.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
