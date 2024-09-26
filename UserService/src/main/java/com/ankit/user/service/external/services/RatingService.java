package com.ankit.user.service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.ankit.user.service.entities.Rating;

@FeignClient(name="RATINGSERVICE")
public interface RatingService {

	//get
	
	//post
	@PostMapping("/ratings")
	public Rating createRating(Rating values);
	
	//put
	@PutMapping("/ratings/{ratingId}")
	public Rating updateRating(@PathVariable("ratingId") String ratingId,Rating values);
	
	
	//delete
	@DeleteMapping("/ratings/{ratingId}")
	public void deleteRating(@PathVariable("ratingId") String ratingId);
	
	@GetMapping("/ratings/users/{userId}")
	public List<Rating> getRatingByUserId(@PathVariable("userId")String userId);
	
}
