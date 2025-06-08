package com.perez.Exception;

public class RestaurantException extends Exception{
    private static final long serialVersionUID = 1L;

    public RestaurantException(String message) {
        super(message);
        // super(message != null ? message : "Error desconocido en el restaurante");

    }

}
