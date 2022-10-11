package com.getnet.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.getnet.app.dto.ClientDtoRequest;
import com.getnet.app.dto.ClientDtoResponse;
import com.getnet.app.model.Client;
import com.getnet.app.security.dto.JwtResponse;
import com.getnet.app.security.service.UserService;
import com.getnet.app.service.ClientService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	UserService userService;

	@ApiOperation(value = "Create a new Client for sign in")
	@PostMapping("/signup")
	public ResponseEntity<ClientDtoResponse> signup(@Valid @RequestBody ClientDtoRequest clientDtoRequest) throws Exception {
		
		Client client = clientService.signup(clientDtoRequest.convertDtoToModel());

		ClientDtoResponse response = ClientDtoResponse.convertoModelToDto(client);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ApiOperation(value = "Login with email and password")
	@ApiParam(hidden = true)
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody ClientDtoRequest clientDtoRequest) {
		return ResponseEntity.ok(userService.getToken(clientDtoRequest));
	}


}
