package com.getnet.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.getnet.app.model.Offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDtoRequest {

	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotNull(message = "Percent Discount is mandatory")
	private Double percentDiscount;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Expiration Date is mandatory")
	private LocalDate expirationDate;
	
	public Offer convertDtoToModel() {
		return new Offer(name, percentDiscount, expirationDate);
	}
}
