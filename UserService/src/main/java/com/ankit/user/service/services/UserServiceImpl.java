package com.ankit.user.service.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ankit.user.service.entities.Hotel;
import com.ankit.user.service.entities.Rating;
import com.ankit.user.service.entities.User;
import com.ankit.user.service.exceptions.ResourceNotFoundException;
import com.ankit.user.service.external.services.HotelService;
import com.ankit.user.service.external.services.RatingService;
import com.ankit.user.service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RatingService ratingService;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {

		// TODO Auto-generated method stub

		List<User> users = userRepository.findAll();
		List<User> updatedUsers = new ArrayList<User>();
		for (User singleUser : users) {
			
			//Using Rest Template
			//Rating[] ratingsOfUser = restTemplate
			//		.getForObject("http://RATINGSERVICE/ratings/users/" + singleUser.getUserId(), Rating[].class);
			//List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
			
			//Using Feign Client
			List<Rating> ratings = ratingService.getRatingByUserId(singleUser.getUserId());
			logger.info("ratings of user using Feign client---------->" + ratings);
			List<Rating> ratingList = ratings.stream().map(rating -> {
             
				//Using Rest Template	
				//ResponseEntity<Hotel> forEntity = restTemplate
				//		.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);

				//Hotel hotel = forEntity.getBody();
				
				//Using Feign Client	
				Hotel hotel = hotelService.getHotel(rating.getHotelId());
				rating.setHotel(hotel);

				return rating;
			}).collect(Collectors.toList());

			singleUser.setRatings(ratingList);
			updatedUsers.add(singleUser);
		}
		return updatedUsers;
	}

	@Override
	public User getUser(String userId) {

		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id is not found on server " + userId));
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(),
				Rating[].class);
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		logger.info("ratings of user" + ratings);
		List<Rating> ratingList = ratings.stream().map(rating -> {

			//ResponseEntity<Hotel> forEntity = restTemplate
			//		.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);

			//Hotel hotel = forEntity.getBody();
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			rating.setHotel(hotel);

			return rating;
		}).collect(Collectors.toList());

		user.setRatings(ratingList);

		return user;
	}
}
