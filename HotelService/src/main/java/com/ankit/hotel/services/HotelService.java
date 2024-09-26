package com.ankit.hotel.services;

import java.util.List;

import com.ankit.hotel.entities.Hotel;

public interface HotelService {
	
	//Create
	
	Hotel create(Hotel hotel);
	
	//get all
	
	List<Hotel> getAll();
	
	//get single
	
	Hotel get(String id);

}
