package com.getnet.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getnet.app.model.Client;
import com.getnet.app.model.Offer;
import com.getnet.app.repository.OfferRepository;

@Service
public class OfferService {

	@Autowired
	private OfferRepository repository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private VoucherService voucherService;
	
    public Offer create(Offer offer) throws Exception {
    	
    	//Create offer
    	offer = repository.save(offer);
    	
    	//Find all Clients
    	List<Client> clients = clientService.findAll();
    	
    	//Create a new voucher for each client
		voucherService.create(clients, offer);
    	
    	return offer;
    }
}
