package com.getnet.app.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
}
