package com.getnet.app.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.getnet.app.dto.ClientDtoRequest;
import com.getnet.app.model.Client;
import com.getnet.app.repository.ClientRepository;
import com.getnet.app.security.config.JwtTokenUtil;
import com.getnet.app.security.dto.JwtResponse;
import com.getnet.app.security.service.UserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	@MockBean
	private ClientRepository clientRepository;

	@Test
	public void should_Create_New_Token() throws Exception {

		String token = "eyJhbGciOiJIUzUxMiJ9.abc.def";

		Optional<Client> clientOpt = Optional.of(new Client("johni@gmail.com", null, "123456"));
		when(this.clientRepository.findByEmail(anyString())).thenReturn(clientOpt);

		when(this.jwtTokenUtil.generateToken(any(UserDetails.class), anySet())).thenReturn(token);

		ClientDtoRequest clientDtoRequest = new ClientDtoRequest("johni@gmail.com", null, "123456");

		JwtResponse jwtResponse = userService.getToken(clientDtoRequest);

		assertEquals(jwtResponse.getToken(), token);
	}

	@Test
	public void should_Throw_UsernameNotFoundException() throws Exception {

		String token = "eyJhbGciOiJIUzUxMiJ9.abc.def";

		when(this.clientRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		when(this.jwtTokenUtil.generateToken(any(UserDetails.class), anySet())).thenReturn(token);

		ClientDtoRequest clientDtoRequest = new ClientDtoRequest("johni@gmail.com", null, "123456");

		assertThrows(UsernameNotFoundException.class, () -> userService.getToken(clientDtoRequest));
	}

}
