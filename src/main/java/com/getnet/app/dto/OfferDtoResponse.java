package com.getnet.app.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.getnet.app.model.Offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfferDtoResponse {

	private String name;
	private Double percentDiscount;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate expirationDate;
	
	public static OfferDtoResponse convertoModelToDto(Offer offer) {
		return new OfferDtoResponse(offer.getName(), offer.getPercentDiscount(), offer.getExpirationDate());
	}
}
