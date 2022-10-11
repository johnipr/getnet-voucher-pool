package com.getnet.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.getnet.app.model.Client;
import com.getnet.app.model.Offer;

public interface OfferRepository extends MongoRepository<Offer, String> {
	
	@Query("{ 'email' : ?0 }")
	public Optional<Client> findByEmail(String email);

}