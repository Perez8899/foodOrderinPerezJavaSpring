package com.perez.service;

import java.util.List;

import com.perez.Exception.RestaurantException;
import com.perez.model.Events;

public interface EventsService {

    public Events createEvent(Events event,Long restaurantId) throws RestaurantException;

    public List<Events> findAllEvent();

    public List<Events> findRestaurantsEvent(Long id);

    public void deleteEvent(Long id) throws Exception;

    public Events findById(Long id) throws Exception;

}

