package com.perez.request;

import lombok.Data;

@Data
public class CreateIngredientCategoryRequest {

    private Long restaurantId;
    private String name;
}
