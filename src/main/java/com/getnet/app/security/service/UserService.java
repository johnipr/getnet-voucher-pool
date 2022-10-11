package com.getnet.app.security.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.getnet.app.dto.ClientDtoRequest;
import com.getnet.app.model.Client;
import com.getnet.app.repository.ClientRepository;
import com.getnet.app.security.config.JwtTokenUtil;
import com.getnet.app.security.dto.JwtResponse;

//@Service("UserService")
@Component("barFormatter")
//@AllArgsConstructor
public class UserService implements UserDetailsService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<Client> clientOpt = clientRepository.findByEmail(email);

		if (clientOpt.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}

		UserDetails userDetails = clientOpt
				.map(user -> new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), //
						new ArrayList<String>().stream().map(a -> new SimpleGrantedAuthority(a)).toList()))
				.get();

		return userDetails;
	}

	public JwtResponse getToken(ClientDtoRequest credentialDto) {

		final UserDetails userDetails = loadUserByUsername(credentialDto.getEmail());

		Set<String> authorities = userDetails.getAuthorities().stream().map(e -> e.getAuthority())
				.collect(Collectors.toSet());

		final String token = jwtTokenUtil.generateToken(userDetails, authorities);
		return new JwtResponse(token);
	}

}