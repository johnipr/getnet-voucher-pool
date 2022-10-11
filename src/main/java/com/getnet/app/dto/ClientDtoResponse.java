package com.getnet.app.dto;

import com.getnet.app.model.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientDtoResponse {

	private String email;
	private String name;

	public static ClientDtoResponse convertoModelToDto(Client client) {
		return new ClientDtoResponse(client.getEmail(), client.getName());
	}

}
