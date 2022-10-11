package com.getnet.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.getnet.app.model.Client;

public interface ClientRepository extends MongoRepository<Client, String> {

	@Query("{ 'email' : ?0 }")
	public Optional<Client> findByEmail(String email);

}
