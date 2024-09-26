package com.ankit.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.user.service.entities.User;
import com.ankit.user.service.services.UserService;
import com.ankit.user.service.controllers.UserController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userservice.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	int retryCount = 1;
	@GetMapping("/{userId}")
	//@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod="ratingHotelFallback")
	//@Retry(name="ratingHotelService",fallbackMethod="ratingHotelFallback")
	@RateLimiter(name="userRateLimiter",fallbackMethod="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId)
	{
		logger.info("inside RateLimiter method");
		//retryCount++;
		User user = userservice.getUser(userId);
		return ResponseEntity.ok(user);
	}

	//Creating fallbackMethod for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		logger.info("Fallback is executed because service is down :",ex.getMessage());
		User user = User.builder()
			.name("Dummy")	
		    .email("dummy@gmail.com")
		    .about("This user is created dummy because some service is down")
		    .userId("141234")
		    .build();
		return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser()
	{
		System.out.println("inside get mapping");
		List<User> allUser = userservice.getAllUser();
		return ResponseEntity.ok(allUser);
	}
}
