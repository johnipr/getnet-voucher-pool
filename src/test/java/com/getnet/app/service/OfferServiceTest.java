package com.getnet.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.getnet.app.model.Offer;
import com.getnet.app.repository.OfferRepository;

@SpringBootTest
public class OfferServiceTest {
	
	@Autowired
	OfferService service;

	@MockBean
	OfferRepository repository;

	@Test
	public void should_Create_New_Offer() throws Exception {
		Offer offerReturn = new Offer("57baf801-c0e2-451a-b0eb-dd9441514dfe","Offer 01", 12d, LocalDate.now());
		when(this.repository.save(any(Offer.class))).thenReturn(offerReturn);

		Offer offer = new Offer("Offer 01", 12d, LocalDate.now());
		Offer offerResponse = service.create(offer);

		assertEquals(offerResponse.getId(), offerReturn.getId());
		assertEquals(offerResponse.getName(), offerReturn.getName());
		assertEquals(offerResponse.getPercentDiscount(), offerReturn.getPercentDiscount());
		assertEquals(offerResponse.getExpirationDate(), offerReturn.getExpirationDate());
	}

}
