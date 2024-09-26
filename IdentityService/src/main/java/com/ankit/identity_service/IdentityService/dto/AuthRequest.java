package com.ankit.identity_service.IdentityService.dto;

import com.ankit.identity_service.IdentityService.entity.UserCredential;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthRequest {
	
	private String username;
	private String password;

}
