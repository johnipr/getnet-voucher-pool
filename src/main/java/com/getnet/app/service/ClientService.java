package com.getnet.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.getnet.app.exception.ConflictException;
import com.getnet.app.model.Client;
import com.getnet.app.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	ClientRepository clientRepository;

	public Client signup(Client client) throws Exception {
		
		client.setPassword(encoder.encode(client.getPassword()));
		
		try {
			
			return clientRepository.save(client);
			
		} catch (DuplicateKeyException e) {
			throw new ConflictException("Email already in use : " + client.getEmail());
		} 
	}
	
	public List<Client> findAll() throws Exception {
		return clientRepository.findAll();
	}

}
