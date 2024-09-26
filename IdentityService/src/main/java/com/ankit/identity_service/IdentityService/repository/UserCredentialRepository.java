package com.ankit.identity_service.IdentityService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.identity_service.IdentityService.entity.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer>{
	Optional<UserCredential> findByName(String username);

}
