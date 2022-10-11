package com.getnet.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getnet.app.dto.ClientDtoRequest;
import com.getnet.app.dto.ClientDtoResponse;
import com.getnet.app.model.Client;
import com.getnet.app.security.config.JwtTokenUtil;
import com.getnet.app.security.dto.JwtResponse;
import com.getnet.app.security.service.UserService;
import com.getnet.app.service.ClientService;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClientControllerTest {

	@MockBean
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	ClientController controller;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	ClientService clientService;

	@MockBean
	private UserService userService;

	@Test
	public void should_Create_New_Client() throws Exception {
		Client client = new Client("johni@gmail.com",  "johni pires", "123456");
		when(this.clientService.signup(any(Client.class))).thenReturn(client);

		ClientDtoRequest request = new ClientDtoRequest("johni@gmail.com", "johni pires", "password");
		
		String json = mapper.writeValueAsString(request);
		
		 String jsonResponse = mvc.perform(post("/signup")
				 .content(json) 
				 .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		 
		 ClientDtoResponse response = mapper.readValue(jsonResponse, ClientDtoResponse.class);
		
		 assertEquals(client.getEmail(), response.getEmail());
		 assertEquals(client.getName(), response.getName());
	}
	
	@Test
	public void should_Signin_And_Get_Bearer_Token() throws Exception {
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W10sInN1YiI6ImpvaG5pMDFAZ21haWwuY29tIiwiaWF0IjoxNjY1NDIzODgyLCJleHAiOjE2NjkwMjM4ODJ9.Jw4nG2mobWWKEws6mTo246Kme7tysBsPM_WeRLbGI6v97M5lPz5ba_22eGlcWvwtUNboRb0ORq84QrnzCEZPSQ";
		when(this.userService.getToken(any(ClientDtoRequest.class))).thenReturn(new JwtResponse(token));

		ClientDtoRequest request = new ClientDtoRequest("johni@gmail.com", "", "password");
		
		String json = mapper.writeValueAsString(request);
		
		 String jsonResponse = mvc.perform(post("/signin")
				 .content(json) 
				 .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		 
		 JwtResponse jwt = mapper.readValue(jsonResponse, JwtResponse.class);
		
		 assertNotNull(jwt.getToken());
		 assertEquals(jwt.getToken(), token);
	}

}
