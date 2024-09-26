package com.ankit.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.user.service.entities.User;

public interface UserRepository extends JpaRepository<User,String>{

}
