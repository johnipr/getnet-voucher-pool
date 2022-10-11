package com.getnet.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class Client {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String email;
	
	private String name;
	private String password;
	
	public Client(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	

}
