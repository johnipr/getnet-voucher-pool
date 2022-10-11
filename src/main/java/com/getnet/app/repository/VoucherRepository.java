package com.getnet.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.getnet.app.model.Voucher;

public interface VoucherRepository  extends MongoRepository<Voucher, String> {
	
	@Query("{ 'email' : ?0, 'code' : ?1 , 'offer.expirationDate' : { $gte: ?2} }")
	Optional<Voucher> findByEmailAndCode(String email, String code,  LocalDate expirationDate );

	@Query("{ 'email' : ?0, 'redeemDate' : { $exists: false }, 'offer.expirationDate' : { $gte: ?1} }")
	Optional<List<Voucher>> findAllByEmail(String email, LocalDate expirationDate);
	
	@Query("{ 'code' : ?0 }")
	Optional<Voucher> findByCode(String code);
	
	
}
