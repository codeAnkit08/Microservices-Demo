package com.ankit.rating.service;

import java.util.List;

import com.ankit.rating.entities.Rating;

public interface RatingService {

	//create
	Rating create(Rating rating);
	
	//get all ratings
	List<Rating> getRatings();
	
	
	//get all by userId
	List<Rating> getRatingByUserId(String userId);
	
	//get all by hotel
	List<Rating> getRatingByHotelId(String hotelId);
}
