package com.getnet.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getnet.app.dto.OfferDtoRequest;
import com.getnet.app.dto.OfferDtoResponse;
import com.getnet.app.model.Offer;
import com.getnet.app.service.OfferService;

@RestController
@RequestMapping("offers")
public class OfferController {
	
	@Autowired
	private OfferService service;
	
	@PostMapping
	public ResponseEntity<OfferDtoResponse> create(@Valid @RequestBody OfferDtoRequest offerDto) throws Exception{
		
		Offer offer = service.create(offerDto.convertDtoToModel());
		
		OfferDtoResponse response = OfferDtoResponse.convertoModelToDto(offer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
