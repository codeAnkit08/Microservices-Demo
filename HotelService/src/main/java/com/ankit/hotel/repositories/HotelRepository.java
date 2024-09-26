package com.ankit.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.hotel.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,String>{

}
